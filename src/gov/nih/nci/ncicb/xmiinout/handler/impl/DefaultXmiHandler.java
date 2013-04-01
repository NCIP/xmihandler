/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler.impl;


import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;

import java.util.*;

public abstract class DefaultXmiHandler implements XmiInOutHandler {

  protected Map<String, UMLModel> models = new HashMap<String, UMLModel>();

  private byte[] clones = null;

  public void load(String filename) {
    _load(filename);
    
  }

  public void load(java.net.URI url) {
    models = new HashMap<String, UMLModel>();
    
    _load(url);
    
  }
  
  protected abstract void _load(String filename);
  protected abstract void _load(java.net.URI uri);
  

}