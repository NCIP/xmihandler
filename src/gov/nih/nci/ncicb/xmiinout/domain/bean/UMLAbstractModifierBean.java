/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLAbstractModifierBean implements UMLAbstractModifier {

  private boolean isAbstract;

  public UMLAbstractModifierBean(String isAbstract) {
    this.isAbstract = isAbstract.equalsIgnoreCase("true") ? true : false;
  }

  public boolean isAbstract() {
    return isAbstract;
  }

}