/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.domain.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.ncicb.xmiinout.domain.UMLDatatype;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperationParameter;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.UMLVisibility;

public class UMLOperationParameterBean extends JDomDomainObject implements UMLOperationParameter {

	private String name;
	private UMLVisibility visibility;
	private String kind;
	private UMLDatatype datatype;
	private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

	public UMLOperationParameterBean(org.jdom.Element element, String name,
			UMLVisibility visibility, UMLDatatype datatype, String kind) {
		super(element);
		this.name = name;
		this.visibility = visibility;
		this.kind = kind;
		this.datatype = datatype;
	}

	/**
	 * @return The name of the operation
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The visibility type
	 */
	public UMLVisibility getVisibility() {
		return visibility;
	}

	/**
	 * return Kind type
	 * @return
	 */
	public String getKind() {
		return kind;
	}
	
	/**
	 * return Datatype
	 * @return
	 */
	public UMLDatatype getDataType() {
		return datatype;
	}
	
	public void _setDatatype(UMLDatatype datatype) {
		this.datatype = datatype;
	}

	public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
		taggedValuesMap.put(taggedValue.getName(), taggedValue);
		return taggedValue;
	}

	public UMLTaggedValue addTaggedValue(String name, String value) {
		UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name,
				value);

		// add to jdom element
		UMLTaggedValue tv = writer.getUMLOperationParameterWriter().writeTaggedValue(this,
				taggedValue);

		taggedValuesMap.put(tv.getName(), tv);

		return tv;
	}

	public void removeTaggedValue(String name) {
		// remove from jdom element
		UMLTaggedValue tv = taggedValuesMap.remove(name);

		if (tv != null) {
			writer.getUMLOperationParameterWriter().removeTaggedValue(this, tv);
		}

	}

	public UMLTaggedValue getTaggedValue(String name) {
		return taggedValuesMap.get(name);
	}

	public UMLTaggedValue getTaggedValue(String name, boolean ignoreCase) {
		if (!ignoreCase)
			return getTaggedValue(name);

		for (String tvName : taggedValuesMap.keySet()) {
			if (tvName.equalsIgnoreCase(name)) {
				return taggedValuesMap.get(tvName);
			}
		}

		return null;
	}

	public Collection<UMLTaggedValue> getTaggedValues() {
		return taggedValuesMap.values();
	}

}
