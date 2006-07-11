package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLDependencyWriter {

  public UMLTaggedValue writeTaggedValue(UMLDependency clazz, UMLTaggedValue taggedValue);
  
}
