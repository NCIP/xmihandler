package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import java.util.*;

public class UMLAssociationEndBean extends JDomDomainObject implements UMLAssociationEnd {

  private UMLClass endClass;
  private String roleName;

  private int lowMultiplicity, highMultiplicity;

  private boolean navigable;
  private UMLVisibility visibility;

  public UMLAssociationEndBean
    (org.jdom.Element elt,
     UMLClass clazz,
     String roleName,
     int lowMultiplicity,
     int highMultiplicity,
     boolean navigable
     ) {

    super(elt);
    
    this.endClass = clazz;
    this.roleName = roleName;
    this.lowMultiplicity = lowMultiplicity;
    this.highMultiplicity = highMultiplicity;
    this.navigable = navigable;

  }

  public UMLClass getUMLClass() {
    return endClass;
  }

  public String getRoleName() {
    return roleName;
  }

  public int getLowMultiplicity() {
    return lowMultiplicity;
  }
  public int getHighMultiplicity() {
    return highMultiplicity;
  }

  public boolean isNavigable() {
    return navigable;
  }

  public UMLVisibility getVisibility() {
    return visibility;
  }

}
