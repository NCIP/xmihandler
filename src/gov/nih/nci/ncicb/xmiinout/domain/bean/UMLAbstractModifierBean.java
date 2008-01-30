package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLAbstractModifierBean implements UMLAbstractModifier {

  private boolean isAbstract;

  public UMLAbstractModifierBean(String isAbstract) {
    this.isAbstract = isAbstract.equalsIgnoreCase("true") ? true : false;
  }

  public boolean isAbstract() {
    return isAbstract;
  }

}