/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;

public interface UMLAssociationEndWriter {

  public UMLTaggedValue writeTaggedValue(UMLAssociationEnd end, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLAssociationEnd end, UMLTaggedValue taggedValue);
  
}
