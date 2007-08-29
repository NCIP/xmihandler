package gov.nih.nci.ncicb.xmiinout.handler.impl;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDatatype;
import gov.nih.nci.ncicb.xmiinout.domain.UMLVisibility;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAttributeBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLClassBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLStereotypeDefinitionBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTagDefinitionBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTaggedValueBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLVisibilityBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class ArgoJDomXmiTransformer extends JDomXmiTransformer {
	
	private static Logger logger = Logger.getLogger(ArgoJDomXmiTransformer.class
			.getName());

	static private Map<String, UMLTagDefinitionBean> tagDefinitionsByNameMap = new HashMap<String, UMLTagDefinitionBean>();
	static private Map<String, UMLTagDefinitionBean> tagDefinitionsByXmiIdMap = new HashMap<String, UMLTagDefinitionBean>();

	private static Map<String, UMLStereotypeDefinitionBean> stereotypeDefinitionsByName = new HashMap<String, UMLStereotypeDefinitionBean>();
	private static Map<String, UMLStereotypeDefinitionBean> stereotypeDefinitionsByXmiId = new HashMap<String, UMLStereotypeDefinitionBean>();	


	public static void addTagDefinition(UMLTagDefinitionBean tagDefinition) {
		tagDefinitionsByXmiIdMap.put(((UMLTagDefinitionBean) tagDefinition).getXmiId(),
				tagDefinition);
		tagDefinitionsByNameMap.put(((UMLTagDefinitionBean) tagDefinition).getName(),
				tagDefinition);		
	}

	static void addStereotypeDefinition(UMLStereotypeDefinitionBean typeDef) {
		stereotypeDefinitionsByXmiId.put(typeDef.getModelId(), typeDef);
		stereotypeDefinitionsByName.put(typeDef.getName(), typeDef);
	}
	
	public static UMLStereotypeDefinitionBean getStereotypeDefinition(String stereotype) {
		UMLStereotypeDefinitionBean defBean = stereotypeDefinitionsByXmiId.get(stereotype);
		
		if (defBean == null){
			defBean = stereotypeDefinitionsByName.get(stereotype);
		}
		
		return defBean;
	}	
		
	static String getStereotypeName(Element stElt) {
		String stereotypeId = stElt.getAttribute("xmi.idref").getValue();
		
		UMLStereotypeDefinitionBean typeDef =  stereotypeDefinitionsByXmiId.get(stereotypeId);
		if (typeDef != null) {
			return typeDef.getName();
		}
		return null;

	}
	
	static UMLClassBean toUMLClass(Element classElement, Namespace ns) {

		Attribute visibilityAtt = classElement.getAttribute("visibility");
		String visibility = visibilityAtt != null ? visibilityAtt.getValue()
				: null;
		UMLVisibility umlVis = new UMLVisibilityBean(visibility);

		String stereotypeName = null;
			
		List<Element> elts = (List<Element>) classElement.getChildren(
				"ModelElement.stereotype", ns);
		
		if (elts.size() > 0) {
			Element modelStElt = elts.get(0);
			List<Element> stElts = (List<Element>) modelStElt.getChildren(
					"Stereotype", ns);
			if (stElts.size() > 0) {
				Element stElt = stElts.get(0);
				stereotypeName = getStereotypeName(stElts.get(0));
			}
		}

		UMLClassBean clazz = new UMLClassBean(classElement, classElement
				.getAttribute("name").getValue(), umlVis, stereotypeName);

		clazz.setModelId(classElement.getAttribute("xmi.id").getValue());

		addDatatype(clazz);
		return clazz;
	}
	
	static UMLStereotypeDefinitionBean toUMLStereotypeDefinition(Element typeElt) {
		String xmiId = typeElt.getAttribute("xmi.id").getValue();

		Attribute nameAtt = typeElt.getAttribute("name");
		String name = null;
		if (nameAtt != null)
			name = nameAtt.getValue();
		else
			name = "";

		UMLStereotypeDefinitionBean result = new UMLStereotypeDefinitionBean(typeElt, xmiId, name);
		result.setModelId(xmiId);
		return result;
	}
	
	static UMLAttributeBean toUMLAttribute(Element attElement, Namespace ns) {
		Attribute visibilityAtt = attElement.getAttribute("visibility");
		String visibility = visibilityAtt != null ? visibilityAtt.getValue()
				: null;
		UMLVisibility umlVis = new UMLVisibilityBean(visibility);

		UMLDatatype datatype = null;

		logger.debug("***attElement.getAttribute('name'): " + attElement.getAttribute("name"));
		
		Element sfTypeElement = attElement.getChild("StructuralFeature.type", ns);		
		logger.debug("sfTypeElement: " + sfTypeElement);
		
		if (sfTypeElement != null) {
			Element classifElt = sfTypeElement.getChild("DataType", ns);
			logger.debug("classifElt: " + classifElt);
			
			if (classifElt == null) {
				classifElt = sfTypeElement.getChild("Class", ns);
				logger.debug("classifElt: " + classifElt);
			}
			if (classifElt != null) {
				Attribute typeAtt = classifElt.getAttribute("xmi.idref");
				if (typeAtt != null) {
					String typeId = typeAtt.getValue();
					logger.debug("*** typeId: " + typeId);
					datatype = datatypes.get(typeId);
				}
			}
		}

		UMLAttributeBean att = new UMLAttributeBean(attElement, attElement
				.getAttribute("name").getValue(), datatype, umlVis);

		// maybe we haven't discovered the datatype yet.
		if (datatype == null) {
			logger.debug("*** datatype is null; will try to discover later");
			attWithMissingDatatypes.add(att);
		}

		return att;
	}	

	/**
	 * Run this after you've processed attributes for attributes that use
	 * classes as datatypes.
	 */
	static void completeAttributes(Namespace ns) {
		for (UMLAttributeBean att : attWithMissingDatatypes) {
			Element attElement = att.getJDomElement();

			Element sfTypeElement = attElement.getChild(
					"StructuralFeature.type", ns);
			if (sfTypeElement != null) {
				Element classifElt = sfTypeElement.getChild("DataType", ns);
				if (classifElt == null) {
					classifElt = sfTypeElement.getChild("Class", ns);
				}
				
				if (classifElt != null) {
					Attribute typeAtt = classifElt.getAttribute("xmi.idref");
					if (typeAtt != null) {
						String typeId = typeAtt.getValue();
						att._setDatatype(datatypes.get(typeId));
					}
				}
			}
		}

	}	

	static UMLTagDefinitionBean toUMLTagDefinition(Element tdElement) {
		//
		// EA
		// None
		//
		// ArgoUML
		// <UML:TagDefinition xmi.id="-64--88-1-107-8238f4:1121acba21f:-8000:000000000000317C" name="type" isSpecification="false" tagType="String" />
		//	 
		
		if (tdElement.getAttribute("name") == null) {
			logger.info("tagDefinition missing 'name' attribute, skipping");
			System.out
			.println("tagDefinition missing 'name' attribute, skipping");
			return null;
		}

		UMLTagDefinitionBean td = new UMLTagDefinitionBean(tdElement, tdElement
				.getAttribute("xmi.id").getValue(), tdElement.getAttribute(
				"name").getValue());
		return td;
	}
	
	static UMLTaggedValueBean toUMLTaggedValue(Element tvElement, Namespace ns) {
		//
		// EA
		// <UML:TaggedValue tag="myClassTaggedValue" value="test 123"
		// xmi.id="1D2F36D4_E881_44C1_8051_106A39593C26"
		// modelElement="EAID_05D28ABC_F678_4c6a_AA5C_513217EB2E68" />
		//
		// ArgoUML
		// <UML:TaggedValue xmi.id="EAID_E5485B89_9415_42ea_AC45_28D8A2349539_fix_2_fix_0_fix_0"
		// isSpecification="false">
		// <UML:TaggedValue.dataValue>gov.nih.nci.cacoresdk.domain.manytomany.bidirectional.Project.employeeCollection</UML:TaggedValue.dataValue>
		// <UML:TaggedValue.type>
		// <UML:TagDefinition xmi.idref="-64--88-1-107-16925b0:1120c726d7c:-8000:0000000000003170" />
		// </UML:TaggedValue.type>
		// </UML:TaggedValue>

		Element dataValueElement = tvElement.getChild("TaggedValue.dataValue",
				ns);
		Element typeElement = tvElement.getChild("TaggedValue.type", ns);

		if (typeElement == null) {
			logger.info("taggedValue "
					+ tvElement.getAttribute("xmi.id").getValue()
					+ " missing 'TagDefinition' element, skipping");
			logger.debug("taggedValue "
					+ tvElement.getAttribute("xmi.id").getValue()
					+ " missing 'TagDefinition' element, skipping");

			return null;
		}

		if (dataValueElement == null) {
			logger.info("taggedValue missing dataValue Element, skipping");
			System.out
			.println("taggedValue missing dataValue Element, skipping");
			return null;
		}

		Element tagDefinitionElement = typeElement
		.getChild("TagDefinition", ns);

		logger.debug("*** tagDefinition name: "
				+ tagDefinitionsByXmiIdMap.get(tagDefinitionElement.getAttributeValue("xmi.idref")).getName());		

		logger.debug("*** dataValue: "
				+ dataValueElement.getText());

		UMLTaggedValueBean tv = new UMLTaggedValueBean(tvElement,
				tagDefinitionsByXmiIdMap.get(tagDefinitionElement.getAttributeValue("xmi.idref")).getName(),
				dataValueElement.getText());
		return tv;
	}

	public static UMLTagDefinitionBean getTagDefinitionByName(String name) {
		return tagDefinitionsByNameMap.get(name);
	}

	public static UMLTagDefinitionBean getTagDefinitionByXmiId(String xmiId) {
		return tagDefinitionsByNameMap.get(xmiId);
	}

	public static UMLStereotypeDefinitionBean addStereotypeDefinition(Element elt, String name) {
		Namespace ns = elt.getNamespace();
		logger.debug("Namespace: " + ns);
		
		Element rootElt = elt.getDocument().getRootElement();
		logger.debug("Root Element name: " + rootElt.getName());
		Element xmiElt = rootElt.getChild("XMI");	
		logger.debug("XMI Element name: " + xmiElt.getName());		
		Element xmiContentElt = xmiElt.getChild("XMI.content");
		logger.debug("XMI Content Element name: " + xmiContentElt.getName());		

	
	    List<Element> xmiContentChildren = (List<Element>)xmiContentElt.getChildren();
	    logger.debug("xmiContentChildren Elements found: " + xmiContentChildren.size());
	    
	    Element modelElt = null;
	    for(Element xmiContentChild : xmiContentChildren) {
	    	logger.debug("xmiContentChild: " + xmiContentChild.getName());
	    	
	    	if (xmiContentChild.getName().equalsIgnoreCase("Model")){
	    		modelElt = xmiContentChild;
	    	}
	    }		
	    
		logger.debug("Model Element name: " + modelElt.getName());	    

		Element ownedElement = modelElt.getChild("Namespace.ownedElement", ns);	
		logger.debug("ownedElement name: " + ownedElement.getName());		
		
		String xmiId = java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase();
		UMLStereotypeDefinitionBean stereotypeDefBean = new UMLStereotypeDefinitionBean(ownedElement, xmiId, name); 
		
		
		Element newStereotypeElt = new Element("Stereotype", ns);
		
		newStereotypeElt.setAttribute("xmi.id", xmiId);
		newStereotypeElt.setAttribute("name", name);

		//<UML:Stereotype.baseClass>Dependency</UML:Stereotype.baseClass>
		Element stBaseClassElt = new Element("Stereotype.baseClass", ns);
		stBaseClassElt.setText("Dependency");	
		
		newStereotypeElt.addContent(stBaseClassElt);

		ownedElement.addContent(newStereotypeElt);

		ArgoJDomXmiTransformer.addStereotypeDefinition(stereotypeDefBean);

		return (UMLStereotypeDefinitionBean)stereotypeDefBean;
	}
	
}