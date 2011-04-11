
package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLOperationParameter;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLOperationParameterWriter {

  public UMLTaggedValue writeTaggedValue(UMLOperationParameter att, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLOperationParameter att, UMLTaggedValue taggedValue);
  
}
