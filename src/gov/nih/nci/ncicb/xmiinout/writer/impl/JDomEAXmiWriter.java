package gov.nih.nci.ncicb.xmiinout.writer.impl;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.domain.bean.*;
import gov.nih.nci.ncicb.xmiinout.writer.*;

import org.jdom.*;

import java.util.*;

public class JDomEAXmiWriter implements UMLWriter {

  public UMLClassWriter getUMLClassWriter() {
    return new UMLClassWriter() {
        public UMLTaggedValue writeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
          UMLClassBean clazzBean = (UMLClassBean)clazz;
          Element clazzElt = clazzBean.getJDomElement();

          return new ClassTaggedValueWriter().addTaggedValue(clazzElt, tv);
        }


        public void removeTaggedValue(UMLClass clazz, UMLTaggedValue tv) {
          UMLClassBean clazzBean = (UMLClassBean)clazz;
          Element clazzElt = clazzBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(clazzElt, tv);
        }

      };
  }

  public UMLAttributeWriter getUMLAttributeWriter() {
    return new UMLAttributeWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
          UMLAttributeBean attBean = (UMLAttributeBean)att;
          Element attElt = attBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(attElt, tv);

        }

        public void removeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
          UMLAttributeBean attBean = (UMLAttributeBean)att;
          Element attElt = attBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(attElt, tv);
        }

      };
  }

  public UMLAssociationWriter getUMLAssociationWriter() {
    return new UMLAssociationWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {
          UMLAssociationBean azzBean = (UMLAssociationBean)azz;
          Element azzElt = azzBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(azzElt, tv);

        }

        public void removeTaggedValue(UMLAssociation azz, UMLTaggedValue tv) {
          UMLAssociationBean azzBean = (UMLAssociationBean)azz;
          Element azzElt = azzBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(azzElt, tv);
        }

      };
  }

  public UMLAssociationEndWriter getUMLAssociationEndWriter() {
    return new UMLAssociationEndWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end, UMLTaggedValue tv) {
          UMLAssociationEndBean endBean = (UMLAssociationEndBean)end;
          Element endElt = endBean.getJDomElement();

          return new AttributeTaggedValueWriter().addTaggedValue(endElt, tv);

        }

        public void removeTaggedValue(UMLAssociationEnd end, UMLTaggedValue tv) {
          UMLAssociationEndBean endBean = (UMLAssociationEndBean)end;
          Element endElt = endBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(endElt, tv);
        }

      };
  }

  public UMLTaggedValueWriter getUMLTaggedValueWriter() {
    
    return new UMLTaggedValueWriter() {
        public void writeValue(UMLTaggedValue taggedValue) {
          UMLTaggedValueBean tvb = (UMLTaggedValueBean)taggedValue;

          Element tvElt = tvb.getJDomElement();
          
          Attribute valueAtt = tvElt.getAttribute("value");
          valueAtt.setValue(taggedValue.getValue());
        }

		public UMLTaggedValue writeTaggedValue(JDomDomainObject domainObjElt, UMLTaggedValue taggedValue) {
			return new GenericTaggedValueWriter().addTaggedValue(domainObjElt.getJDomElement(), taggedValue);
		}     

		public void removeTaggedValue(JDomDomainObject domainObjElt, UMLTaggedValue taggedValue) {
			new GenericTaggedValueWriter().removeTaggedValue(domainObjElt.getJDomElement(), taggedValue);
		} 		
      };

  }

  public UMLModelWriter getUMLModelWriter() {
    return new UMLModelWriter() {
        public UMLTaggedValue writeTaggedValue(UMLModel model, UMLTaggedValue tv) {
          UMLModelBean modelBean = (UMLModelBean)model;
          Element modelElt = modelBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(modelElt, tv);

        }

        public void removeTaggedValue(UMLModel model, UMLTaggedValue tv) {
          UMLModelBean modelBean = (UMLModelBean)model;
          Element modelElt = modelBean.getJDomElement();

          new GenericTaggedValueWriter().removeTaggedValue(modelElt, tv);
        }

        
        public UMLDependency writeDependency(UMLModel model, UMLDependency dependency) {
          UMLModelBean modelBean = (UMLModelBean)model;
          Element modelElt = modelBean.getJDomElement();

          Namespace ns = modelElt.getNamespace();

          try {
            JDomDomainObject clientObj = (JDomDomainObject)dependency.getClient();
            
            JDomDomainObject supplierObj = (JDomDomainObject)dependency.getSupplier();

            Element depElement = new Element("Dependency", ns);
            depElement.setAttribute("client", clientObj.getModelId());
            depElement.setAttribute("supplier", supplierObj.getModelId());
            depElement.setAttribute("name", dependency.getName());

            if(dependency.getVisibility() != null) {
              depElement.setAttribute("visibility", dependency.getVisibility().getName());
            }

            depElement.setAttribute("xmi.id", "EAID_" + java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());

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
          UMLPackageBean packageBean = (UMLPackageBean)pkg;
          Element pkgElt = packageBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(pkgElt, tv);

        }
        public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue tv) {
          UMLPackageBean pkgBean = (UMLPackageBean)pkg;
          Element pkgElt = pkgBean.getJDomElement();
          
          new GenericTaggedValueWriter().removeTaggedValue(pkgElt, tv);
        }
      };
  }

  public UMLDependencyWriter getUMLDependencyWriter() {
    return new UMLDependencyWriter() {
        public UMLTaggedValue writeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
          UMLDependencyBean depBean = (UMLDependencyBean)dep;
          Element depElt = depBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(depElt, tv);
        }
        public void removeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
          UMLDependencyBean depBean = (UMLDependencyBean)dep;
          Element depElt = depBean.getJDomElement();
          
          new GenericTaggedValueWriter().removeTaggedValue(depElt, tv);
        }

      };
  }


  class GenericTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
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
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());

//       newTvElt.setAttribute("xmi.id", "EAID_" + java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      
      meTvElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(meTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }

    public void removeTaggedValue(Element elt, UMLTaggedValue tv) {
      UMLTaggedValueBean tvBean = (UMLTaggedValueBean)tv;
      Namespace ns = elt.getNamespace();
      List<Element> meTvElts = (List<Element>)elt.getChildren("ModelElement.taggedValue", ns);
      
      Element meTvElt = null;
      if(meTvElts.size() > 0)
        meTvElt = meTvElts.get(0);
    
      if(meTvElt == null || !meTvElt.removeContent(tvBean.getJDomElement())) { // try to remove from elt itself, if not, do the following
        Element rootElement = elt.getDocument().getRootElement();
        Element xmiContentElt = rootElement.getChild("XMI.content");
        xmiContentElt.removeContent(tvBean.getJDomElement());
      }
    }

  }

  class ClassTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {

      Element rootElement = elt.getDocument().getRootElement();
      Element xmiContentElt = rootElement.getChild("XMI.content");
      
      Namespace ns = elt.getNamespace();
      
      Attribute xmiId = elt.getAttribute("xmi.id");
      if(xmiId == null)
        return null;

      Element newTvElt = new Element("TaggedValue", ns);
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());

      newTvElt.setAttribute("xmi.id", "EAID_" + java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      newTvElt.setAttribute("modelElement", xmiId.getValue());
      
      xmiContentElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }
  }

  class AttributeTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {
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
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());
      newTvElt.setAttribute("xmi.id", "EAID_" + java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      
      meTvElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());
      
      return tv;
    } 
  }
 
}