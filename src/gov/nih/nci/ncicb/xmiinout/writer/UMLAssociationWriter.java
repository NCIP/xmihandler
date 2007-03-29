package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;


public interface UMLAssociationWriter {

  public UMLTaggedValue writeTaggedValue(UMLAssociation assoc, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLAssociation assoc, UMLTaggedValue taggedValue);
  
}
