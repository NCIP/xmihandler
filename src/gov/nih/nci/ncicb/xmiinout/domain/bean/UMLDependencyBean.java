package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;
import org.jdom.*;


public class UMLDependencyBean extends JDomDomainObject implements UMLDependency {

  private UMLDependencyEnd client, supplier;

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

  private UMLVisibility visibility;

  private String name;

  public UMLDependencyBean(Element element, String name, UMLVisibility visibility, UMLDependencyEnd client, UMLDependencyEnd supplier) {
    super(element);

    this.visibility = visibility;

    this.supplier = supplier;
    this.client = client;

    this.name = name;

    supplier.addDependency(this);
    client.addDependency(this);

  }

  public UMLDependencyEnd getSupplier() {
    return supplier;
  }

  public UMLDependencyEnd getClient() {
    return client;
  }

  public UMLVisibility getVisibility() {
    return visibility;
  }

  public String getName() {
    return name;
  }

  public UMLTaggedValue getTaggedValue(String name) {
    return taggedValuesMap.get(name);
  }

  public Collection<UMLTaggedValue> getTaggedValues() {
    return new ArrayList(taggedValuesMap.values());
  }
  
  public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }

  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLDependencyWriter().writeTaggedValue(this, taggedValue);

    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    taggedValuesMap.remove(name);
  }



}