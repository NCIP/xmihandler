package gov.nih.nci.ncicb.xmiinout.handler;

public enum HandlerEnum {
  EAXmi10 ("gov.nih.nci.ncicb.xmiinout.handler.impl.EAXmi10Impl"),
  EADefault ("gov.nih.nci.ncicb.xmiinout.handler.impl.EADefaultImpl");

  private final String className;

  HandlerEnum(String className) {
    this.className = className;
  }

  public String className() {
    return className;
  }

}