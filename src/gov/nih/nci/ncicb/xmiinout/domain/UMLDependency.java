package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * Represents a dependency between 2 UML Elements. 
 * Usually represented by a dashed line in UML.
 * <br> A Dependency has 2 ends, called client and supplier
 */
public interface UMLDependency extends UMLTaggableElement {

  /**
   * @return the class name
   */
  public String getName();
  
  /**
   * @return the visibility (scope)
   */
  public UMLVisibility getVisibility();

  /**
   * 
   * @return the client end
   */
  public UMLDependencyEnd getClient();

  /**
   * 
   * @return the supplier end
   */
  public UMLDependencyEnd getSupplier();

}

