package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class UMLClassBean extends JDomDomainObject 
  implements UMLClass, IUMLDependencyEnd, UMLDatatype {

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

  private List<UMLAttribute> attributes = new ArrayList<UMLAttribute>();

  private Set<UMLAssociation> associations = new HashSet<UMLAssociation>();
  private List<UMLGeneralization> generalizations = new ArrayList<UMLGeneralization>();

  private Set<UMLDependency> dependencies = new HashSet<UMLDependency>();

  private UMLAbstractModifier abstractModifier;
  private UMLVisibility visibility;
  private UMLPackageBean umlPkg;

  private String stereotype;

  public UMLClassBean(org.jdom.Element element, 
                      String className,
                      UMLAbstractModifier abstractModifier,
                      UMLVisibility visibility,
                      String stereotype) {
    super(element);
    this.name = className;
    this.abstractModifier = abstractModifier;
    this.visibility = visibility;
    this.stereotype = stereotype;
  }

  private String name;

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

  public UMLTaggedValue getTaggedValue(String name, boolean ignoreCase) {
    if(!ignoreCase)
      return getTaggedValue(name);

    for(String tvName : taggedValuesMap.keySet()) {
      if(tvName.equalsIgnoreCase(name)) {
        return taggedValuesMap.get(tvName);
      }
    }
    
    return null;
  }
  

  public Collection<UMLTaggedValue> getTaggedValues() {
    return new ArrayList(taggedValuesMap.values());
  }
  

  public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
    if(taggedValue == null)
      return null;
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }

  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLClassWriter().writeTaggedValue(this, taggedValue);

    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    UMLTaggedValue tv = taggedValuesMap.remove(name);

    if(tv != null) {
      writer.getUMLClassWriter().removeTaggedValue(this, tv);
    }
    
  }


  public List<UMLAttribute> getAttributes() {
    return attributes;
  }

  public UMLAttribute getAttribute(String name) {
    for(UMLAttribute att : attributes)
      if(att.getName().equals(name))
        return att;

    return null;
  }

  public void addAttribute(UMLAttribute attribute) {
    attributes.add(attribute);
  }

  public void addAssociation(UMLAssociation assoc) {
    associations.add(assoc);
  }

  public Set<UMLAssociation> getAssociations() {
    return associations;
  }

  public void addGeneralization(UMLGeneralization generalization) {
    generalizations.add(generalization);
  }

  public List<UMLGeneralization> getGeneralizations() {
    return generalizations;
  }

  
  
  public UMLAbstractModifier getAbstractModifier() {
	return abstractModifier;
  }

  public void setAbstractModifier(UMLAbstractModifier abstractModifier) {
	this.abstractModifier = abstractModifier;
  }

  public UMLVisibility getVisibility() {
    return visibility;
  }

  public void setVisibility(UMLVisibility visibility) {
    this.visibility = visibility;
  }


  /**
   * Get the UmlPkg value.
   * @return the UmlPkg value.
   */
  public UMLPackageBean getPackage() {
    return umlPkg;
  }

  public String getStereotype() {
    return stereotype;
  }

  public Set<UMLDependency> getDependencies() {
    return dependencies;
  }

  public void addDependency(UMLDependency dependency) {
    dependencies.add(dependency);
  }

  /**
   * Set the UmlPkg value.
   * @param newUmlPkg The new UmlPkg value.
   */
  void setPackage(UMLPackageBean newUmlPkg) {
    this.umlPkg = newUmlPkg;
  }

  

}
