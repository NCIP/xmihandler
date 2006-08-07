package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.List;

/**
 * Reprensents a single model within an XMI file
 */
public interface UMLModel {

  /**
   * @return the model name
   */
  public String getName();

  /**
   * @return a List of packages belonging to this model
   */
  public List<UMLPackage> getPackages();

  /**
   * a List of Classes belonging directly to the model, not under a package.
   */
  public List<UMLClass> getClasses();

  /**
   * All generalizations in this model.
   */
  public List<UMLGeneralization> getGeneralizations();

  /**
   * All dependencies in this model.
   */
  public List<UMLDependency> getDependencies();
 
  /**
   * Add a dependency to the model. A dependency can be created with createDependency.
   * <br> Existing Dependency can not be added this way.
   * 
   * @param dependency
   * @return
   */
  public UMLDependency addDependency(UMLDependency dependency);
 
  /**
   * All associations in this model.
   */
  public List<UMLAssociation> getAssociations();
  
  /**
   * @param client
   * @param supplier
   * @param name Dependency's name
   * @return a non persisted UMLDependency. Use addDependency to persist.
   */
  public UMLDependency createDependency(UMLDependencyEnd client, UMLDependencyEnd supplier, String name);

}