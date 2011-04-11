package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLSynchronizedModifier;


/**
 * Wrapper class for a Class synchronized modifier 
 */
public class UMLSynchronizedModifierBean implements UMLSynchronizedModifier {

	  private boolean isSynchronized;

	  public UMLSynchronizedModifierBean(String isSynchronized) {
	    this.isSynchronized = isSynchronized.equalsIgnoreCase("true") ? true : false;
	  }

	  public boolean isSynchronized() {
	    return isSynchronized;
	  }

	}