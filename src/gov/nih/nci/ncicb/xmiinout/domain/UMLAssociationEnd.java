/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * Represents the end of an association
 */
public interface UMLAssociationEnd extends UMLTaggableElement {

  /**
   * @return the element (i.e class) this end points to
   */
  public UMLAssociable getUMLElement();

  /**
   * @return the end role name, or null if there isn't any.
   */
  public String getRoleName();
  
  /**
   * @return the low multiplicity.
   */
  public int getLowMultiplicity();

  /**
   * @return the high multiplicity, -1 represents * (or n)
   */
  public int getHighMultiplicity();

  /**
   * @return true is this end is navigable
   */
  public boolean isNavigable();

  /**
   * @return the visibility (scope)
   */
  public UMLVisibility getVisibility();

  /**
   * @return the association this end belongs to.
   */
  public UMLAssociation getOwningAssociation();
  

}