/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

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