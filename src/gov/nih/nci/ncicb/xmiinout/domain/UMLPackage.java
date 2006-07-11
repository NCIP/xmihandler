package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.Collection;

public interface UMLPackage extends UMLTaggableElement {

  /**
   * The package name. 
   */
  public String getName();

  /**
   * The parent Package
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
