package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.*;

public interface UMLPackageWriter {

  public UMLTaggedValue writeTaggedValue(UMLPackage pkg, UMLTaggedValue taggedValue);
  
  public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue taggedValue);
  
}
