package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLPackageBean extends JDomDomainObject implements UMLPackage {

  private List<UMLPackage> packages = new ArrayList<UMLPackage>();
  private List<UMLClass> classes = new ArrayList<UMLClass>();

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();


  private UMLPackage parent;
  private String name;

//   public UMLPackageBean(org.jdom.Element element, UMLPackage parent,
//                         String name) {
//     super(element);
//     this.parent = parent;
//     this.name = name;
//   }

  public UMLPackageBean(org.jdom.Element element, 
                        String name) {
    super(element);
    this.name = name;
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


  public List<UMLPackage> getPackages() {
    return packages;
  }

  public UMLPackage getPackage(String name) {
    for(UMLPackage pkg : packages) 
      if(pkg.getName().equals(name))
        return pkg;

    return null;
  }

  public void addPackage(UMLPackage pkg) {
    packages.add(pkg);

    ((UMLPackageBean)pkg).setParent(this);
  }

  public UMLPackage getParent() {
    return parent;
  }

  public void setParent(UMLPackage parent) {
    this.parent = parent;
  }

  public List<UMLClass> getClasses() {
    return classes;
  }

  public UMLClass getClass(String name) {
    for(UMLClass clazz : classes)
      if(clazz.getName().equals(name))
        return clazz;
    
    return null;
  }

  public void addClass(UMLClassBean clazz) {
    classes.add(clazz);
    clazz.setPackage(this);
  }

  public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }

  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLPackageWriter().writeTaggedValue(this, taggedValue);

    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    taggedValuesMap.remove(name);
  }

  public UMLTaggedValue getTaggedValue(String name) {
    return taggedValuesMap.get(name);
  }

  public Collection<UMLTaggedValue> getTaggedValues() {
    return taggedValuesMap.values();
  }
  

}
