package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLStaticModifier;

/**
 * Wrapper class for a Class synchronized modifier 
 */
public class UMLStaticModifierBean implements UMLStaticModifier {

	  private boolean isStatic;

	  public UMLStaticModifierBean(String isStatic) {
	    this.isStatic = isStatic.equalsIgnoreCase("true") ? true : false;
	  }

	  public boolean isStatic() {
	    return isStatic;
	  }

}