/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain;


/**
 * Represents a dependency between 2 UML Elements. 
 * Usually represented by a dashed line in UML.
 * <br> A Dependency has 2 ends, called client and supplier
 */
public interface UMLDependency extends UMLTaggableElement {

	/**
	 * @return the class name
	 */
	public String getName();

	/**
	 * @return the visibility (scope)
	 */
	public UMLVisibility getVisibility();

	/**
	 * 
	 * @return the client end
	 */
	public UMLDependencyEnd getClient();

	/**
	 * 
	 * @return the supplier end
	 */
	public UMLDependencyEnd getSupplier();

	/**
	 * @return the dependency stereotype name, or null if there isn't any
	 */
	public String getStereotype();

	/**
	 * @param stereotype 
	 */
	public void addStereotype(String stereotype);	
	
	/**
	 * @param stereotype
	 */
	public void removeStereotype(String stereotype);
}

