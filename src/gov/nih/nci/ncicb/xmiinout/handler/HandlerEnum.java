/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.handler;

public enum HandlerEnum {
	
	EAXmi10 ("gov.nih.nci.ncicb.xmiinout.handler.impl.EAXmi10Impl",
	"gov.nih.nci.ncicb.xmiinout.writer.impl.JDomEAXmiWriter"),
	EADefault ("gov.nih.nci.ncicb.xmiinout.handler.impl.EADefaultImpl",
	"gov.nih.nci.ncicb.xmiinout.writer.impl.JDomEAXmiWriter"),
	ArgoUMLDefault ("gov.nih.nci.ncicb.xmiinout.handler.impl.ArgoUMLDefaultImpl",
	"gov.nih.nci.ncicb.xmiinout.writer.impl.JDomArgoUMLXmiWriter");  

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

	public static HandlerEnum getHandlerEnumType(String handlerEnumType){
		return HandlerEnum.valueOf(handlerEnumType);
	}

}