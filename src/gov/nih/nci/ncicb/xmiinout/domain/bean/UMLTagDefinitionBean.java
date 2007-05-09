package gov.nih.nci.ncicb.xmiinout.domain.bean;

public class UMLTagDefinitionBean extends JDomDomainObject {

  private String xmiId;
  private String name;  
  private boolean newTagDefinition;

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

  /**
   * Set the Name value.
   * @param newName The new Name value.
   */
  public void setName(String newName) {
    this.name = newName;
    //writer.getUMLTagDefinitionWriter().writeValue(this);
  }

  public boolean isNewTagDefinition() {
	  return newTagDefinition;
  }

  public void setNewTagDefinition(boolean newTagDefinition) {
	  this.newTagDefinition = newTagDefinition;
  }
  
  
}
