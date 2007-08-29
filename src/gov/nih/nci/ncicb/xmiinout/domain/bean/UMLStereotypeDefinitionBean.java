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