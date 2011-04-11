package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLOperationWriter {

  public UMLTaggedValue writeTaggedValue(UMLOperation att, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLOperation att, UMLTaggedValue taggedValue);
  
}
