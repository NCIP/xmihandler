package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;

import java.util.*;

public abstract class UMLElementBean extends JDomDomainObject {

  private String stereotype;

  private String name;
  
  private Collection<UMLTaggedValue> taggedValues = new ArrayList<UMLTaggedValue>();

  public UMLElementBean(org.jdom.Element element) {
    super(element);
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

  public Collection<UMLTaggedValue> getTaggedValues() {
    return taggedValues;
  }
 
  public void addTaggedValue(UMLTaggedValue taggedValue) {
    taggedValues.add(taggedValue);
  }

  public UMLTaggedValue getTaggedValue(String name) {
    for(UMLTaggedValue tv : taggedValues) {
      if(tv.getName().equals(name))
        return tv;
    }

    return null;
  }

  public String getStereotype() {
    return stereotype;
  }
  public void setStereotype(String stereotype) {
    this.stereotype = stereotype;
  }


}


