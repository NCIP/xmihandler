package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.Collection;

public interface UMLPackage extends UMLTaggableElement {

  /**
   * The package name. 
   */
  public String getName();

  /**
   * @return the parent package or null if this is the root package
   */ 
  public UMLPackage getParent();

  /**
   * All packages under this package
   */ 
  public Collection<UMLPackage> getPackages();

  /**
   * All classes directly under this package
   */ 
  public Collection<UMLClass> getClasses();

}
