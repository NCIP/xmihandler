package gov.nih.nci.ncicb.xmiinout.handler.impl;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAbstractModifier;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDatatype;
import gov.nih.nci.ncicb.xmiinout.domain.UMLVisibility;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAbstractModifierBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAttributeBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLClassBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLDatatypeBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLModelBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLPackageBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTaggedValueBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLVisibilityBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class JDomXmiTransformer {

	protected Map<String, UMLDatatype> datatypes = new HashMap<String, UMLDatatype>();

	protected List<UMLAttributeBean> attWithMissingDatatypes = new ArrayList<UMLAttributeBean>();

	private static Logger logger = Logger.getLogger(JDomXmiTransformer.class
			.getName());

	void addDatatype(UMLDatatype datatype) {
		if (datatype instanceof UMLDatatypeBean)
			datatypes.put(((UMLDatatypeBean) datatype).getModelId(), datatype);
		else if (datatype instanceof UMLClassBean)
			datatypes.put(((UMLClassBean) datatype).getModelId(), datatype);
	}
	
	 String getStereotypeName(Element stElt){
		
		String stereotypeName = null;
		Attribute stereotypeAtt = stElt.getAttribute("name");
		stereotypeName =  stereotypeAtt.getValue();
		
		return stereotypeName;
	}

	 UMLDatatypeBean toUMLDatatype(Element typeElt) {
		String xmi_id = typeElt.getAttribute("xmi.id").getValue();

		Attribute nameAtt = typeElt.getAttribute("name");
		String name = null;
		if (nameAtt != null)
			name = nameAtt.getValue();
		else
			name = "";

		UMLDatatypeBean result = new UMLDatatypeBean(typeElt, name);
		result.setModelId(xmi_id);
		return result;
	}

	 UMLModelBean toUMLModel(Element modelElement) {
		UMLModelBean model = new UMLModelBean(modelElement);
		model.setName(modelElement.getAttribute("name").getValue());
		return model;
	}

	 UMLPackageBean toUMLPackage(Element packageElement) {
		UMLPackageBean pkg = new UMLPackageBean(packageElement, packageElement
				.getAttribute("name").getValue());
		return pkg;
	}

	 UMLClassBean toUMLClass(Element classElement, Namespace ns) {

		Attribute isAbstractAtt = classElement.getAttribute("isAbstract");
		String abstractModifier = isAbstractAtt != null ? isAbstractAtt.getValue()
				: "false";
		UMLAbstractModifier umlAbstractModifier = new UMLAbstractModifierBean(abstractModifier);
		 
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
				.getAttribute("name").getValue(), umlAbstractModifier, umlVis, stereotypeName);

		clazz.setModelId(classElement.getAttribute("xmi.id").getValue());

		addDatatype(clazz);
		return clazz;
	}

	 UMLAttributeBean toUMLAttribute(Element attElement, Namespace ns) {
		Attribute visibilityAtt = attElement.getAttribute("visibility");
		String visibility = visibilityAtt != null ? visibilityAtt.getValue()
				: null;
		UMLVisibility umlVis = new UMLVisibilityBean(visibility);

		UMLDatatype datatype = null;

		logger.debug("***attElement.getAttribute('name'): " + attElement.getAttribute("name"));
		
		Element sfTypeElement = attElement.getChild("StructuralFeature.type", ns);		
		logger.debug("sfTypeElement: " + sfTypeElement);
		
		if (sfTypeElement != null) {
			Element classifElt = sfTypeElement.getChild("Classifier", ns);
			logger.debug("classifElt: " + classifElt);
			
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
	 void completeAttributes(Namespace ns) {
		for (UMLAttributeBean att : attWithMissingDatatypes) {
			Element attElement = att.getJDomElement();

			Element sfTypeElement = attElement.getChild(
					"StructuralFeature.type", ns);
			if (sfTypeElement != null) {
				Element classifElt = sfTypeElement.getChild("Classifier", ns);
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
	
	 UMLTaggedValueBean toUMLTaggedValue(Element tvElement) {
		if (tvElement.getAttribute("tag") == null) {
			logger.info("taggedValue missing tag attribute, skipping");
			logger.debug("taggedValue missing tag attribute, skipping");
			return null;
		}
		if (tvElement.getAttribute("value") == null) {
			logger.info("taggedValue "
					+ tvElement.getAttribute("tag").getValue()
					+ " missing value attribute, skipping");
			logger.debug("taggedValue "
					+ tvElement.getAttribute("tag").getValue()
					+ " missing value attribute, skipping");

			return null;
		}

		UMLTaggedValueBean tv = new UMLTaggedValueBean(tvElement, tvElement
				.getAttribute("tag").getValue(), tvElement
				.getAttribute("value").getValue());
		return tv;
	}
}