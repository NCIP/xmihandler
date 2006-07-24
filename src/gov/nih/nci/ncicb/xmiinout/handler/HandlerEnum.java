package gov.nih.nci.ncicb.xmiinout.handler;

public enum HandlerEnum {
  EAXmi10 ("gov.nih.nci.ncicb.xmiinout.handler.impl.EAXmi10Impl",
           "gov.nih.nci.ncicb.xmiinout.writer.impl.JDomEAXmiWriter"),
  EADefault ("gov.nih.nci.ncicb.xmiinout.handler.impl.EADefaultImpl",
           "gov.nih.nci.ncicb.xmiinout.writer.impl.JDomEAXmiWriter");

  private final String className, writerClassName;

  HandlerEnum(String className, String writerClassName) {
    this.className = className;
    this.writerClassName = writerClassName;
  }

  public String className() {
    return className;
  }
  
  public String getWriterClassName() 
  {
    return writerClassName;
  }

}