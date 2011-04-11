package gov.nih.nci.ncicb.xmiinout.domain.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAbstractModifier;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLFinalModifier;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperationParameter;
import gov.nih.nci.ncicb.xmiinout.domain.UMLStaticModifier;
import gov.nih.nci.ncicb.xmiinout.domain.UMLSynchronizedModifier;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.domain.UMLVisibility;

public class UMLOperationBean extends JDomDomainObject implements UMLOperation {

	private String name;
	private String specification;
	private UMLVisibility visibility;
	private String stereoType;
	private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();
	private List<UMLOperationParameter> parameters = new ArrayList<UMLOperationParameter>();
	private UMLAbstractModifier abstractModifier = new UMLAbstractModifierBean("false");
	private UMLFinalModifier finalModifier = new UMLFinalModifierBean("false");
	private UMLSynchronizedModifier synchronizedModifier = new UMLSynchronizedModifierBean("false");
	private UMLStaticModifier staticModifier = new UMLStaticModifierBean("false");
	private List<UMLAttribute> attributes = new ArrayList<UMLAttribute>();

	public UMLOperationBean(org.jdom.Element element, 
			String name,
			String stereoType,
			String specification, 
			UMLVisibility visibility) {
		super(element);
		this.name = name;
		this.visibility = visibility;
		this.specification = specification;
	}

	public String getSpecification() {
		return specification;
	}

	public String setSpecification(String specification) {
		return this.specification = specification;
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

	public String getStereoType() {
		return stereoType;
	}

	public List<UMLOperationParameter> getParameters() {
		return parameters;
	}

	public UMLOperationParameter getParameter(String name) {
		for (UMLOperationParameter att : parameters)
			if (att.getName().equals(name))
				return att;

		return null;
	}

	public void addOperationParameter(UMLOperationParameter attribute) {
		parameters.add(attribute);
		// add to jdom element
	}

	public void addOperationParameters(List<UMLOperationParameter> params) {
		parameters.addAll(params);
		// add to jdom element
	}

	public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
		taggedValuesMap.put(taggedValue.getName(), taggedValue);
		return taggedValue;
	}

	public UMLTaggedValue addTaggedValue(String name, String value) {
		UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name,
				value);

		// add to jdom element
		UMLTaggedValue tv = writer.getUMLOperationWriter().writeTaggedValue(
				this, taggedValue);

		taggedValuesMap.put(tv.getName(), tv);

		return tv;
	}

	public void removeTaggedValue(String name) {
		// remove from jdom element
		UMLTaggedValue tv = taggedValuesMap.remove(name);

		if (tv != null) {
			writer.getUMLOperationWriter().removeTaggedValue(this, tv);
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

	public List<UMLAttribute> getAttributes() {
		return attributes;
	}

	public UMLAttribute getAttribute(String name) {
		for (UMLAttribute att : attributes)
			if (att.getName().equals(name))
				return att;

		return null;
	}

	public UMLAbstractModifier getAbstractModifier() {
		return abstractModifier;
	}

	public void setAbstractModifier(UMLAbstractModifier abstractModifier) {
		this.abstractModifier = abstractModifier;
	}

	public UMLFinalModifier getFinalModifier() {
		return finalModifier;
	}

	public void setFinalModifier(UMLFinalModifier finalModifier) {
		this.finalModifier = finalModifier;
	}

	public UMLStaticModifier getStaticModifier() {
		return staticModifier;
	}

	public void setStaticModifier(UMLStaticModifier staticModifier) {
		this.staticModifier = staticModifier;
	}

	public UMLSynchronizedModifier getSynchronizedModifier() {
		return synchronizedModifier;
	}

	public void setSynchronizedModifier(
			UMLSynchronizedModifier synchronizedModifier) {
		this.synchronizedModifier = synchronizedModifier;
	}

}
