package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

public interface UMLAttribute extends UMLTaggableElement {

  /**
   * @return the attribute name
   */
  public String getName();

  /**
   * @return the visibility (scope)
   */
  public UMLVisibility getVisibility();

  /**
   * @return the attribute's datatype
   */
  public UMLDatatype getDatatype();


}

