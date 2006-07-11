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

      };
  }

  public UMLAttributeWriter getUMLAttributeWriter() {
    return new UMLAttributeWriter() {
        public UMLTaggedValue writeTaggedValue(UMLAttribute att, UMLTaggedValue tv) {
          UMLAttributeBean attBean = (UMLAttributeBean)att;
          Element attElt = attBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(attElt, tv);

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
      };

  }

  public UMLModelWriter getUMLModelWriter() {
    return new UMLModelWriter() {
        public UMLTaggedValue writeTaggedValue(UMLModel model, UMLTaggedValue tv) {
          UMLModelBean modelBean = (UMLModelBean)model;
          Element modelElt = modelBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(modelElt, tv);

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
      };
  }

  public UMLDependencyWriter getUMLDependencyWriter() {
    return new UMLDependencyWriter() {
        public UMLTaggedValue writeTaggedValue(UMLDependency dep, UMLTaggedValue tv) {
          UMLDependencyBean depBean = (UMLDependencyBean)dep;
          Element depElt = depBean.getJDomElement();

          return new GenericTaggedValueWriter().addTaggedValue(depElt, tv);
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
      else 
        meTvElt = new Element("ModelElement.taggedValue", ns);
      
      Element newTvElt = new Element("TaggedValue", ns);
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());

      newTvElt.setAttribute("xmi.id", "EAID_" + java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      
      meTvElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(meTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }
  }

  class ClassTaggedValueWriter {
    public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv) {

      Element rootElement = elt.getDocument().getRootElement();
      Element xmiContentElt = rootElement.getChild("XMI.content");
      
      Namespace ns = elt.getNamespace();
      List<Element> meTvElts = (List<Element>)elt.getChildren("ModelElement.taggedValue", ns);
      
      Attribute xmiId = elt.getAttribute("xmi.id");
      if(xmiId == null)
        return null;

      Element newTvElt = new Element("TaggedValue", ns);
      newTvElt.setAttribute("tag", tv.getName());
      newTvElt.setAttribute("value", tv.getValue());

      newTvElt.setAttribute("xmi.id", java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase());
      newTvElt.setAttribute("modelElement", xmiId.getValue());
      
      xmiContentElt.addContent(newTvElt);
      
      tv = new UMLTaggedValueBean(newTvElt, tv.getName(), tv.getValue());
      
      return tv;
    }
  }

 
 
}