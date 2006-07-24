package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.*;

public interface UMLModelWriter {

  public UMLTaggedValue writeTaggedValue(UMLModel model, UMLTaggedValue taggedValue);

  public UMLDependency writeDependency(UMLModel model, UMLDependency dependency);
}
