package gov.nih.nci.ncicb.xmiinout.domain.bean;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDatatype;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependencyEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTagDefinition;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

public class UMLModelBean extends JDomDomainObject implements UMLModel {

	private List<UMLPackage> packages = new ArrayList();
	private List<UMLClass> classes = new ArrayList<UMLClass>();

	static private Map<String, UMLTagDefinition> tagDefinitionsByNameMap = new HashMap<String, UMLTagDefinition>();
	static private Map<String, UMLTagDefinition> tagDefinitionsByXmiIdMap = new HashMap<String, UMLTagDefinition>();

	private Map<String, UMLTaggedValue> taggedValuesMap = new HashMap<String, UMLTaggedValue>();

	private List<UMLAssociation> associations = new ArrayList<UMLAssociation>();
	private List<UMLGeneralization> generalizations = new ArrayList<UMLGeneralization>();
	private List<UMLDependency> dependencies = new ArrayList<UMLDependency>();

	private Map<String, UMLDatatype> datatypes = new HashMap<String, UMLDatatype>();

	private String name;

	public UMLModelBean(org.jdom.Element element) {
		super(element);
	}

	/**
	 * Get the Name value.
	 * @return the Name value.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Name value.
	 * @param newName The new Name value.
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	public List<UMLPackage> getPackages() {
		return packages;
	}

	public UMLPackage getPackage(String name) {
		for(UMLPackage pkg : packages) 
			if(pkg.getName().equals(name))
				return pkg;

		return null;
	}

	public void addPackage(UMLPackage pkg) {
		packages.add(pkg);
	}

	public boolean equals(Object o) {
		if(o instanceof UMLModelBean) {
			UMLModelBean compareTo = (UMLModelBean)o;
			return (compareTo.getName().equals(this.name));
		} else
			return false;
	}


	public List<UMLClass> getClasses() {
		return classes;
	}

	public void addClass(UMLClass clazz) {
		classes.add(clazz);
	}

	static public UMLTagDefinition getTagDefinitionByName(String name) {
		return tagDefinitionsByNameMap.get(name);
	}

	static public UMLTagDefinition getTagDefinitionByXmiId(String xmiId) {
		return tagDefinitionsByNameMap.get(xmiId);
	}

	public UMLTaggedValue getTaggedValue(String name) {
		return taggedValuesMap.get(name);
	}

	public UMLTaggedValue addTaggedValue(UMLTaggedValue taggedValue) {
		taggedValuesMap.put(taggedValue.getName(), taggedValue);
		return taggedValue;
	}

	public UMLTagDefinition addTagDefinition(UMLTagDefinition tagDefinition) {
		tagDefinitionsByNameMap.put(tagDefinition.getName(), tagDefinition);
		tagDefinitionsByXmiIdMap.put(tagDefinition.getXmiId(), tagDefinition);	    
		return tagDefinition;
	}  

	public UMLTagDefinition addTagDefinition(String name) {

		// add to jdom element
		Element modelElt = this.getJDomElement();

		Namespace ns = modelElt.getNamespace();
		Element ownedElement = modelElt.getChild("Namespace.ownedElement", ns);	    

		String xmiId = java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase();
		UMLTagDefinitionBean tdBean = new UMLTagDefinitionBean(ownedElement, xmiId, name);


		Element tdElement = new Element("TagDefinition", ns);
		tdElement.setAttribute("xmi.id", xmiId);
		tdElement.setAttribute("name", name);;
		ownedElement.addContent(tdElement);

		tagDefinitionsByNameMap.put(tdBean.getName(), tdBean);
		tagDefinitionsByXmiIdMap.put(tdBean.getXmiId(), tdBean);

		return (UMLTagDefinition)tdBean;
	}

	public static UMLTagDefinition addTagDefinition(Element elt, String name) {
		Namespace ns = elt.getNamespace();
		System.out.println("Namespace: " + ns);
		
		Element rootElt = elt.getDocument().getRootElement();
		System.out.println("Root Element name: " + rootElt.getName());
		Element xmiElt = rootElt.getChild("XMI");	
		System.out.println("XMI Element name: " + xmiElt.getName());		
		Element xmiContentElt = xmiElt.getChild("XMI.content");
		System.out.println("XMI Content Element name: " + xmiContentElt.getName());		

	
	    List<Element> xmiContentChildren = (List<Element>)xmiContentElt.getChildren();
	    System.out.println("xmiContentChildren Elements found: " + xmiContentChildren.size());
	    
	    Element modelElt = null;
	    for(Element xmiContentChild : xmiContentChildren) {
	    	System.out.println("xmiContentChild: " + xmiContentChild.getName());
	    	
	    	if (xmiContentChild.getName().equalsIgnoreCase("Model")){
	    		modelElt = xmiContentChild;
	    	}
	    }		
	    
		System.out.println("Model Element name: " + modelElt.getName());	    

		Element ownedElement = modelElt.getChild("Namespace.ownedElement", ns);	
		System.out.println("ownedElement name: " + ownedElement.getName());		
		
		String xmiId = java.util.UUID.randomUUID().toString().replace('-','_').toUpperCase();
		UMLTagDefinitionBean tdBean = new UMLTagDefinitionBean(ownedElement, xmiId, name); 

		Element tdElement = new Element("TagDefinition", ns);
		tdElement.setAttribute("xmi.id", xmiId);
		tdElement.setAttribute("name", name);
		ownedElement.addContent(tdElement);

		tagDefinitionsByNameMap.put(tdBean.getName(), tdBean);
		tagDefinitionsByXmiIdMap.put(tdBean.getXmiId(), tdBean);		

		return (UMLTagDefinition)tdBean;
	}  	

	public UMLTaggedValue addTaggedValue(String name, String value) {
		UMLTaggedValueBean taggedValue = new UMLTaggedValueBean(null, name, value);

		// add to jdom element
		// Can now use the following method instead, if desired;
//		UMLTaggedValue tv = writer.getUMLTaggedValueWriter().writeTaggedValue(this, taggedValue);    
		UMLTaggedValue tv = writer.getUMLModelWriter().writeTaggedValue(this, taggedValue);

		taggedValuesMap.put(tv.getName(), tv);

		return tv;
	}

	public void removeTaggedValue(String name) {
		//remove from jdom element
		UMLTaggedValue tv = taggedValuesMap.remove(name);
		if(tv != null) {
			writer.getUMLModelWriter().removeTaggedValue(this, tv);
		}
	}

	public void addAssociation(UMLAssociation assoc) {
		associations.add(assoc);
	}

	public List<UMLAssociation> getAssociations() {
		return associations;
	}


	public void addGeneralization(UMLGeneralization generalization) {
		generalizations.add(generalization);
	}

	public List<UMLGeneralization> getGeneralizations() {
		return generalizations;
	}


	public void _addDependency(UMLDependency dependency) 
	{
		dependencies.add(dependency);
	}

	public UMLDependency addDependency(UMLDependency dependency) {
		if(dependency instanceof UMLDependencyBean) 
		{
			UMLDependencyBean depBean = (UMLDependencyBean)dependency;
			// if dep does not have an element, then we want to add one
			if(depBean.getJDomElement() == null) 
			{
				UMLDependency dep = writer.getUMLModelWriter().writeDependency(this, dependency);

				dependencies.add(dep);
				return dep;
			} else {
				throw new IllegalArgumentException("Cannot add an existing dependency");
			}
		} else {
			throw new IllegalArgumentException("Incorrect dependency parameter, expecting class=" + UMLDependencyBean.class + " but received=" + dependency.getClass().getName());
		}

	}

	public List<UMLDependency> getDependencies() {
		return dependencies;
	}


	public void addDatatype(UMLDatatype type) {
		datatypes.put(((UMLDatatypeBean)type).getModelId(), type);
	}


	public UMLDependency createDependency(UMLDependencyEnd client,
			UMLDependencyEnd supplier, String name)
	{
		if(client == null)
			throw new IllegalArgumentException("client may not be null");

		if(supplier == null)
			throw new IllegalArgumentException("supplier may not be null");

		return new UMLDependencyBean(null, name, null, client, supplier);
	}

	public static UMLTagDefinition getTagDefinition(String name){
		UMLTagDefinition td = UMLModelBean.getTagDefinitionByName(name);

		if (td != null){ return td; }

		td = UMLModelBean.getTagDefinitionByXmiId(name);

		if (td != null){ return td; }

		return null;
	}
}
