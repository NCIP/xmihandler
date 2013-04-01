/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;


public interface UMLDependencyWriter {

  public UMLTaggedValue writeTaggedValue(UMLDependency dep, UMLTaggedValue taggedValue);

  public void removeTaggedValue(UMLDependency dep, UMLTaggedValue taggedValue);
  
  public void writeStereotype(UMLDependency dep, String stereotype);

  public void removeStereotype(UMLDependency dep, String stereotype);  
  
}
