/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler;

public class XmiException extends Exception {

  public XmiException(Throwable t) {
    super(t);
  }

  public XmiException(String msg) {
    super(msg);
  }

}