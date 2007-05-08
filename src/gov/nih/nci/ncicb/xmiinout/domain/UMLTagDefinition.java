package gov.nih.nci.ncicb.xmiinout.domain;

/**
 * represents a tagged value
 */
public interface UMLTagDefinition {

//	/**
//	 * return flag indicating whether or not the tag definition
//	 * has already been added to the tag definition map(s).
//	 */  
//	public boolean isNewTagDefinition();
//
//	/**
//	 * set flag to indicate whether or not tag definition
//	 * has already been added to the tag definition map(s).
//	 * @param value the value to change to.
//	 */  
//	public void setNewTagDefinition(boolean value);

	/**
	 * the tag's name, key, id or tag
	 */
	public String getXmiId();

	/**
	 * the tag's value
	 */
	public String getName();

	/**
	 * Modify a tagDefinition's Name.
	 * @param value the value to change to.
	 */
	public void setName(String name);

}