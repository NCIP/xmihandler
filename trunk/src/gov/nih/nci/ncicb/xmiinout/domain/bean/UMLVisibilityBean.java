package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLVisibilityBean implements UMLVisibility {

  private String name;

  public UMLVisibilityBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}