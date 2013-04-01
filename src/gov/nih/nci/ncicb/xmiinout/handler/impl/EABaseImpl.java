/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler.impl;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDatatype;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLClassBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLInterfaceBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLModelBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLPackageBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.apache.log4j.Logger;

public abstract class EABaseImpl extends DefaultXmiHandler {

  private static Logger logger = Logger.getLogger(EABaseImpl.class.getName());
  
  protected Element rootElement;
  
  protected Map<String, UMLClassBean> idClassMap = new HashMap<String, UMLClassBean>();
  
  protected Map<String, UMLInterfaceBean> idInterfaceMap = new HashMap<String, UMLInterfaceBean>();

  protected Map<String, UMLPackageBean> idPkgClassifierMap = new HashMap<String, UMLPackageBean>();
  
  protected JDomXmiTransformer jdomXmiTransformer;

  public void _load(String filename) {
    models = new HashMap<String, UMLModel>();
    
    jdomXmiTransformer = new JDomXmiTransformer();
    
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filename);
      rootElement = doc.getRootElement();
      readModel(rootElement);
      
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }
    
  }
  
  public void _load(java.net.URI uri) {
    models = new HashMap<String, UMLModel>();
    
    jdomXmiTransformer = new JDomXmiTransformer();
    
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
    
    Writer writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
    XMLOutputter xmlout = new XMLOutputter();
    xmlout.setFormat(Format.getPrettyFormat());
    writer.write(xmlout.outputString(rootElement));
    writer.flush();
    writer.close();
    
  }
  
  public UMLModel getModel() {
    Iterator<UMLModel> it = models.values().iterator();
    if (it.hasNext())
      return it.next();
    else
      return null;
  }
  
  public UMLModel getModel(String modelName) {
    return models.get(modelName);
  }
  
  private void readModel(Element rootElement) throws JaxenException {
    
    String xpath = "/XMI/XMI.content/*[local-name()='Model']";
    Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
    
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> elts = path.selectNodes(rootElement);
    
    for (Element elt : elts) {
      UMLModelBean model = jdomXmiTransformer.toUMLModel(elt);
      models.put(model.getName(), model);
      
      for (UMLDatatype type : doDataTypes(elt)) {
        model.addDatatype(type);
        jdomXmiTransformer.addDatatype(type);
      }
      
      for (UMLPackage pkg : doPackages(elt)) {
        model.addPackage(pkg);
      }
      
      for (UMLClass clazz : doClasses(elt)) {
        model.addClass(clazz);
      }
      
      for (UMLInterface interfaze : doInterfaces(elt)) {
        model.addInterface(interfaze);
      }
      
      for (UMLGeneralization gen : doGeneralizations(elt)) {
        model.addGeneralization(gen);
      }
      
      for (UMLDependency dep : doDependencies(elt)) {
        model._addDependency(dep);
      }
      
      for (UMLAssociation assoc : doAssociations(elt)) {
        model.addAssociation(assoc);
      }
      
      Collection<UMLTaggedValue> taggedValues = doTaggedValues(elt);
      for(UMLTaggedValue tv : taggedValues) {
        model.addTaggedValue(tv);
      }
      
    }
    
    doRootTaggedValues(rootElement);
    
    
    // Must be done after classes for cross references.
    jdomXmiTransformer.completeAttributes(ns);
    jdomXmiTransformer.completeOperations(ns);
  }

  private List<UMLTaggedValue> doRootTaggedValues(Element rootElement)
    throws JaxenException {
    String xpath = "/XMI/XMI.content/*[local-name()='TaggedValue']";
    JDOMXPath path = new JDOMXPath(xpath);
    List<Element> elts = path.selectNodes(rootElement);
    
    List<UMLTaggedValue> result = new ArrayList<UMLTaggedValue>();
    
    for (Element tvElt : elts) {
      UMLTaggedValue tv = jdomXmiTransformer.toUMLTaggedValue(tvElt);
      
      if (tv != null)
        result.add(tv);
      
      Attribute refAtt = tvElt.getAttribute("modelElement");
      if (refAtt != null) {
        UMLClassBean clazz = idClassMap.get(refAtt.getValue());
        if (clazz != null) {
          clazz.addTaggedValue(tv);
        } else { // need to do it for packages
          UMLPackageBean pkg = idPkgClassifierMap.get(refAtt.getValue());
          if(pkg != null)
            pkg.addTaggedValue(tv);
        }
      }
    }
    
    return result;
  }
  
  private List<UMLPackageBean> doPackages(Element elt) {
    Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
    Element ownedElement = elt.getChild("Namespace.ownedElement", ns);
    List<Element> packageElements = (List<Element>) ownedElement
      .getChildren("Package", ns);
    
    List<UMLPackageBean> result = new ArrayList<UMLPackageBean>();
    
    for (Element pkgElement : packageElements) {
      UMLPackageBean umlPkg = jdomXmiTransformer.toUMLPackage(pkgElement);

      try {
        Element parentPkg = pkgElement.getParentElement().getParentElement();
        if(parentPkg.getName().equals("Model")) {
          // if parent is a model, then the ID used is that of the Model itself.
          String classRoleId = parentPkg.getAttribute("xmi.id").getValue();
          idPkgClassifierMap.put(classRoleId, umlPkg);
        } else if (parentPkg.getName().equals("Package")) {
          List<Element> CRElts = parentPkg
            .getChild("Namespace.ownedElement", ns)
            .getChild("Collaboration", ns)
            .getChild("Namespace.ownedElement", ns)
            .getChildren("ClassifierRole", ns);
          for(Element CRElt : CRElts) {
            String nameAtt = CRElt.getAttribute("name").getValue();
            if(nameAtt.equals(umlPkg.getName())) {
              String classRoleId = CRElt.getAttribute("xmi.id").getValue();
              idPkgClassifierMap.put(classRoleId, umlPkg);
            }
          }
        }
      } catch (NullPointerException e) {
        logger.error("ClassifierRole can't be found for package: " + umlPkg.getName());
      } 

      result.add(umlPkg);
      
//       try {
//         Collection<UMLTaggedValue> taggedValues = doRootTaggedValues(pkgElement);
//         for (UMLTaggedValue tv : taggedValues) {
//           umlPkg.addTaggedValue(tv);
//         }
//       } catch (JaxenException e) {
//         logger.error("Could not read tagged values for Package: " + umlPkg.getName());
//         logger.error(e);
//       } 
      
      for (UMLPackageBean pkg : doPackages(pkgElement)) {
        umlPkg.addPackage(pkg);
      }
      for (UMLClassBean clazz : doClasses(pkgElement)) {
        umlPkg.addClass(clazz);
      }
      for (UMLInterfaceBean interfaze : doInterfaces(pkgElement)) {
        umlPkg.addInterface(interfaze);
      }			
    }
    
    return result;
    
  }

	private List<UMLClassBean> doClasses(Element elt) {
		Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
		Element ownedElement = elt.getChild("Namespace.ownedElement", ns);

		List<Element> classElements = (List<Element>) ownedElement.getChildren(
				"Class", ns);
		List<UMLClassBean> result = new ArrayList<UMLClassBean>();

		for (Element classElement : classElements) {
			UMLClassBean umlClass = jdomXmiTransformer.toUMLClass(classElement,
					ns);
			Collection<UMLTaggedValue> taggedValues = doTaggedValues(classElement);
			for (UMLTaggedValue tv : taggedValues) {
				umlClass.addTaggedValue(tv);
			}

			List<UMLAttribute> atts = doAttributes(classElement);
			for (UMLAttribute att : atts) {
				umlClass.addAttribute(att);
			}

			logger.debug("Parsing operations for Class: "+classElement.getAttributeValue("name"));
			List<UMLOperation> ops = doOperations(classElement);
			for (UMLOperation att : ops) {
				umlClass.addOperation(att);
			}
			
			idClassMap.put(umlClass.getModelId(), umlClass);
			result.add(umlClass);

		}

		return result;

	}

	private List<UMLInterfaceBean> doInterfaces(Element elt) {
		Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
		Element ownedElement = elt.getChild("Namespace.ownedElement", ns);

		List<Element> interfaceElements = (List<Element>) ownedElement
				.getChildren("Interface", ns);
		List<UMLInterfaceBean> result = new ArrayList<UMLInterfaceBean>();

		for (Element interfaceElement : interfaceElements) {
			UMLInterfaceBean umlInterface = jdomXmiTransformer.toUMLInterface(
					interfaceElement, ns);
			Collection<UMLTaggedValue> taggedValues = doTaggedValues(interfaceElement);
			for (UMLTaggedValue tv : taggedValues) {
				umlInterface.addTaggedValue(tv);
			}

			List<UMLAttribute> atts = doAttributes(interfaceElement);
			for (UMLAttribute att : atts) {
				umlInterface.addAttribute(att);
			}

			List<UMLOperation> ops = doOperations(interfaceElement);
			for (UMLOperation att : ops) {
				umlInterface.addOperation(att);
			}
			
			idInterfaceMap.put(umlInterface.getModelId(), umlInterface);
			result.add(umlInterface);

		}

		return result;

	}

	protected abstract List<UMLAttribute> doAttributes(Element elt);

	protected abstract List<UMLOperation> doOperations(Element elt);

	protected abstract List<UMLTaggedValue> doTaggedValues(Element elt);

	protected abstract List<UMLAssociation> doAssociations(Element elt)
			throws JaxenException;

	protected abstract List<UMLGeneralization> doGeneralizations(Element elt)
			throws JaxenException;

	protected abstract List<UMLDependency> doDependencies(Element elt)
			throws JaxenException;

	protected abstract List<UMLDatatype> doDataTypes(Element elt);
}