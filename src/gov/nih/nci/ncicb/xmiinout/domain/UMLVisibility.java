/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain;

/**
 * Wrapper class for a UML Visibility.
 */
public interface UMLVisibility {

  /**
   * 
   * @return The visibility name, for example 'public'
   */
  public String getName();

}