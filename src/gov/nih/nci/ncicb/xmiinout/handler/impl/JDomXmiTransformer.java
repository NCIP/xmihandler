package gov.nih.nci.ncicb.xmiinout.handler.impl;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.domain.bean.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import org.apache.log4j.Logger;

import java.util.*;

class JDomXmiTransformer {

	private static Map<String, UMLDatatype> datatypes = new HashMap<String, UMLDatatype>();

	private static Map<String, UMLTagDefinition> tagDefinitions = new HashMap<String, UMLTagDefinition>();

	private static List<UMLAttributeBean> attWithMissingDatatypes = new ArrayList<UMLAttributeBean>();

	private static Logger logger = Logger.getLogger(JDomXmiTransformer.class
			.getName());

	static void addDatatype(UMLDatatype datatype) {
		if (datatype instanceof UMLDatatypeBean)
			datatypes.put(((UMLDatatypeBean) datatype).getModelId(), datatype);
		else if (datatype instanceof UMLClassBean)
			datatypes.put(((UMLClassBean) datatype).getModelId(), datatype);
	}

	static void addTagDefinition(UMLTagDefinition tagDefinition) {
		tagDefinitions.put(((UMLTagDefinition) tagDefinition).getXmiId(),
				tagDefinition);
	}

	static UMLDatatypeBean toUMLDatatype(Element typeElt) {
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

	static UMLModelBean toUMLModel(Element modelElement) {
		UMLModelBean model = new UMLModelBean(modelElement);
		model.setName(modelElement.getAttribute("name").getValue());
		return model;
	}

	static UMLPackageBean toUMLPackage(Element packageElement) {
		UMLPackageBean pkg = new UMLPackageBean(packageElement, packageElement
				.getAttribute("name").getValue());
		return pkg;
	}

	static UMLClassBean toUMLClass(Element classElement) {
		Namespace ns = Namespace.getNamespace("omg.org/UML1.3");

		Attribute visibilityAtt = classElement.getAttribute("visibility");
		String visibility = visibilityAtt != null ? visibilityAtt.getValue()
				: null;
		UMLVisibility umlVis = new UMLVisibilityBean(visibility);

		String stereotype = null;
		List<Element> elts = (List<Element>) classElement.getChildren(
				"ModelElement.stereotype", ns);
		if (elts.size() > 0) {
			Element modelStElt = elts.get(0);
			List<Element> stElts = (List<Element>) modelStElt.getChildren(
					"Stereotype", ns);
			if (stElts.size() > 0) {
				Element stElt = stElts.get(0);
				stereotype = stElt.getAttribute("name").getValue();
			}
		}

		UMLClassBean clazz = new UMLClassBean(classElement, classElement
				.getAttribute("name").getValue(), umlVis, stereotype);

		clazz.setModelId(classElement.getAttribute("xmi.id").getValue());

		addDatatype(clazz);
		return clazz;
	}

	static UMLAttributeBean toUMLAttribute(Element attElement) {
		Attribute visibilityAtt = attElement.getAttribute("visibility");
		String visibility = visibilityAtt != null ? visibilityAtt.getValue()
				: null;
		UMLVisibility umlVis = new UMLVisibilityBean(visibility);

		UMLDatatype datatype = null;

		Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
		Element sfTypeElement = attElement.getChild("StructuralFeature.type",
				ns);
		if (sfTypeElement != null) {
			Element classifElt = sfTypeElement.getChild("Classifier", ns);
			if (classifElt != null) {
				Attribute typeAtt = classifElt.getAttribute("xmi.idref");
				if (typeAtt != null) {
					String typeId = typeAtt.getValue();
					datatype = datatypes.get(typeId);
				}
			}
		}

		UMLAttributeBean att = new UMLAttributeBean(attElement, attElement
				.getAttribute("name").getValue(), datatype, umlVis);

		// maybe we haven't discovered the datatype yet.
		if (datatype == null)
			attWithMissingDatatypes.add(att);

		return att;
	}

	/**
	 * Run this after you've processed attributes for attributes that use
	 * classes as datatypes.
	 */
	static void completeAttributes() {
		for (UMLAttributeBean att : attWithMissingDatatypes) {
			Element attElement = att.getJDomElement();

			Namespace ns = Namespace.getNamespace("omg.org/UML1.3");
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

	static UMLTagDefinitionBean toUMLTagDefinition(Element tdElement) {
		//
		// EA
		// None
		//
		// ArgoUML
		// <UML:TagDefinition xmi.id="-64--88-1-107-8238f4:1121acba21f:-8000:000000000000317C" name="type" isSpecification="false" tagType="String" />
		//	  	  
		Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");

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

	static UMLTaggedValueBean toUMLTaggedValue(Element tvElement) {
		if (tvElement.getAttribute("tag") == null) {
			logger.info("taggedValue missing tag attribute, skipping");
			System.out.println("taggedValue missing tag attribute, skipping");
			return null;
		}
		if (tvElement.getAttribute("value") == null) {
			logger.info("taggedValue "
					+ tvElement.getAttribute("tag").getValue()
					+ " missing value attribute, skipping");
			System.out.println("taggedValue "
					+ tvElement.getAttribute("tag").getValue()
					+ " missing value attribute, skipping");

			return null;
		}

		UMLTaggedValueBean tv = new UMLTaggedValueBean(tvElement, tvElement
				.getAttribute("tag").getValue(), tvElement
				.getAttribute("value").getValue());
		return tv;
	}

	static UMLTaggedValueBean toArgoUMLTaggedValue(Element tvElement) {
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

		Namespace ns = Namespace.getNamespace("org.omg.xmi.namespace.UML");
		Element dataValueElement = tvElement.getChild("TaggedValue.dataValue",
				ns);
		Element typeElement = tvElement.getChild("TaggedValue.type", ns);

		if (typeElement == null) {
			logger.info("taggedValue "
					+ tvElement.getAttribute("xmi.id").getValue()
					+ " missing 'TagDefinition' element, skipping");
			System.out.println("taggedValue "
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

		System.out.println("*** tagDefinition name: "
				+ tagDefinitions.get(tagDefinitionElement.getAttributeValue("xmi.idref")).getName());		

		System.out.println("*** dataValue: "
				+ dataValueElement.getText());

		UMLTaggedValueBean tv = new UMLTaggedValueBean(tvElement,
				tagDefinitions.get(tagDefinitionElement.getAttributeValue("xmi.idref")).getName(),
				dataValueElement.getText());
		return tv;
	}

	// static UMLGeneralizationBean toUMLGeneralization(Element genElement) {
	// String subtypeId = genElement.getAttribute("subtype").getValue();
	// String supertypeId = genElement.getAttribute("supertype").getValue();

	// return null;

	// }

}