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
    taggedValuesMap.remove(name);
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
}
