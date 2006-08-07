package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * Generalization or inheritance between 2 classes. 
 */
public interface UMLGeneralization {

  /**
   * @return the sub type, or child class
   */
  public UMLClass getSubtype();

  /**
   * @return the super type, or parent class
   */
  public UMLClass getSupertype();

}