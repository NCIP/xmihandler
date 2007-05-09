
package gov.nih.nci.ncicb.xmiinout.handler.impl;


import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.domain.bean.*;

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * 
 */
public class ArgoUMLDefaultImpl extends DefaultXmiHandler {

  private static Logger logger = Logger.getLogger(EADefaultImpl.class.getName());

  
  protected Element rootElement;

  protected Map<String, UMLClassBean> idClassMap = new HashMap<String, UMLClassBean>();

  protected void _load(String filename) {
	  
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filename);
      rootElement = doc.getRootElement();

      logger.debug("rootElement.getName(): " + rootElement.getName());      
      System.out.println("rootElement.getName(): " + rootElement.getName());
      readModel(rootElement);
      
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }
    
  }

  protected void _load(java.net.URI uri) {

    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(uri.toURL());
      rootElement = doc.getRootElement();
      readModel(rootElement);
      
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }
    
  }


  public void save(String filename) throws IOException {
    File f = new File(filename);
    
    Writer writer = new OutputStreamWriter
      (new FileOutputStream(f), "UTF-8");
    XMLOutputter xmlout = new XMLOutputter();
    xmlout.setFormat(Format.getPrettyFormat());
    writer.write(xmlout.outputString(rootElement));
    writer.flush();
    writer.close();
    
  }

  public UMLModel getModel() {
    Iterator<UMLModel> it = models.values().iterator();
    if(it.hasNext())
      return it.next();
    else 
      return null;
  }

  public UMLModel getModel(String modelName) {
    return models.get(modelName);
  }

  protected void readModel(Element rootElement) throws JaxenException {

    String xpath = "/uml/XMI/XMI.content/*[local-name()='Model']";

    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> elts = path.selectNodes(rootElement);

    System.out.println("Elements Found:  " + elts.size());
    
    for(Element elt : elts) {
      UMLModelBean model = JDomXmiTransformer.toUMLModel(elt);
      models.put(model.getName(), model);
      
      System.out.println("Model Name:  " + model.getName());
 
      for(UMLTagDefinitionBean def : doTagDefinitions(elt)) {
          model.addTagDefinition(def);
          JDomXmiTransformer.addTagDefinition(def);
        }
      
      for(UMLDatatype type : doDataTypes(elt)) {
        model.addDatatype(type);
        JDomXmiTransformer.addDatatype(type);
      }

      for(UMLPackage pkg : doPackages(elt)) {
        model.addPackage(pkg);
      }

      for(UMLClass clazz : doClasses(elt)) {
        model.addClass(clazz);
      }

      for(UMLGeneralization gen : doGeneralizations(elt)) {
        model.addGeneralization(gen);
      }

      for(UMLDependency dep : doDependencies(elt)) {
        model._addDependency(dep);
      }

      for(UMLAssociation assoc : doAssociations(elt)) {
        model.addAssociation(assoc);
      }      
    }

    doRootTaggedValues(rootElement);

    // Must be done after classes for cross references.
    JDomXmiTransformer.completeAttributes();
  }


  protected List<UMLTaggedValue> doRootTaggedValues(Element rootElement) 
    throws JaxenException {
    String xpath = "/XMI/XMI.content/*[local-name()='TaggedValue']";
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> elts = path.selectNodes(rootElement);
    
    List<UMLTaggedValue> result = new ArrayList<UMLTaggedValue>();

    if ( elts != null) {
    	System.out.println("Root Tagged Values found: " + elts.size());
    } else {
    	System.out.println("No Root Tagged Values found");
    }
    
    for(Element tvElt : elts) {
      UMLTaggedValue tv = JDomXmiTransformer.toArgoUMLTaggedValue(tvElt);

      if(tv != null)
        result.add(tv);

      Attribute refAtt = tvElt.getAttribute("modelElement");
      if(refAtt != null) {
        UMLClassBean clazz = idClassMap.get(refAtt.getValue());
        if(clazz != null) {
          clazz.addTaggedValue(tv);
        }
      }
    }

    return result;
    
  }
  
  protected List<UMLTagDefinitionBean> doTagDefinitions(Element elt) {
	    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
	    Element ownedElement = elt.getChild("Namespace.ownedElement", ns);	    
	    
	    if (ownedElement == null){
	    	System.out.println("ownedElement is null for Element " + elt.getAttributeValue("name"));
	    	return (List)new ArrayList();
	    }
	    
	    List<UMLTagDefinitionBean> result = new ArrayList<UMLTagDefinitionBean>();

	    List<Element> tdElements = (List<Element>)ownedElement.getChildren("TagDefinition", ns);
	    System.out.println("TagDefinition Elements found: " + tdElements.size());
	    
	    for(Element tdElt : tdElements) {
	    	UMLTagDefinitionBean td = JDomXmiTransformer.toUMLTagDefinition(tdElt);
	      System.out.println("TagDefinition: " + td.getName() + ", xmi.id: " + td.getXmiId());
	      if(td != null)
	        result.add(td);
	    }

	    return result;
  }
  

  protected List<UMLPackageBean> doPackages(Element elt) {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    Element ownedElement = elt.getChild("Namespace.ownedElement", ns);
    
    if (ownedElement == null){
    	System.out.println("ownedElement is null for Element " + elt.getAttributeValue("name"));
    	return (List)new ArrayList();
    }
    
    List<Element> packageElements = (List<Element>)ownedElement.getChildren("Package", ns);

    System.out.println("Package size: " + packageElements.size());
    List<UMLPackageBean> result = new ArrayList<UMLPackageBean>();

    for(Element pkgElement : packageElements) {
      System.out.println("Package name: " + pkgElement.getAttributeValue("name"));
      UMLPackageBean umlPkg = JDomXmiTransformer.toUMLPackage(pkgElement);
      result.add(umlPkg);

      Collection<UMLTaggedValue> taggedValues = doTaggedValues(pkgElement);
      for(UMLTaggedValue tv : taggedValues) {
        umlPkg.addTaggedValue(tv);
      }

      for(UMLPackageBean pkg : doPackages(pkgElement)) {
        umlPkg.addPackage(pkg);
      }
      for(UMLClassBean clazz : doClasses(pkgElement)) {
        umlPkg.addClass(clazz);
      }
    }
    
    return result;
    
  }

  protected List<UMLClassBean> doClasses(Element elt) {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    Element ownedElement = elt.getChild("Namespace.ownedElement", ns);
    
    
    if (ownedElement == null){
    	System.out.println("ownedElement is null for Element " + elt.getAttributeValue("name"));
    	return (List)new ArrayList();
    }
    
    List<Element> classElements = (List<Element>)ownedElement.getChildren("Class", ns);
    List<UMLClassBean> result = new ArrayList<UMLClassBean>();

    for(Element classElement : classElements) {
      UMLClassBean umlClass = JDomXmiTransformer.toUMLClass(classElement);
      
      Collection<UMLTaggedValue> taggedValues = doTaggedValues(classElement);
      for(UMLTaggedValue tv : taggedValues) {
        umlClass.addTaggedValue(tv);
      }

      List<UMLAttribute> atts = doAttributes(classElement);
      for(UMLAttribute att : atts) {
        umlClass.addAttribute(att);
      }

      idClassMap.put(umlClass.getModelId(), umlClass);
      result.add(umlClass);
      
    }
    
    return result;

  }
  
  
  protected List<UMLDatatype> doDataTypes(Element modelElt) {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    Element ownedElement = modelElt.getChild("Namespace.ownedElement", ns);

    List<Element> typeElements = (List<Element>)ownedElement.getChildren("DataType", ns);
    
    System.out.println("TypeElements size: " + typeElements.size());
    
    List<UMLDatatype> result = new ArrayList<UMLDatatype>();

    for(Element typeElt : typeElements) {
      result.add(JDomXmiTransformer.toUMLDatatype(typeElt));
    }
    return result;

  }
 
  protected List<UMLTaggedValue> doTaggedValues(Element elt) {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    Element modelElement = elt.getChild("ModelElement.taggedValue", ns);

    List<UMLTaggedValue> result = new ArrayList<UMLTaggedValue>();
    if(modelElement == null)
      return result;

    List<Element> tvElements = (List<Element>)modelElement.getChildren("TaggedValue", ns);
    System.out.println("TaggedValue Elements found: " + tvElements.size());
    for(Element tvElt : tvElements) {
      UMLTaggedValue tv = JDomXmiTransformer.toArgoUMLTaggedValue(tvElt);
      if(tv != null)
        result.add(tv);
    }

    return result;
  }

  protected  List<UMLAttribute> doAttributes(Element classElement) {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    Element featureElement = classElement.getChild("Classifier.feature", ns);

    List<UMLAttribute> result = new ArrayList<UMLAttribute>();
    if(featureElement == null)
      return result;

    List<Element> attElements = (List<Element>)featureElement.getChildren("Attribute", ns);
    
    for(Element attElt : attElements) {
      UMLAttributeBean umlAtt = JDomXmiTransformer.toUMLAttribute(attElt);

      Collection<UMLTaggedValue> taggedValues = doTaggedValues(attElt);
      for(UMLTaggedValue tv : taggedValues) {
        umlAtt.addTaggedValue(tv);
      }

      result.add(umlAtt);
    }

    return result;
    
  }

  protected List<UMLDependency> doDependencies(Element modelElement) throws JaxenException {
    String xpath = "//*[local-name()='Dependency']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> depElts = path.selectNodes(rootElement);
    
    System.out.println("Dependency Elements Found: " + depElts.size());
    
    List<UMLDependency> result = new ArrayList<UMLDependency>();
    
    for(Element depElt : depElts) {
    	
    	String xmiId = depElt.getAttributeValue("xmi.id");
        if (xmiId == null ) { continue; }
        
        System.out.println("depElt.getAttributeValue('xmi.id'): " + xmiId);    	
    	
		Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
		Element clientElement = depElt.getChild("Dependency.client", ns);
		Element clientClassElement = clientElement.getChild("Class", ns);
		
		System.out.println("clientClassElement: " + clientClassElement.getAttributeValue("xmi.idref")); 
		
		Element supplierElement = depElt.getChild("Dependency.supplier", ns);
		Element supplierClassElement = supplierElement.getChild("Class", ns);
		
		System.out.println("supplierClassElement: " + supplierClassElement.getAttributeValue("xmi.idref"));  
        
		UMLDependencyEnd client = idClassMap.get(clientClassElement.getAttributeValue("xmi.idref"));
	    System.out.println("client: " + client);
	    
		UMLDependencyEnd supplier = idClassMap.get(supplierClassElement.getAttributeValue("xmi.idref"));
	    System.out.println("supplier: " + supplier);
	
	    if(client == null) {
	      System.out.println("Can't find client for dependency: " + depElt.getAttribute("xmi.id") + " -- only dependencies to classes are supported -- ignoring");
	      logger.info("Can't find client for dependency: " + depElt.getAttribute("xmi.id") + " -- only dependencies to classes are supported -- ignoring");
	      continue;
	    }
	    if(supplier == null) {
		  System.out.println("Can't find supplier for dependency: " + depElt.getAttribute("xmi.id") + " -- only dependencies to classes are supported -- ignoring");
	      logger.info("Can't find supplier for dependency: " + depElt.getAttribute("xmi.id") + " -- only dependencies to classes are supported -- ignoring");
	      continue;
	    }
        
	  Attribute nameAtt = depElt.getAttribute("name");
	  String depName = null;
	  if(nameAtt != null)
	    depName = nameAtt.getValue();
	  
	  Attribute visAtt = depElt.getAttribute("visibility");
	  UMLVisibility visibility = null;
	  if(visAtt != null) {
	    visibility = new UMLVisibilityBean(visAtt.getValue());
	  }


      UMLDependencyBean depBean = new UMLDependencyBean(depElt, depName, visibility, client, supplier);
	  
      Collection<UMLTaggedValue> taggedValues = doTaggedValues(depElt);
      for(UMLTaggedValue tv : taggedValues) {
    	  depBean.addTaggedValue(tv);
      }
      
      result.add(depBean);
    }    

    return result;

  }


  protected List<UMLGeneralization> doGeneralizations(Element modelElement) throws JaxenException {
    String xpath = "//*[local-name()='Generalization']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> genElts = path.selectNodes(rootElement);
    
    System.out.println("Number of Generalization Elements Found: " + genElts.size());
    
    List<UMLGeneralization> result = new ArrayList<UMLGeneralization>();
    
    if (genElts == null || genElts.isEmpty()){
    	System.out.println("No Generalization Elements found");
    	return (List)new ArrayList();
    }
    
    for(Element genElt : genElts) {
     
      if (genElt.getAttributeValue("xmi.id") == null ) { continue; }
      
//      System.out.println("genElt.getAttributeValue('xmi.id'): " + genElt.getAttributeValue("xmi.id"));

      Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
      Element childElement = genElt.getChild("Generalization.child", ns);
      Element childClassElement = childElement.getChild("Class", ns);
      
//      System.out.println("*** childClassElement: " + childClassElement.getAttributeValue("xmi.idref")); 
      
      Element parentElement = genElt.getChild("Generalization.parent", ns);
      Element parentClassElement = parentElement.getChild("Class", ns);
      
//      System.out.println("*** parentClassElement: " + parentClassElement.getAttributeValue("xmi.idref"));  
   
      UMLClassBean subClass = idClassMap.get(childClassElement.getAttributeValue("xmi.idref"));
      System.out.println("*** subClass name: " + subClass.getName());
      
      UMLClassBean superClass = idClassMap.get(parentClassElement.getAttributeValue("xmi.idref"));
      System.out.println("*** superClass name: " + superClass.getName());      
//    result.add(JDomXmiTransformer.toUMLGeneralization(genElt));
      result.add(new UMLGeneralizationBean(genElt, superClass, subClass));
    }    

    return result;

  }

  protected List<UMLAssociation> doAssociations(Element modelElement) throws JaxenException {
    Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
    String xpath = "//*[local-name()='Association']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> assocElts = path.selectNodes(rootElement);
    
    System.out.println("Number of Association Elements Found: " + assocElts.size());    
    
    List<UMLAssociation> result = new ArrayList<UMLAssociation>();
    
    for(Element assocElt : assocElts) {
    	
      String xmiId = assocElt.getAttributeValue("xmi.id");
      if (xmiId == null ) { continue; }
        
      System.out.println("assocElt.getAttributeValue('xmi.id'): " + xmiId);    	
    	
      Element connectionElement = assocElt.getChild("Association.connection", ns);

      if(connectionElement == null)
        continue;

      List<Element> endElements = (List<Element>)connectionElement.getChildren("AssociationEnd", ns);
      
      System.out.println("Number of Association End Elements Found: " + endElements.size());
      
      UMLAssociationEndBean srcEnd = null, targetEnd = null;
      
      for(Element endElt : endElements) {
    	  
	  	Element participantElement = endElt.getChild("AssociationEnd.participant", ns);
	  	Element participantClassElement = participantElement.getChild("Class", ns);
	  	System.out.println("participantClassElement: " + participantClassElement.getAttributeValue("xmi.idref")); 
    	  
        UMLClassBean endClass = idClassMap.get(participantClassElement.getAttributeValue("xmi.idref"));
        System.out.println("AssociationEnd Class: " + endClass.getName());
  
//  EA
//		<UML:AssociationEnd visibility="public" multiplicity="1..*" name="organization" aggregation="none" isOrdered="false" isNavigable="true" type="EAID_C090E3E1_AFE9_48cd_B28C_130C42C6A4C7">
//			<UML:ModelElement.taggedValue/>
//		</UML:AssociationEnd>

//  ArgoUML
//        <UML:AssociationEnd.multiplicity>
//	        <UML:Multiplicity xmi.id = '-64--88-1-107-c5495e:111b95291de:-8000:000000000000314A'>
//	          <UML:Multiplicity.range>
//	            <UML:MultiplicityRange xmi.id = '-64--88-1-107-c5495e:111b95291de:-8000:0000000000003149'
//	              lower = '0' upper = '-1'/>
//	          </UML:Multiplicity.range>
//	        </UML:Multiplicity>
//        </UML:AssociationEnd.multiplicity>        

        // Get Multiplicity Range
	  	Element assocEndMultiplicity = endElt.getChild("AssociationEnd.multiplicity", ns);
	  	Element multiplicityElement = assocEndMultiplicity.getChild("Multiplicity", ns);
	  	Element multiplicityDotRangeElement = multiplicityElement.getChild("Multiplicity.range", ns);
	  	Element multiplicityRangeElement = multiplicityDotRangeElement.getChild("MultiplicityRange", ns);	  	
	  	System.out.println("multiplicityRangeElement: " + multiplicityRangeElement.getAttributeValue("xmi.id"));         
        
        int low = 0, high = 0;
        org.jdom.Attribute lowerMultAttr = multiplicityRangeElement.getAttribute("lower");
        org.jdom.Attribute upperMultAttr = multiplicityRangeElement.getAttribute("upper");        
        
        if(lowerMultAttr != null && upperMultAttr != null) {
          String lower = lowerMultAttr.getValue();
          System.out.println("multiplicity lower:  " + lower);
          
          String upper = upperMultAttr.getValue();
          System.out.println("multiplicity upper:  " + upper);
          
          low = Integer.valueOf(lower);
          low = Integer.valueOf(upper);          
        }

        boolean navigable = Boolean.valueOf(endElt.getAttribute("isNavigable").getValue());
        System.out.println("isNavigable: " + navigable);

        org.jdom.Attribute nameAtt = endElt.getAttribute("name");
        String name = nameAtt != null?nameAtt.getValue():"";
        System.out.println("name: " + name);

        UMLAssociationEndBean endBean = new UMLAssociationEndBean
          (endElt,
           endClass,
           name,
           low,
           high,
           navigable);
        
        if(srcEnd == null)
          srcEnd = endBean;
        else
          targetEnd = endBean;

        Collection<UMLTaggedValue> taggedValues = doTaggedValues(endElt);
        for(UMLTaggedValue tv : taggedValues) {
          System.out.println("taggedValue: " + tv.getName());
          endBean.addTaggedValue(tv);
        }

      }

      List<UMLAssociationEnd> endBeans = new ArrayList<UMLAssociationEnd>();
      endBeans.add(srcEnd);
      endBeans.add(targetEnd);

      if(srcEnd.getUMLElement() == null || targetEnd.getUMLElement() == null) {
        logger.info("Can't find end class for Association: " + assocElt.getAttribute("xmi.id") + " -- only associations to classes are supported -- ignoring");
        continue;
      }

      
      Attribute nameAtt = assocElt.getAttribute("name");
      String assocRoleName = null;
      if(nameAtt != null)
        assocRoleName = nameAtt.getValue();
        
      
      UMLAssociationBean assoc = new UMLAssociationBean(assocElt, assocRoleName, endBeans);

      Collection<UMLTaggedValue> taggedValues = doTaggedValues(assocElt);
      for(UMLTaggedValue tv : taggedValues) {
        assoc.addTaggedValue(tv);
      }

      result.add(assoc);
    }    

    return result;

  }



}