package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

public interface UMLClass extends UMLTaggableElement, UMLDependencyEnd, UMLAssociable, UMLDatatype {

  /**
   * @return the class name
   */
  public String getName();

  /**
   * @return the visibility (scope)
   */
  public UMLVisibility getVisibility();

  /**
   * @return the UMLPackage to which this class belongs
   */
  public UMLPackage getPackage();


  /**
   * @return a List of all the UML Attributes belonging to this class
   */
  public List<UMLAttribute> getAttributes();

  /**
   * @return a Set of all association where at least one end points to this class. 
   */
  public Set<UMLAssociation> getAssociations();
  
  
  /**
   * @return a List of all Generalizations where one end points to this class. 
   */
  public List<UMLGeneralization> getGeneralizations();

  /**
   * @return the class stereotype, or null if there isn't any
   */
  public String getStereotype();
}