/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer.impl;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.domain.bean.*;
import gov.nih.nci.ncicb.xmiinout.handler.impl.ArgoJDomXmiTransformer;
import gov.nih.nci.ncicb.xmiinout.writer.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class JDomEAXmiWriter implements UMLWriter {

  private static Logger logger = Logger.getLogger(JDomEAXmiWriter.class
                                                  .getName());

  public UMLClassWriter getUMLClassWriter() {
    return new UMLClassWriter() {
        public UMLTaggedValue writeTaggedValue(UMLClass clazz,
                                               UMLTaggedValue tv) {
          UMLClassBean clazzBean = (UMLClassBean) clazz;
          Element clazzElt = clazzBean.getJDomElement();

          return new ClassTaggedValueWriter()
            .addTaggedValue(clazzElt, tv);
        }

        public void removeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
          UMLClassBean clazzBean = (UMLClassBean) clazz;
          Element clazzElt = clazzBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(clazzElt, tv);
        }

      };
  }

  public UMLInterfaceWriter getUMLInterfaceWriter() {
    return new UMLInterfaceWriter() {
        public UMLTaggedValue writeTaggedValue(UMLInterface interfaze,
                                               UMLTaggedValue tv) {
          UMLInterfaceBean interfaceBean = (UMLInterfaceBean) interfaze;
          Element interfaceElt = interfaceBean.getJDomElement();

          return new InterfaceTaggedValueWriter()
            .addTaggedValue(interfaceElt, tv);
        }

        public void removeTaggedValue(UMLInterface interfaze, UMLTaggedValue tv) {
          UMLInterfaceBean interfaceBean = (UMLInterfaceBean) interfaze;
          Element interfaceElt = interfaceBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(interfaceElt, tv);
        }

      };
  }

  public UMLAttributeWriter getUMLAttributeWriter() {
    return new UMLAttributeWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAttribute att,
                                               UMLTaggedValue tv) {
          UMLAttributeBean attBean = (UMLAttributeBean) att;
          Element attElt = attBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(attElt,
                                                                 tv);

        }

        public void removeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
          UMLAttributeBean attBean = (UMLAttributeBean) att;
          Element attElt = attBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(attElt, tv);
        }

      };
  }
  
	public UMLOperationWriter getUMLOperationWriter() {
		return new UMLOperationWriter() {

			public UMLTaggedValue writeTaggedValue(UMLOperation pkg,
					UMLTaggedValue tv) {
				UMLOperationBean pkgBean = (UMLOperationBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				return new OperationTaggedValueWriter().addTaggedValue(pkgElt, tv);
			}

			public void removeTaggedValue(UMLOperation pkg, UMLTaggedValue tv) {
				UMLOperationBean pkgBean = (UMLOperationBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				new GenericTaggedValueWriter().removeTaggedValue(pkgElt, tv);
			}
		};
	}
  
	public UMLOperationParameterWriter getUMLOperationParameterWriter() {
		return new UMLOperationParameterWriter() {

			public UMLTaggedValue writeTaggedValue(UMLOperationParameter pkg,
					UMLTaggedValue tv) {
				UMLOperationParameterBean pkgBean = (UMLOperationParameterBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				return new OperationParameterTaggedValueWriter().addTaggedValue(pkgElt, tv);
			}

			public void removeTaggedValue(UMLOperationParameter pkg, UMLTaggedValue tv) {
				UMLOperationParameterBean pkgBean = (UMLOperationParameterBean) pkg;
				Element pkgElt = pkgBean.getJDomElement();

				new GenericTaggedValueWriter().removeTaggedValue(pkgElt, tv);
			}
		};
	}
  

  public UMLAssociationWriter getUMLAssociationWriter() {
    return new UMLAssociationWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAssociation azz,
                                               UMLTaggedValue tv) {
          UMLAssociationBean azzBean = (UMLAssociationBean) azz;
          Element azzElt = azzBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(azzElt,
                                                                 tv);

        }

        public void removeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {
          UMLAssociationBean azzBean = (UMLAssociationBean) azz;
          Element azzElt = azzBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(azzElt, tv);
        }

      };
  }

  public UMLAssociationEndWriter getUMLAssociationEndWriter() {
    return new UMLAssociationEndWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end,
                                               UMLTaggedValue tv) {
          UMLAssociationEndBean endBean = (UMLAssociationEndBean) end;
          Element endElt = endBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(endElt,
                                                                 tv);

        }

        public void removeTaggedValue(UMLAssociationEnd end,
                                      UMLTaggedValue tv) {
          UMLAssociationEndBean endBean = (UMLAssociationEndBean) end;
          Element endElt = endBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(endElt, tv);
        }

      };
  }

  public UMLTaggedValueWriter getUMLTaggedValueWriter() {
    return new GenericTaggedValueWriter();
  }

  public UMLModelWriter getUMLModelWriter() {
    return new UMLModelWriter() {
        public UMLTaggedValue writeTaggedValue(UMLModel model,
                                               UMLTaggedValue tv) {
          UMLModelBean modelBean = (UMLModelBean) model;
          Element modelElt = modelBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(modelElt,
                                                               tv);

        }

        public void removeTaggedValue(UMLModel model, UMLTaggedValue tv) {
          UMLModelBean modelBean = (UMLModelBean) model;
          Element modelElt = modelBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(modelElt, tv);
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
            depElement.setAttribute("client", clientObj.getModelId());
            depElement.setAttribute("supplier", supplierObj
                                    .getModelId());
            depElement.setAttribute("name", dependency.getName());

            if (dependency.getVisibility() != null) {
              depElement.setAttribute("visibility", dependency
                                      .getVisibility().getName());
            }

            depElement.setAttribute("xmi.id", "EAID_"
                                    + java.util.UUID.randomUUID().toString().replace(
                                      '-', '_').toUpperCase());

            Element supplierElt = supplierObj.getJDomElement();
            supplierElt.getParentElement().addContent(depElement);

            return new UMLDependencyBean(depElement, dependency
                                         .getName(), dependency.getVisibility(), dependency
                                         .getClient(), dependency.getSupplier());

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
          UMLPackageBean packageBean = (UMLPackageBean) pkg;
          Element pkgElt = packageBean.getJDomElement();
                
          return new PackageTaggedValueWriter()
            .addTaggedValue(pkgElt, tv);
                
        }
              
        public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue tv) {
          UMLPackageBean pkgBean = (UMLPackageBean) pkg;
          Element pkgElt = pkgBean.getJDomElement();
                
          new GenericTaggedValueWriter().removeTaggedValue(pkgElt, tv);
        }
      };
  }

  public UMLDependencyWriter getUMLDependencyWriter() {
    return new UMLDependencyWriter() {

        public UMLTaggedValue writeTaggedValue(UMLDependency dep,
                                               UMLTaggedValue tv) {
          UMLDependencyBean depBean = (UMLDependencyBean) dep;
          Element depElt = depBean.getJDomElement();

          return new GenericTaggedValueWriter()
            .addTaggedValue(depElt, tv);
        }

        public void removeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
          UMLDependencyBean depBean = (UMLDependencyBean) dep;
          Element depElt = depBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(depElt, tv);
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

    //		<UML:ModelElement.stereotype>
    //			<UML:Stereotype name="DataSource"/>
    //		</UML:ModelElement.stereotype>
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
      newStereotypeElt.setAttribute("name", stereotype);

      meStereotypeElt.addContent(newStereotypeElt);

    }

    public void removeStereotype(Element elt, String stereotype) {

      Namespace ns = elt.getNamespace();
      List<Element> meTvElts = (List<Element>) elt.getChildren(
        "ModelElement.stereotype", ns);

      logger.debug("meTvElts.size(): " + meTvElts.size());

      Element meTvElt = null;
      if (meTvElts.size() > 0)
        meTvElt = meTvElts.get(0);

      if (meTvElt == null || !meTvElt.removeChild("Stereotype", ns)) { // try to remove from elt itself, if not, do the following
        logger.error("Was not able to remove stereotype: " + stereotype
                     + " from Element: " + elt.getName());
      }
    }

  }

  class GenericTaggedValueWriter implements UMLTaggedValueWriter {
    
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
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());
      
      // newTvElt.setAttribute("xmi.id", "EAID_" +
      // java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      
      meTvElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(meTvElt, tv.getName(), tv.getValue());
      
      return tv;
		}
    
    public void removeTaggedValue(Element elt, UMLTaggedValue tv) {
      UMLTaggedValueBean tvBean = (UMLTaggedValueBean) tv;
      Namespace ns = elt.getNamespace();
      List<Element> meTvElts = (List<Element>) elt.getChildren(
        "ModelElement.taggedValue", ns);

      Element meTvElt = null;
      if (meTvElts.size() > 0)
        meTvElt = meTvElts.get(0);
      
      if (meTvElt == null
          || !meTvElt.removeContent(tvBean.getJDomElement())) {
        
        Element rootElement = elt.getDocument().getRootElement();
        Element xmiContentElt = rootElement.getChild("XMI.content");
        boolean b = xmiContentElt.removeContent(tvBean.getJDomElement());
        if (b == false)
          System.out.println("# removed: " +b);

      }
    }
    
  }

  class ClassTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
      
      Element rootElement = elt.getDocument().getRootElement();
      Element xmiContentElt = rootElement.getChild("XMI.content");
      
      Namespace ns = elt.getNamespace();
      
      Attribute xmiId = elt.getAttribute("xmi.id");
      if (xmiId == null)
        return null;
      
      Element newTvElt = new Element("TaggedValue", ns);
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());
      
      newTvElt.setAttribute("xmi.id", "EAID_"
                            + java.util.UUID.randomUUID().toString().replace('-', '_')
                            .toUpperCase());
      newTvElt.setAttribute("modelElement", xmiId.getValue());
      
      xmiContentElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }
  }
	
  class PackageTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
      
      Element rootElement = elt.getDocument().getRootElement();
      Element xmiContentElt = rootElement.getChild("XMI.content");
      
      Namespace ns = elt.getNamespace();
      
//       Attribute xmiId = elt.getAttribute("xmi.id");
//       if (xmiId == null)
//         return null;
      Attribute pkgNameAtt = elt.getAttribute("name");
      if(pkgNameAtt == null)
        return null;

      Attribute xmiId = null;
      try {
        Element parentPkg = elt.getParentElement().getParentElement();
        if(parentPkg.getName().equals("Model")) {
          // if parent is a model, then the ID used is that of the Model itself.
          xmiId = parentPkg.getAttribute("xmi.id");
        } else if (parentPkg.getName().equals("Package")) {
          List<Element> CRElts = parentPkg
            .getChild("Namespace.ownedElement", ns)
            .getChild("Collaboration", ns)
            .getChild("Namespace.ownedElement", ns)
            .getChildren("ClassifierRole", ns);
          for(Element CRElt : CRElts) {
            String nameAtt = CRElt.getAttribute("name").getValue();
            if(nameAtt.equals(pkgNameAtt.getValue())) {
              xmiId = CRElt.getAttribute("xmi.id");
            }
          }
        }
      } catch (NullPointerException e) {
        logger.error("ClassifierRole can't be found for package: " + pkgNameAtt.getValue());
        return null;
      } 


      
      Element newTvElt = new Element("TaggedValue", ns);
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());
      
      newTvElt.setAttribute("xmi.id", "EAID_"
                            + java.util.UUID.randomUUID().toString().replace('-', '_')
                            .toUpperCase());
      newTvElt.setAttribute("modelElement", xmiId.getValue());
      
      xmiContentElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }
  }


  class InterfaceTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
      
      Element rootElement = elt.getDocument().getRootElement();
			Element xmiContentElt = rootElement.getChild("XMI.content");

			Namespace ns = elt.getNamespace();

			Attribute xmiId = elt.getAttribute("xmi.id");
			if (xmiId == null)
				return null;

			Element newTvElt = new Element("TaggedValue", ns);
			newTvElt.setAttribute("tag", tv.getName());
			newTvElt.setAttribute("value", tv.getValue());

			newTvElt.setAttribute("xmi.id", "EAID_"
					+ java.util.UUID.randomUUID().toString().replace('-', '_')
							.toUpperCase());
			newTvElt.setAttribute("modelElement", xmiId.getValue());

			xmiContentElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}
	}

	class AttributeTaggedValueWriter {
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
			newTvElt.setAttribute("tag", tv.getName());
			newTvElt.setAttribute("value", tv.getValue());
			newTvElt.setAttribute("xmi.id", "EAID_"
					+ java.util.UUID.randomUUID().toString().replace('-', '_')
							.toUpperCase());

			meTvElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}
	}

	class OperationTaggedValueWriter {
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
			newTvElt.setAttribute("tag", tv.getName());
			newTvElt.setAttribute("value", tv.getValue());
			newTvElt.setAttribute("xmi.id", "EAID_"
					+ java.util.UUID.randomUUID().toString().replace('-', '_')
							.toUpperCase());

			meTvElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}
	}

	class OperationParameterTaggedValueWriter {
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
			newTvElt.setAttribute("tag", tv.getName());
			newTvElt.setAttribute("value", tv.getValue());
			newTvElt.setAttribute("xmi.id", "EAID_"
					+ java.util.UUID.randomUUID().toString().replace('-', '_')
							.toUpperCase());

			meTvElt.addContent(newTvElt);

			tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());

			return tv;
		}
	}
}