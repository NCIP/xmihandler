package gov.nih.nci.ncicb.xmiinout.domain.bean;


public class UMLStereotypeDefinitionBean extends JDomDomainObject {

  private String name;

  public UMLStereotypeDefinitionBean(org.jdom.Element element, String name) {
    super(element);
    this.name = name;
  }

  public String getName() {
    return name;
  }

}