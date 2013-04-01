/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLAttributeWriter {

  public UMLTaggedValue writeTaggedValue(UMLAttribute att, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLAttribute att, UMLTaggedValue taggedValue);
  
}
