/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler.impl;


import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;

import java.io.*;

/**
 * 
 */
public class EAXmi10Impl extends EABaseImpl {

  protected List<UMLTaggedValue> doTaggedValues(Element elt) {
    return null;
  }

  protected  List<UMLAttribute> doAttributes(Element elt) {
    return null;
  }

  public List<UMLAssociation> doAssociations(Element classElement) {
    return null;
  }
  
  public List<UMLGeneralization> doGeneralizations(Element classElement) {
    return null;
  }

  public List<UMLDatatype> doDataTypes(Element modelElement) {
    return null;
  }


  protected List<UMLDependency> doDependencies(Element elt)
  {
    return null;
  }

  protected List<UMLOperation> doOperations(Element elt)
  {
    return null;
  }
  
}
