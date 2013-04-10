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

public class UMLAttributeBean extends JDomDomainObject implements UMLAttribute {

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

  private UMLVisibility visibility;
  private String name;
  private UMLDatatype datatype;

  public UMLAttributeBean(org.jdom.Element element,
                          String attributeName, 
                          UMLDatatype datatype,
                          UMLVisibility visibility) {
    super(element);
    this.name = attributeName;
    this.visibility = visibility;
    this.datatype = datatype;
  }


  /**
   * Get the Name value.
   * @return the Name value.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the Name value.
   * @param newName The new Name value.
   */
  public void setName(String newName) {
    this.name = newName;
  }

  public UMLTaggedValue getTaggedValue(String name) {
    return taggedValuesMap.get(name);
  }

  public UMLTaggedValue getTaggedValue(String name, boolean ignoreCase) {
    if(!ignoreCase)
      return getTaggedValue(name);

    for(String tvName : taggedValuesMap.keySet()) {
      if(tvName.equalsIgnoreCase(name)) {
        return taggedValuesMap.get(tvName);
      }
    }
    
    return null;
  }

  public Collection<UMLTaggedValue> getTaggedValues() {
    return new ArrayList(taggedValuesMap.values());
  }

  public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }
  
  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLAttributeWriter().writeTaggedValue(this, taggedValue);

    
    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    UMLTaggedValue tv = taggedValuesMap.remove(name);

    if(tv != null) {
      writer.getUMLAttributeWriter().removeTaggedValue(this, tv);
    }

  }

  public UMLVisibility getVisibility() {
    return visibility;
  }

  public void setVisibility(UMLVisibility visibility) {
    this.visibility = visibility;
  }

  public UMLDatatype getDatatype() {
    return datatype;
  }

  public void _setDatatype(UMLDatatype datatype) {
    this.datatype = datatype;
  }
}
