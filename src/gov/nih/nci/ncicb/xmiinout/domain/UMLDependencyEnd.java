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
 * All Elements a dependency can point to should implement this interface
 */
public interface UMLDependencyEnd {

  /**
   * @return a List of all Dependencies where one end points to this element. 
   */
  public Set<UMLDependency> getDependencies();

}
