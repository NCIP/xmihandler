package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.*;

/**
 * Designates an Element that holds tagged Values
 */
public interface UMLTaggableElement {

  /**
   * Retrieve all tagged value by name.
   *
   * @param name the tagged value name (or key identifier)
   */
  public UMLTaggedValue getTaggedValue(String name);

  /**
   * Retrieve all tagged values for this element.
   */
  public Collection<UMLTaggedValue> getTaggedValues();
  
  /**
   * Add a tagged value to this element
   * @param name the tagged value nam
   * @param value the value of the tagged value
   */
  public UMLTaggedValue addTaggedValue(String name, String value);

  /**
   * remove tagged value from this element.<br>
   * If more than one tagged value exist with this name, all with be removed
   * @param name the tagged value name (or key identifier)
   */
  public void removeTaggedValue(String name);


}