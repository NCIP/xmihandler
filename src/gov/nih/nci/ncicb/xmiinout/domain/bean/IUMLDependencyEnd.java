package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDependencyEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;

import java.util.*;

public interface IUMLDependencyEnd extends UMLDependencyEnd {

  public void addDependency(UMLDependency dependency);

}
