package gov.nih.nci.ncicb.xmiinout.domain;

/**
 * represents a tagged value
 */
public interface UMLTaggedValue {

  /**
   * the tag's name, key, id or tag
   */
  public String getName();

  /**
   * the tag's value
   */
  public String getValue();

  /**
   * Modify a taggedValue's value.
   * @param value the value to change to.
   */
  public void setValue(String value);

}