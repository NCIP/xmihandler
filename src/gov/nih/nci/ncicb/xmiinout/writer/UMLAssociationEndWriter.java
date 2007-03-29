package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;

public interface UMLAssociationEndWriter {

  public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLAssociationEnd end, UMLTaggedValue taggedValue);
  
}
