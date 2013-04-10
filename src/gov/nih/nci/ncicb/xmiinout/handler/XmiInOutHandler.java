/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler;

import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.writer.UMLWriter;

import java.io.IOException;

public interface XmiInOutHandler {

  public void load(String filename) throws XmiException, IOException;
  public void load(java.net.URI url) throws XmiException, IOException;

  public void save(String filename) throws XmiException, IOException;

  public UMLModel getModel();

  public UMLModel getModel(String modelName);

//  public UMLWriter getUMLWriter();


}