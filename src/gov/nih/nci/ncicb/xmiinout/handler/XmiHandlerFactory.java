/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler;

import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;
import gov.nih.nci.ncicb.xmiinout.writer.UMLWriter;

public class XmiHandlerFactory {

  public static XmiInOutHandler getXmiHandler(HandlerEnum handlerType) {
    
    try {
      XmiInOutHandler handler = (XmiInOutHandler)(Class.forName(handlerType.className()).newInstance());

      JDomDomainObject.setUMLWriter((UMLWriter)Class.forName(handlerType.getWriterClassName()).newInstance());

      return handler;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;

  }

}