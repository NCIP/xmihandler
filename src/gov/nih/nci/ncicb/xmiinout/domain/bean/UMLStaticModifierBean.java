/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

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