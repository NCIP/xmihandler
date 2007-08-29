package gov.nih.nci.ncicb.xmiinout.handler;

public class XmiException extends Exception {

  public XmiException(Throwable t) {
    super(t);
  }

  public XmiException(String msg) {
    super(msg);
  }

}