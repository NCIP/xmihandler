package gov.nih.nci.ncicb.xmiinout.writer;

import org.jdom.Element;

import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.bean.JDomDomainObject;


public interface UMLTaggedValueWriter {

	public void writeTaggedValue(UMLTaggedValue taggedValue);
	
	public UMLTaggedValue addTaggedValue(Element elt, UMLTaggedValue tv);
	
	public void removeTaggedValue(Element elt, UMLTaggedValue tv);

}
