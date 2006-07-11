package gov.nih.nci.ncicb.xmiinout.handler;

import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;

public class XmiHandlerFactory {

  public static XmiInOutHandler getXmiHandler(HandlerEnum handlerType) {
    
    try {
      XmiInOutHandler handler = (XmiInOutHandler)(Class.forName(handlerType.className()).newInstance());

      JDomDomainObject.setUMLWriter(handler.getUMLWriter());

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