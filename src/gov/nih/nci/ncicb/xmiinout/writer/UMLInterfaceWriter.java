package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLInterfaceWriter {

  public UMLTaggedValue writeTaggedValue(UMLInterface interfaze, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLInterface interfaze, UMLTaggedValue taggedValue);
  
}
