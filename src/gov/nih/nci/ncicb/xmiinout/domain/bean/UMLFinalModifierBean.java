package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLFinalModifier;

public class UMLFinalModifierBean implements UMLFinalModifier {

	  private boolean isFinal;

	  public UMLFinalModifierBean(String isFinal) {
	    this.isFinal = isFinal.equalsIgnoreCase("true") ? true : false;
	  }

	  public boolean isFinal() {
	    return isFinal;
	  }

}