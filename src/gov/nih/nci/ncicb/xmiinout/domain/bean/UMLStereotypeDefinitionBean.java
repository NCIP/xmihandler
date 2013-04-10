/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;


public class UMLStereotypeDefinitionBean extends JDomDomainObject {

  private String name;
  private String xmiId;

  public UMLStereotypeDefinitionBean(org.jdom.Element element, String xmiId, String name) {
    super(element);
    this.xmiId = xmiId;
    this.name = name;
  }


  /**
   * Get the Name name.
   * @return the Name name.
   */
  public String getXmiId() {
    return xmiId;
  }
  
  /**
   * Get the Name name.
   * @return the Name name.
   */
  public String getName() {
    return name;
  }
  
}