/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.*;

public interface UMLPackageWriter {

  public UMLTaggedValue writeTaggedValue(UMLPackage pkg, UMLTaggedValue taggedValue);
  
  public void removeTaggedValue(UMLPackage pkg, UMLTaggedValue taggedValue);
  
}
