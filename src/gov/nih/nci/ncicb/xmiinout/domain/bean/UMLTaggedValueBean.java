package gov.nih.nci.ncicb.xmiinout.domain.bean;


import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;

import java.util.*;

public class UMLTaggedValueBean extends JDomDomainObject implements UMLTaggedValue {

  private String name;
  private String value;

  public UMLTaggedValueBean(org.jdom.Element element,
                            String name,
                            String value) {
    super(element);
    this.name = name;
    this.value = value;
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
  private void setName(String newName) {
    this.name = newName;
  }

  /**
   * Get the Value value.
   * @return the Value value.
   */
  public String getValue() {
    return value;
  }

  /**
   * Set the Value value.
   * @param newValue The new Value value.
   */
  public void setValue(String newValue) {
    this.value = newValue;
    writer.getUMLTaggedValueWriter().writeTaggedValue(this);
  }
  
}
