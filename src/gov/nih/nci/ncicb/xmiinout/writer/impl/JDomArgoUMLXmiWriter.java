package gov.nih.nci.ncicb.xmiinout.writer.impl;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAssociationBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAssociationEndBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAttributeBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLClassBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLDependencyBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLInterfaceBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLModelBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLPackageBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLStereotypeDefinitionBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTagDefinitionBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTaggedValueBean;
import gov.nih.nci.ncicb.xmiinout.handler.impl.ArgoJDomXmiTransformer;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAssociationEndWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAssociationWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAttributeWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLClassWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLDependencyWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLInterfaceWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLModelWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLPackageWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLStereotypeWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLTaggedValueWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLWriter;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class JDomArgoUMLXmiWriter implements UMLWriter {

	private static Logger logger = Logger.getLogger(JDomArgoUMLXmiWriter.class
			.getName());

	private ArgoJDomXmiTransformer argoJDomXmiTransformer = new ArgoJDomXmiTransformer();

	static private Map<String, UMLTagDefinitionBean> tagDefinitionsByNameMap = new HashMap<String, UMLTagDefinitionBean>();

	static private Map<String, UMLTagDefinitionBean> tagDefinitionsByXmiIdMap = new HashMap<String, UMLTagDefinitionBean>();

	public UMLClassWriter getUMLClassWriter() {
		return new UMLClassWriter() {
			public UMLTaggedValue writeTaggedValue(UMLClass clazz,
					UMLTaggedValue tv) {
				UMLClassBean clazzBean = (UMLClassBean) clazz;
				Element clazzElt = clazzBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(clazzElt, tv);
			}

			public void removeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
				UMLClassBean clazzBean = (UMLClassBean) clazz;
				Element clazzElt = clazzBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(clazzElt, tv);
			}
		};
	}

	public UMLInterfaceWriter getUMLInterfaceWriter() {
		return new UMLInterfaceWriter() {

			public UMLTaggedValue writeTaggedValue(UMLInterface interfaze,
					UMLTaggedValue tv) {

				UMLInterfaceBean interfaceBean = (UMLInterfaceBean) interfaze;
				Element interfaceElt = interfaceBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(interfaceElt, tv);
			}

			public void removeTaggedValue(UMLInterface interfaze, UMLTaggedValue tv) {

				UMLInterfaceBean interfaceBean = (UMLInterfaceBean) interfaze;
				Element interfaceElt = interfaceBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(interfaceElt, tv);
			}

		};
	}
	
	public UMLAttributeWriter getUMLAttributeWriter() {
		return new UMLAttributeWriter() {

			public UMLTaggedValue writeTaggedValue(UMLAttribute att,
					UMLTaggedValue tv) {

				UMLAttributeBean attBean = (UMLAttributeBean) att;
				Element attElt = attBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(attElt, tv);
			}

			public void removeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {

				UMLAttributeBean attBean = (UMLAttributeBean) att;
				Element attElt = attBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(attElt, tv);
			}

		};
	}

	public UMLAssociationWriter getUMLAssociationWriter() {
		return new UMLAssociationWriter() {

			public UMLTaggedValue writeTaggedValue(UMLAssociation azz,
					UMLTaggedValue tv) {

				UMLAssociationBean azzBean = (UMLAssociationBean) azz;
				Element azzElt = azzBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(azzElt, tv);
			}

			public void removeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {

				UMLAssociationBean azzBean = (UMLAssociationBean) azz;
				Element azzElt = azzBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(azzElt, tv);
			}

		};
	}

	public UMLAssociationEndWriter getUMLAssociationEndWriter() {
		return new UMLAssociationEndWriter() {
			public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end,
					UMLTaggedValue tv) {

				UMLAssociationEndBean endBean = (UMLAssociationEndBean) end;
				Element endElt = endBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(endElt, tv);
			}

			public void removeTaggedValue(UMLAssociationEnd end,
					UMLTaggedValue tv) {
				UMLAssociationEndBean endBean = (UMLAssociationEndBean) end;
				Element endElt = endBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(endElt, tv);
			}

		};
	}

	public UMLModelWriter getUMLModelWriter() {
		return new UMLModelWriter() {

			public UMLTaggedValue writeTaggedValue(UMLModel model,
					UMLTaggedValue tv) {

				UMLModelBean modelBean = (UMLModelBean) model;
				Element modelElt = modelBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(modelElt, tv);
			}

			public void removeTaggedValue(UMLModel model, UMLTaggedValue tv) {
				UMLModelBean modelBean = (UMLModelBean) model;
				Element modelElt = modelBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(modelElt, tv);
			}

			public UMLDependency writeDependency(UMLModel model,
					UMLDependency dependency) {
				UMLModelBean modelBean = (UMLModelBean) model;
				Element modelElt = modelBean.getJDomElement();

				Namespace ns = modelElt.getNamespace();

				try {
					JDomDomainObject clientObj = (JDomDomainObject) dependency
							.getClient();

					JDomDomainObject supplierObj = (JDomDomainObject) dependency
							.getSupplier();

					Element depElement = new Element("Dependency", ns);

					Element clientElement = new Element("Dependency.client", ns);
					Element clientClassElement = new Element("Class", ns);
					clientClassElement.setAttribute("xmi.idref", clientObj
							.getModelId());

					clientElement.addContent(clientClassElement);
					depElement.addContent(clientElement);

					Element supplierElement = new Element(
							"Dependency.supplier", ns);
					Element supplierClassElement = new Element("Class", ns);
					supplierClassElement.setAttribute("xmi.idref", supplierObj
							.getModelId());

					supplierElement.addContent(supplierClassElement);
					depElement.addContent(supplierElement);

					depElement.setAttribute("name", dependency.getName());

					if (dependency.getVisibility() != null) {
						depElement.setAttribute("visibility", dependency
								.getVisibility().getName());
					}

					depElement.setAttribute("xmi.id", java.util.UUID
							.randomUUID().toString().replace('-', '_')
							.toUpperCase());

					Element supplierElt = supplierObj.getJDomElement();
					supplierElt.getParentElement().addContent(depElement);

					return new UMLDependencyBean(depElement, dependency
							.getName(), dependency.getVisibility(), dependency
							.getClient(), dependency.getSupplier(), null);

				} catch (ClassCastException e) {
					throw new IllegalArgumentException(
							"Incorrect Input Dependency. root -- "
									+ e.getMessage());
				}

			}

		};
	}

	public UMLPackageWriter getUMLPackageWriter() {
		return new UMLPackageWriter() {

			public UMLTaggedValue writeTaggedValue(UMLPackage pkg,
					UMLTaggedValue tv) {
				UMLPackageBean pkgBean = (UMLPackageBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(pkgElt, tv);
			}

			public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue tv) {
				UMLPackageBean pkgBean = (UMLPackageBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(pkgElt, tv);
			}
		};
	}

	public UMLDependencyWriter getUMLDependencyWriter() {
		return new UMLDependencyWriter() {

			public UMLTaggedValue writeTaggedValue(UMLDependency dep,
					UMLTaggedValue tv) {
				UMLDependencyBean depBean = (UMLDependencyBean) dep;
				Element depElt = depBean.getJDomElement();

				return new UMLTagValueWriter().addTaggedValue(depElt, tv);
			}

			public void removeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
				UMLDependencyBean depBean = (UMLDependencyBean) dep;
				Element depElt = depBean.getJDomElement();

				new UMLTagValueWriter().removeTaggedValue(depElt, tv);
			}

			public void writeStereotype(UMLDependency dep, String stereotype) {
				UMLDependencyBean depBean = (UMLDependencyBean) dep;
				Element depElt = depBean.getJDomElement();

				new StereotypeWriter().addStereotype(depElt, stereotype);
			}

			public void removeStereotype(UMLDependency dep, String stereotype) {
				UMLDependencyBean depBean = (UMLDependencyBean) dep;
				Element depElt = depBean.getJDomElement();

				new StereotypeWriter().removeStereotype(depElt, stereotype);
			}

		};
	}

	public UMLStereotypeWriter getUMLStereotypeWriter() {
		return new StereotypeWriter();
	}

	private class StereotypeWriter implements UMLStereotypeWriter {

		public void addStereotype(Element elt, String stereotype) {
			Namespace ns = elt.getNamespace();
			List<Element> meStereotypeElts = (List<Element>) elt.getChildren(
					"ModelElement.stereotype", ns);

			Element meStereotypeElt = null;
			if (meStereotypeElts.size() > 0)
				meStereotypeElt = meStereotypeElts.get(0);
			else {
				meStereotypeElt = new Element("ModelElement.stereotype", ns);
				elt.addContent(meStereotypeElt);
			}

			Element newStereotypeElt = new Element("Stereotype", ns);

			UMLStereotypeDefinitionBean stereotypeDef = argoJDomXmiTransformer
					.getStereotypeDefinition(stereotype);
			if (stereotypeDef == null) {
				logger.debug("Adding Stereotype Definition for Name:  " + stereotype);
				stereotypeDef = argoJDomXmiTransformer.addStereotypeDefinition(
						elt, stereotype);
			}

			logger.debug("Stereotype Definition xmi.id: "
					+ stereotypeDef.getXmiId());
			newStereotypeElt
					.setAttribute("xmi.idref", stereotypeDef.getXmiId());

			meStereotypeElt.addContent(newStereotypeElt);

		}

		public void removeStereotype(Element elt, String stereotype) {
			logger.debug("stereotype: " + stereotype);
			UMLStereotypeDefinitionBean stereotypeDef = argoJDomXmiTransformer
					.getStereotypeDefinition(stereotype);
			if (stereotypeDef != null) {
				Namespace ns = elt.getNamespace();
				List<Element> meStereotypeElts = (List<Element>) elt
						.getChildren("ModelElement.stereotype", ns);

				logger.debug("Removing Stereotype: " + stereotype
						+ " with xmi.id: " + stereotypeDef.getModelId()
						+ " from Element: " + elt.getAttributeValue("name"));
				logger.debug("meStereotypeElts.size(): "
						+ meStereotypeElts.size());

				Element meStereotypeElt = null;
				if (meStereotypeElts.size() > 0)
					meStereotypeElt = meStereotypeElts.get(0);

				if (meStereotypeElt == null
						|| !meStereotypeElt.removeChild("Stereotype", ns)) { // try to remove from elt itself, if not, do the following
					logger.error("Was not able to remove stereotype: "
							+ stereotype + " from Element: " + elt.getName());
				}
			}
		}
	}

	public UMLTaggedValueWriter getUMLTaggedValueWriter() {
		return new UMLTagValueWriter();
	}

	private class UMLTagValueWriter implements UMLTaggedValueWriter {

		public void writeTaggedValue(UMLTaggedValue taggedValue) {
			UMLTaggedValueBean tvb = (UMLTaggedValueBean) taggedValue;

			Element tvElt = tvb.getJDomElement();

			Attribute valueAtt = tvElt.getAttribute("value");
			valueAtt.setValue(taggedValue.getValue());
		}

		public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
			Namespace ns = elt.getNamespace();
			List<Element> meTvElts = (List<Element>) elt.getChildren(
					"ModelElement.taggedValue", ns);

			Element meTvElt = null;
			if (meTvElts.size() > 0)
				meTvElt = meTvElts.get(0);
			else {
				meTvElt = new Element("ModelElement.taggedValue", ns);
				elt.addContent(meTvElt);
			}

			Element newTvElt = new Element("TaggedValue", ns);
			newTvElt.setAttribute("xmi.id", java.util.UUID.randomUUID()
					.toString().replace('-', '_').toUpperCase());

			Element tvDataValueElt = new Element("TaggedValue.dataValue", ns);
			tvDataValueElt.setText(tv.getValue());

			Element tvTypeElt = new Element("TaggedValue.type", ns);
			Element tvTagDefinition = new Element("TagDefinition", ns);

			logger.debug("Adding Tag Value Tag Definition for Name:  "
					+ tv.getName());

			UMLTagDefinitionBean td = tagDefinitionsByNameMap.get(tv.getName());
			if (td == null) {
				td = tagDefinitionsByXmiIdMap.get(tv.getName());
			}

			if (td == null) { // TD does not exist

				Element rootElt = elt.getDocument().getRootElement();
				logger.debug("Root Element name: " + rootElt.getName());
				Element xmiElt = rootElt.getChild("XMI");
				logger.debug("XMI Element name: " + xmiElt.getName());
				Element xmiContentElt = xmiElt.getChild("XMI.content");
				logger.debug("XMI Content Element name: "
						+ xmiContentElt.getName());

				List<Element> xmiContentChildren = (List<Element>) xmiContentElt
						.getChildren();
				logger.debug("xmiContentChildren Elements found: "
						+ xmiContentChildren.size());

				Element modelElt = null;
				for (Element xmiContentChild : xmiContentChildren) {
					logger.debug("xmiContentChild: "
							+ xmiContentChild.getName());

					if (xmiContentChild.getName().equalsIgnoreCase("Model")) {
						modelElt = xmiContentChild;
					}
				}

				logger.debug("Model Element name: " + modelElt.getName());

				Element ownedElement = modelElt.getChild(
						"Namespace.ownedElement", ns);
				logger.debug("ownedElement name: " + ownedElement.getName());

				String xmiId = java.util.UUID.randomUUID().toString().replace(
						'-', '_').toUpperCase();
				UMLTagDefinitionBean tdBean = new UMLTagDefinitionBean(
						ownedElement, xmiId, tv.getName());

				Element tdElement = new Element("TagDefinition", ns);
				tdElement.setAttribute("xmi.id", xmiId);
				tdElement.setAttribute("name", tv.getName());
				ownedElement.addContent(tdElement);

				tagDefinitionsByXmiIdMap.put(tdBean.getXmiId(), tdBean);
				tagDefinitionsByNameMap.put(tdBean.getName(), tdBean);

				td = tdBean;
			}

			//       UMLTagDefinitionBean td = UMLModelBean.getTagDefinition(tv.getName());
			//       if (td == null){
			//         logger.debug("Adding Tag Definition for Name:  " + tv.getName());
			//         td = UMLModelBean.addTagDefinition(elt, tv.getName());
			//       }

			logger.debug("Tag Definition xmi.id: " + td.getXmiId());

			tvTagDefinition.setAttribute("xmi.idref", td.getXmiId());

			tvTypeElt.addContent(tvTagDefinition);

			newTvElt.addContent(tvDataValueElt);
			newTvElt.addContent(tvTypeElt);

			meTvElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}

		public void removeTaggedValue(Element elt, UMLTaggedValue tv) {
			UMLTaggedValueBean tvBean = (UMLTaggedValueBean) tv;
			Namespace ns = elt.getNamespace();
			List<Element> meTvElts = (List<Element>) elt.getChildren(
					"ModelElement.taggedValue", ns);

			logger.debug("Removing tagged value: " + tv.getName()
					+ " with xmi.id: " + tvBean.getModelId()
					+ " from Element: " + elt.getAttributeValue("name"));
			logger.debug("meTvElts.size(): " + meTvElts.size());

			Element meTvElt = null;
			if (meTvElts.size() > 0)
				meTvElt = meTvElts.get(0);

			if (meTvElt == null
					|| !meTvElt.removeContent(tvBean.getJDomElement())) { // try to remove from elt itself, if not, do the following
				logger.debug("Was not able to remove tagged value: "
						+ tv.getName() + " from Element: " + elt.getName());
				Element rootElement = elt.getDocument().getRootElement();
				Element xmiContentElt = rootElement.getChild("XMI.content");
				xmiContentElt.removeContent(tvBean.getJDomElement());
			}
		}

	}
}