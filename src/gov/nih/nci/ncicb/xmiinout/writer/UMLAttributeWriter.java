package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLAttributeWriter {

  public UMLTaggedValue writeTaggedValue(UMLAttribute att, UMLTaggedValue taggedValue);
  
}
