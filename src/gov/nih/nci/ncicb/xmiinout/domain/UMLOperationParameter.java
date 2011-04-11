package gov.nih.nci.ncicb.xmiinout.domain;

public interface UMLOperationParameter extends UMLTaggableElement{
	/**
	  * @return The name of the operation
	  */
	  public String getName();
	  
	  public UMLVisibility getVisibility();
	  
	  public String getKind();
	  
	  public UMLDatatype getDataType();

}
