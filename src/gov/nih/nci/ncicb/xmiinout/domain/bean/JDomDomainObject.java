/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.writer.UMLWriter;

import org.jdom.*;

import java.io.Serializable;


public class JDomDomainObject implements Serializable {

  // internal id for this element.
  private static int idCounter = 1;
  private int id;

  private String modelId = null;

  private Element jDomElement;

  protected static UMLWriter writer;

  public JDomDomainObject(Element element) {
    this.jDomElement = element;
    id = idCounter++;
  }

  /**
   * Get the Id value.
   * @return the Id value.
   */
  public int getId() {
    return id;
  }

  public void setModelId(String id) {
    modelId = id;
  }
  public String getModelId() {
    return modelId;
  }
  

  /**
   * Get the GetJDomElement value.
   * @return the GetJDomElement value.
   */
  public Element getJDomElement() {
    return jDomElement;
  }

  public static void setUMLWriter(UMLWriter umlWriter) {
    writer = umlWriter;
  }

  public boolean equals(Object o) {
    if(o instanceof JDomDomainObject) {
      JDomDomainObject jO = (JDomDomainObject)o;
      return jO.getId() == this.id;
    }
    return false;
  }

}