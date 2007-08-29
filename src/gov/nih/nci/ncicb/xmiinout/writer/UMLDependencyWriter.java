package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLDependencyWriter {

  public UMLTaggedValue writeTaggedValue(UMLDependency dep, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLDependency dep, UMLTaggedValue taggedValue);
  
  public void writeStereotype(UMLDependency dep, String stereotype);

  public void removeStereotype(UMLDependency dep, String stereotype);  
  
}
