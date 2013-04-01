/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;


import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralizable;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;

import java.util.*;
import org.jdom.*;


public class UMLGeneralizationBean extends JDomDomainObject implements UMLGeneralization {

  private UMLClass subClassType, superClassType;
  private UMLInterface subInterfaceType, superInterfaceType;

  public UMLGeneralizationBean(Element element, UMLClassBean supertype, UMLClassBean subtype) {
    super(element);

    this.subClassType = subtype;
    this.superClassType = supertype;

    if(subtype == null)
      throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid subtype. Please correct model.");

    if(supertype == null)
      throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid supertype. Please correct model.");

    supertype.addGeneralization(this);
    subtype.addGeneralization(this);
  }
  
  public UMLGeneralizationBean(Element element, UMLInterfaceBean supertype, UMLInterfaceBean subtype) {
	    super(element);

	    this.subInterfaceType = subtype;
	    this.superInterfaceType = supertype;

	    if(subtype == null)
	      throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid subtype. Please correct model.");

	    if(supertype == null)
	      throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid supertype. Please correct model.");

	    supertype.addGeneralization(this);
	    subtype.addGeneralization(this);
	  }
  
  public UMLGeneralizationBean(Element element, UMLInterface supertype, UMLClass subtype) {
	    super(element);

	    throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid subtype. A Generalization between a class and an interface is not supported.  Please correct model.");
	  }
  
  public UMLGeneralizationBean(Element element, UMLClass supertype, UMLInterface subtype) {
	    super(element);

	    throw new IllegalArgumentException("Generalization with Id: " + getId() + " has invalid subtype. A Generalization between a class and an interface is not supported.  Please correct model.");
	  }

  public UMLGeneralizable getSubtype() {
	  
	  if (subClassType != null)
		  return subClassType;
	  
	  return subInterfaceType;
  }

  public UMLGeneralizable getSupertype() {
	  if (superClassType != null)
		  return superClassType;
		  
    return superInterfaceType;
  }

}