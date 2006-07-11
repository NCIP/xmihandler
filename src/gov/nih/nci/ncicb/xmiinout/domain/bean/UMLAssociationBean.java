package gov.nih.nci.ncicb.xmiinout.domain.bean;


import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;

import java.util.*;
import org.jdom.*;


public class UMLAssociationBean extends JDomDomainObject implements UMLAssociation {

  private List<UMLAssociationEnd> associationEnds = new ArrayList<UMLAssociationEnd>();

  private String roleName;

  public UMLAssociationBean(Element element,
                            String roleName,
                            List<UMLAssociationEnd> ends) {
    super(element);
    this.roleName = roleName;
    associationEnds = new ArrayList<UMLAssociationEnd>(ends);

    for(UMLAssociationEnd end : associationEnds) {
      ((UMLClassBean)(end.getUMLClass())).addAssociation(this);
    }
  }

  public List<UMLAssociationEnd> getAssociationEnds() {
    return associationEnds;
  }

  public String getRoleName() {
    return roleName;
  }

}