/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

public class UMLTagDefinitionBean extends JDomDomainObject {

  private String xmiId;
  private String name;  

  public UMLTagDefinitionBean(org.jdom.Element element, String id,
                            String name) {
    super(element);
    this.xmiId = id;
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
