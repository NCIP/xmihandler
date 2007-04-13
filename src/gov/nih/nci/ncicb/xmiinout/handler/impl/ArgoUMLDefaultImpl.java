
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
public class ArgoUMLDefaultImpl extends ArgoUMLBaseImpl {

  private static Logger logger = Logger.getLogger(EADefaultImpl.class.getName());

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
    for(Element tvElt : tvElements) {
      UMLTaggedValue tv = JDomXmiTransformer.toUMLTaggedValue(tvElt);
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

  public List<UMLDependency> doDependencies(Element modelElement) throws JaxenException {
    String xpath = "//*[local-name()='Dependency']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> depElts = path.selectNodes(rootElement);
    
    System.out.println("Number of Dependency Elements Found: " + depElts.size());
    
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
	      logger.info("Can't find client for dependency: " + depElt.getAttribute("xmi.id") + " -- only dependencies to classes are supported -- ignoring");
	      continue;
	    }
	    if(supplier == null) {
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

      result.add(new UMLDependencyBean(depElt, depName, visibility, client, supplier));
    }    

    return result;

  }


  public List<UMLGeneralization> doGeneralizations(Element modelElement) throws JaxenException {
    String xpath = "//*[local-name()='Generalization']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> genElts = path.selectNodes(rootElement);
    
    System.out.println("Number of Generalization Elements Found: " + genElts.size());
    
    List<UMLGeneralization> result = new ArrayList<UMLGeneralization>();
    
    System.out.println("modelElement.getAttributeValue('name'): " + modelElement.getAttributeValue("name"));
    
    if (genElts == null || genElts.isEmpty()){
    	System.out.println("No Generalization Elements found");
    	return (List)new ArrayList();
    }
    
    for(Element genElt : genElts) {
     
      if (genElt.getAttributeValue("xmi.id") == null ) { continue; }
      
      System.out.println("genElt.getAttributeValue('xmi.id'): " + genElt.getAttributeValue("xmi.id"));

      Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
      Element childElement = genElt.getChild("Generalization.child", ns);
      Element childClassElement = childElement.getChild("Class", ns);
      
      System.out.println("childClassElement: " + childClassElement.getAttributeValue("xmi.idref")); 
      
      Element parentElement = genElt.getChild("Generalization.parent", ns);
      Element parentClassElement = parentElement.getChild("Class", ns);
      
      System.out.println("parentClassElement: " + parentClassElement.getAttributeValue("xmi.idref"));  
   
      UMLClassBean subClass = idClassMap.get(childClassElement.getAttributeValue("xmi.idref"));
      System.out.println("subClass name: " + subClass.getName());
      
      UMLClassBean superClass = idClassMap.get(parentClassElement.getAttributeValue("xmi.idref"));
      System.out.println("superClass name: " + superClass.getName());      
//    result.add(JDomXmiTransformer.toUMLGeneralization(genElt));
      result.add(new UMLGeneralizationBean(genElt, superClass, subClass));
    }    

    return result;

  }

  public List<UMLAssociation> doAssociations(Element modelElement) throws JaxenException {
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

	  	Element multiplicityElement = endElt.getChild("AssociationEnd.multiplicity", ns);
	  	Element multiplicityClassElement = multiplicityElement.getChild("Class", ns);
	
	  	System.out.println("multiplicityElement: " + participantClassElement.getAttributeValue("xmi.idref"));         
        
        int low = 0, high = 0;
        org.jdom.Attribute multAtt = endElt.getAttribute("multiplicity");
        
        if(multAtt != null) {
          String multiplicity = multAtt.getValue();
          String[] multiplicities = multiplicity.split("\\.\\.");
          low = multiplicities[0].equals("*")?-1:Integer.valueOf(multiplicities[0]);
          if(multiplicities.length > 1)
            high = multiplicities[1].equals("*")?-1:Integer.valueOf(multiplicities[1]);
          else high = low;
        }

        boolean navigable = Boolean.valueOf(endElt.getAttribute("isNavigable").getValue());

        org.jdom.Attribute nameAtt = endElt.getAttribute("name");
        String name = nameAtt != null?nameAtt.getValue():"";

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