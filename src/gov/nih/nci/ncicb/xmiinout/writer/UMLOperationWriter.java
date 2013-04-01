/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLOperationWriter {

  public UMLTaggedValue writeTaggedValue(UMLOperation att, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLOperation att, UMLTaggedValue taggedValue);
  
}
