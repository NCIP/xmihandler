package gov.nih.nci.ncicb.xmiinout.writer.impl;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTagDefinition;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAssociationBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAssociationEndBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLAttributeBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLClassBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLDependencyBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLModelBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLPackageBean;
import gov.nih.nci.ncicb.xmiinout.domain.bean.UMLTaggedValueBean;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAssociationEndWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAssociationWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLAttributeWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLClassWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLDependencyWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLModelWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLPackageWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLTaggedValueWriter;
import gov.nih.nci.ncicb.xmiinout.writer.UMLWriter;

import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class JDomArgoUMLXmiWriter implements UMLWriter {

	public UMLClassWriter getUMLClassWriter() {
		return new UMLClassWriter() {
			public UMLTaggedValue writeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLClassBean)clazz, tv);
			}


			public void removeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLClassBean)clazz, tv);
			}

		};
	}

	public UMLAttributeWriter getUMLAttributeWriter() {
		return new UMLAttributeWriter() {
			public UMLTaggedValue writeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLAttributeBean)att, tv);
			}

			public void removeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLAttributeBean)att, tv);
			}

		};
	}

	public UMLAssociationWriter getUMLAssociationWriter() {
		return new UMLAssociationWriter() {
			public UMLTaggedValue writeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLAssociationBean)azz, tv);				
			}

			public void removeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLAssociationBean)azz, tv);
			}

		};
	}

	public UMLAssociationEndWriter getUMLAssociationEndWriter() {
		return new UMLAssociationEndWriter() {
			public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLAssociationEndBean)end, tv);				
			}

			public void removeTaggedValue(UMLAssociationEnd end, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLAssociationEndBean)end, tv);
			}

		};
	}

	public UMLModelWriter getUMLModelWriter() {
		return new UMLModelWriter() {

			public UMLTaggedValue writeTaggedValue(UMLModel model, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLModelBean)model, tv);
			}

			public void removeTaggedValue(UMLModel model, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLModelBean)model, tv);
			}

			public UMLDependency writeDependency(UMLModel model, UMLDependency dependency) {
				UMLModelBean modelBean = (UMLModelBean)model;
				Element modelElt = modelBean.getJDomElement();

				Namespace ns = modelElt.getNamespace();

				try {
					JDomDomainObject clientObj = (JDomDomainObject)dependency.getClient();

					JDomDomainObject supplierObj = (JDomDomainObject)dependency.getSupplier();

					Element depElement = new Element("Dependency", ns);

					Element clientElement = new Element("Dependency.client", ns);
					Element clientClassElement = new Element("Class", ns);
					clientClassElement.setAttribute("xmi.idref", clientObj.getModelId());

					clientElement.addContent(clientClassElement);
					depElement.addContent(clientElement);

					Element supplierElement = new Element("Dependency.supplier", ns);
					Element supplierClassElement = new Element("Class", ns);
					supplierClassElement.setAttribute("xmi.idref", supplierObj.getModelId());

					supplierElement.addContent(supplierClassElement);
					depElement.addContent(supplierElement);

					depElement.setAttribute("name", dependency.getName());

					if(dependency.getVisibility() != null) {
						depElement.setAttribute("visibility", dependency.getVisibility().getName());
					}

					depElement.setAttribute("xmi.id", java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());

					Element supplierElt = supplierObj.getJDomElement();
					supplierElt.getParentElement().addContent(depElement);

					return new UMLDependencyBean(depElement, dependency.getName(), dependency.getVisibility(), dependency.getClient(), dependency.getSupplier());


				} catch (ClassCastException e) {
					throw new IllegalArgumentException("Incorrect Input Dependency. root -- " + e.getMessage());
				}

			}

		};
	}

	public UMLPackageWriter getUMLPackageWriter() {
		return new UMLPackageWriter() {
			public UMLTaggedValue writeTaggedValue(UMLPackage pkg, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLPackageBean)pkg, tv);
			}
			public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLPackageBean)pkg, tv);
			}
		};
	}

	public UMLDependencyWriter getUMLDependencyWriter() {
		return new UMLDependencyWriter() {
			
			public UMLTaggedValue writeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
				return new UMLTagValueWriter().writeTaggedValue((UMLDependencyBean)dep, tv);
			}

			public void removeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
				new UMLTagValueWriter().removeTaggedValue((UMLDependencyBean)dep, tv);
			}

		};
	}

	private Element getModel(Element elt){

		Element rootElement = elt;

		while (!(rootElement.isRootElement())){
			rootElement = rootElement.getParentElement();
			System.out.println("Getting model Element; Current element name: " + rootElement.getName());
		}

		return rootElement;
	}

	public UMLTaggedValueWriter getUMLTaggedValueWriter() {
		return new UMLTagValueWriter();
	}
	
	private class UMLTagValueWriter implements UMLTaggedValueWriter {	
		
		public void writeValue(UMLTaggedValue taggedValue) {
			UMLTaggedValueBean tvb = (UMLTaggedValueBean)taggedValue;

			Element tvElt = tvb.getJDomElement();

			Attribute valueAtt = tvElt.getAttribute("value");
			valueAtt.setValue(taggedValue.getValue());
		}

		public UMLTaggedValue writeTaggedValue(JDomDomainObject domainObjElt, UMLTaggedValue tv) {
			Element elt = domainObjElt.getJDomElement();

			return addTaggedValue(elt, tv);
		}

		public void removeTaggedValue(JDomDomainObject domainObjElt, UMLTaggedValue tv) {
			Element elt = domainObjElt.getJDomElement();

			removeTaggedValue(elt, tv);
		}  

		private UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
			Namespace ns = elt.getNamespace();
			List<Element> meTvElts = (List<Element>)elt.getChildren("ModelElement.taggedValue", ns);

			Element meTvElt = null;
			if(meTvElts.size() > 0)
				meTvElt = meTvElts.get(0);
			else {
				meTvElt = new Element("ModelElement.taggedValue", ns);
				elt.addContent(meTvElt);
			}      

			Element newTvElt = new Element("TaggedValue", ns);
			newTvElt.setAttribute("xmi.id", java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());

			Element tvDataValueElt = new Element("TaggedValue.dataValue", ns);
			tvDataValueElt.setText(tv.getValue());

			Element tvTypeElt = new Element("TaggedValue.type", ns);
			Element tvTagDefinition = new Element("TagDefinition",ns);

			System.out.println("Adding Tag Value Tag Definition for Name:  " + tv.getName());
			UMLTagDefinition td = UMLModelBean.getTagDefinition(tv.getName());
			if (td == null){
				System.out.println("Adding Tag Definition for Name:  " + tv.getName());
				td = UMLModelBean.addTagDefinition(elt, tv.getName());
			}

			System.out.println("Tag Definition xmi.id: " + td.getXmiId());

			tvTagDefinition.setAttribute("xmi.idref", td.getXmiId());

			tvTypeElt.addContent(tvTagDefinition);

			newTvElt.addContent(tvDataValueElt);
			newTvElt.addContent(tvTypeElt);      

			meTvElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}
		
		private void removeTaggedValue(Element elt, UMLTaggedValue tv) {
			UMLTaggedValueBean tvBean = (UMLTaggedValueBean)tv;
			Namespace ns = elt.getNamespace();
			List<Element> meTvElts = (List<Element>)elt.getChildren("ModelElement.taggedValue", ns);

			System.out.println("Removing tagged value: " + tv.getName() + " with xmi.id: " + tvBean.getModelId() + " from Element: " + elt.getAttributeValue("name"));
			System.out.println("meTvElts.size(): " + meTvElts.size());
			
			Element meTvElt = null;
			if(meTvElts.size() > 0)
				meTvElt = meTvElts.get(0);
			
			if(meTvElt == null || !meTvElt.removeContent(tvBean.getJDomElement())) { // try to remove from elt itself, if not, do the following
				System.out.println("Was not able to remove tagged value: " + tv.getName() + " from Element: " + elt.getName());
				Element rootElement = elt.getDocument().getRootElement();
				Element xmiContentElt = rootElement.getChild("XMI.content");
				xmiContentElt.removeContent(tvBean.getJDomElement());
			}
		}

	}	
}