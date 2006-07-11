package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

public interface UMLDependencyEnd {

  /**
   * @return a List of all Dependencies where one end points to this element. 
   */
  public List<UMLDependency> getDependencies();

  public void addDependency(UMLDependency dependency);

}
