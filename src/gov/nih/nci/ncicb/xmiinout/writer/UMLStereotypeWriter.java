package gov.nih.nci.ncicb.xmiinout.writer;

import org.jdom.Element;

public interface UMLStereotypeWriter {

	public void addStereotype(Element elt, String stereotype);

	public void removeStereotype(Element elt, String stereotype);

}
