/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLClassWriter {

  public UMLTaggedValue writeTaggedValue(UMLClass clazz, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLClass clazz, UMLTaggedValue taggedValue);

  
}
