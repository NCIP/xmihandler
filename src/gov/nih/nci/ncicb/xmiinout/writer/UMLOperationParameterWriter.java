/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */


package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLOperationParameter;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLOperationParameterWriter {

  public UMLTaggedValue writeTaggedValue(UMLOperationParameter att, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLOperationParameter att, UMLTaggedValue taggedValue);
  
}
