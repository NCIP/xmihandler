/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

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
