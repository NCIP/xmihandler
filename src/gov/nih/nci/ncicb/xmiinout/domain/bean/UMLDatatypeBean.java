package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLDatatypeBean extends JDomDomainObject implements UMLDatatype {

  private String name;

  public UMLDatatypeBean(org.jdom.Element element, String name) {
    super(element);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}