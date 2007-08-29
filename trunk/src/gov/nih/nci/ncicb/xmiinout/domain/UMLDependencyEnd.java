package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * All Elements a dependency can point to should implement this interface
 */
public interface UMLDependencyEnd {

  /**
   * @return a List of all Dependencies where one end points to this element. 
   */
  public Set<UMLDependency> getDependencies();

}
