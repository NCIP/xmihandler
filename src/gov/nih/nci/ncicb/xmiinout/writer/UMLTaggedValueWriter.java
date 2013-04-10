/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import org.jdom.Element;

import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;


public interface UMLTaggedValueWriter {

	public void writeTaggedValue(UMLTaggedValue taggedValue);
	
	public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv);
	
	public void removeTaggedValue(Element elt, UMLTaggedValue tv);

}
