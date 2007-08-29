package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * Represents a UML Association between UML Classes
 */
public interface UMLAssociation extends UMLTaggableElement {

  /**
   * @return a List of at least two Association Ends. 
   */
  public List<UMLAssociationEnd> getAssociationEnds();

  /**
   * @return the association's role name, or null if there isn't any
   */
  public String getRoleName();
  

}