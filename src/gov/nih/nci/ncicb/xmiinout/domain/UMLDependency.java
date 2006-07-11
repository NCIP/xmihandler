package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

public interface UMLDependency extends UMLTaggableElement {

  /**
   * @return the class name
   */
  public String getName();
  
  /**
   * @return the visibility (scope)
   */
  public UMLVisibility getVisibility();


  public UMLDependencyEnd getClient();

  public UMLDependencyEnd getSupplier();

}

