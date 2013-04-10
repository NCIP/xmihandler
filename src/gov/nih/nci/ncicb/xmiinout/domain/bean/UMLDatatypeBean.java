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

public class UMLDatatypeBean extends JDomDomainObject implements UMLDatatype {

  private String name;

  public UMLDatatypeBean(org.jdom.Element element, String name) {
    super(element);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}