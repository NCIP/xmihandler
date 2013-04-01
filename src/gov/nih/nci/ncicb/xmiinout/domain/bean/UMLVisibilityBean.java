/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLVisibilityBean implements UMLVisibility {

  private String name;

  public UMLVisibilityBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}