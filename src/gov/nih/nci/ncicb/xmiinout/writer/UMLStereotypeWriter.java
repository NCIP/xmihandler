/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import org.jdom.Element;

public interface UMLStereotypeWriter {

	public void addStereotype(Element elt, String stereotype);

	public void removeStereotype(Element elt, String stereotype);

}
