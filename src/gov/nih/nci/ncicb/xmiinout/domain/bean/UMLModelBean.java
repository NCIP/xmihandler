package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLModelBean extends JDomDomainObject implements UMLModel {

  private List<UMLPackage> packages = new ArrayList();
  private List<UMLClass> classes = new ArrayList<UMLClass>();

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

  private List<UMLAssociation> associations = new ArrayList<UMLAssociation>();
  private List<UMLGeneralization> generalizations = new ArrayList<UMLGeneralization>();
  private List<UMLDependency> dependencies = new ArrayList<UMLDependency>();

  private Map<String, UMLDatatype> datatypes = new HashMap<String, UMLDatatype>();

  private String name;

  public UMLModelBean(org.jdom.Element element) {
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

  public List<UMLPackage> getPackages() {
    return packages;
  }

  public void addPackage(UMLPackage pkg) {
    packages.add(pkg);
  }

  public boolean equals(Object o) {
    if(o instanceof UMLModelBean) {
      UMLModelBean compareTo = (UMLModelBean)o;
      return (compareTo.getName().equals(this.name));
    } else
      return false;
  }

  
  public List<UMLClass> getClasses() {
    return classes;
  }

  public void addClass(UMLClass clazz) {
    classes.add(clazz);
  }

  public UMLTaggedValue getTaggedValue(String name) {
    return taggedValuesMap.get(name);
  }

  public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }

  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLModelWriter().writeTaggedValue(this, taggedValue);

    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    taggedValuesMap.remove(name);
  }

  public void addAssociation(UMLAssociation assoc) {
    associations.add(assoc);
  }

  public List<UMLAssociation> getAssociations() {
    return associations;
  }


  public void addGeneralization(UMLGeneralization generalization) {
    generalizations.add(generalization);
  }

  public List<UMLGeneralization> getGeneralizations() {
    return generalizations;
  }

  public void _addDependency(UMLDependency dependency) 
  {
    dependencies.add(dependency);
  }

  public void addDependency(UMLDependency dependency) {
    if(dependency instanceof UMLDependencyBean) 
    {
      UMLDependencyBean depBean = (UMLDependencyBean)dependency;
      // if dep does not have an element, then we want to 
      if(depBean.getJDomElement() == null) 
      {
        
      }
    }
     
  
    dependencies.add(dependency);
  }

  public List<UMLDependency> getDependencies() {
    return dependencies;
  }

  
  public void addDatatype(UMLDatatype type) {
    datatypes.put(((UMLDatatypeBean)type).getModelId(), type);
  }


  public UMLDependency createDependency(UMLDependencyEnd client,
                                        UMLDependencyEnd supplier, String name)
  {
    return new UMLDependencyBean(null, name, null, client, supplier);
  }
}
