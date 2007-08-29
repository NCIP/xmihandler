package gov.nih.nci.ncicb.xmiinout.domain.bean;


import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;

import java.util.*;
import org.jdom.*;


public class UMLGeneralizationBean extends JDomDomainObject implements UMLGeneralization {

  private UMLClass subtype, supertype;

  public UMLGeneralizationBean(Element element, UMLClassBean supertype, UMLClassBean subtype) {
    super(element);

    this.subtype = subtype;
    this.supertype = supertype;

    supertype.addGeneralization(this);
    subtype.addGeneralization(this);
  }

  public UMLClass getSubtype() {
    return subtype;
  }

  public UMLClass getSupertype() {
    return supertype;
  }

}