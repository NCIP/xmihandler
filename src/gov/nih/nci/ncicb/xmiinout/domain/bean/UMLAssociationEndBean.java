/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import java.util.*;

public class UMLAssociationEndBean extends JDomDomainObject implements UMLAssociationEnd {

  private UMLAssociable endElement;
  private String roleName;

  private int lowMultiplicity, highMultiplicity;

  private boolean navigable;
  private UMLVisibility visibility;

  private UMLAssociation owner;

  private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

  public UMLAssociationEndBean
    (org.jdom.Element elt,
     UMLAssociable end,
     String roleName,
     int lowMultiplicity,
     int highMultiplicity,
     boolean navigable
     ) {

    super(elt);

    this.endElement = end;
    this.roleName = roleName;
    this.lowMultiplicity = lowMultiplicity;
    this.highMultiplicity = highMultiplicity;
    this.navigable = navigable;

  }

  public UMLAssociable getUMLElement() {
    return endElement;
  }

  public String getRoleName() {
    return roleName;
  }

  public int getLowMultiplicity() {
    return lowMultiplicity;
  }
  public int getHighMultiplicity() {
    return highMultiplicity;
  }

  public boolean isNavigable() {
    return navigable;
  }

  public UMLVisibility getVisibility() {
    return visibility;
  }

  public UMLAssociation getOwningAssociation() {
    return owner;
  }

  public void _setOwningAssociation(UMLAssociation owner) {
    this.owner = owner;
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
    taggedValuesMap.put(taggedValue.getName(), taggedValue);
    return taggedValue;
  }
  
  public UMLTaggedValue addTaggedValue(String name, String value) {
    UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

    // add to jdom element
    UMLTaggedValue tv = writer.getUMLAssociationEndWriter().writeTaggedValue(this, taggedValue);
    
    taggedValuesMap.put(tv.getName(), tv);
    
    return tv;
  }

  public void removeTaggedValue(String name) {
    //remove from jdom element
    UMLTaggedValue tv = taggedValuesMap.remove(name);

    if(tv != null) {
      writer.getUMLAssociationEndWriter().removeTaggedValue(this, tv);
    }

  }

}
