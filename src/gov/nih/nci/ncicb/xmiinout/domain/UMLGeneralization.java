package gov.nih.nci.ncicb.xmiinout.domain;

/**
 * Generalization or inheritance between 2 classes or 2 interfaces. 
 */
public interface UMLGeneralization {

  /**
   * @return the sub type, or child class
   */
  public Object getSubtype();

  /**
   * @return the super type, or parent class
   */
  public Object getSupertype();

}