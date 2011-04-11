package gov.nih.nci.ncicb.xmiinout.domain;

import java.util.List;

public interface UMLOperation extends UMLTaggableElement{
	/**
	  * @return The name of the operation
	  */
	  public String getName();
	  
	  /**
	   * @return the visibility (scope)
	   */
	  public UMLVisibility getVisibility();
	  
	  /**
	   * @return the stereo type
	   */
	  public String getStereoType();
	  
	  
	  /**
	   * @return list of parameters
	   */
	  public List<UMLOperationParameter> getParameters();	 
	  
	  /**
	   * @return the isAbstract modifier
	   */
	  public UMLAbstractModifier getAbstractModifier();
	  
	  /**
	   * @return the isFinal modifier
	   */
	  public UMLFinalModifier getFinalModifier();
	  
	  /**
	   * @return the isSynchronized modifier
	   */
	  public UMLSynchronizedModifier getSynchronizedModifier();
	  
	  /**
	   * @return the isStatic modifier
	   */
	  public UMLStaticModifier getStaticModifier();
	  
	  /**
	   * @return a List of all the UML Attributes belonging to this class
	   */
	  public List<UMLAttribute> getAttributes();
	  
	  public String getSpecification();
	  
}
