
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependencyEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;
import gov.nih.nci.ncicb.xmiinout.util.ModelUtil;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class SDKTestModel_TestCase extends TestCase {

	private static Logger logger = Logger.getLogger(SDKTestModel_TestCase.class.getName());

	private XmiInOutHandler handler = null;
	private String filename;
	private String handlerEnumType;
	private String newFileExtension;
	private String modelName;

	private boolean noColor = true;

	static final short RED = 31, GREEN = 32,
	BLACK = 30, YELLOW = 33, BLUE = 34,
	MAGENTA = 35, CYAN = 36, WHITE = 37; 

	public SDKTestModel_TestCase(String filename, String handlerEnumType, String newFileExtension, String modelName) {
		this.filename = filename;
		this.handlerEnumType = handlerEnumType;
		this.newFileExtension = newFileExtension;
		this.modelName = modelName;
	}
	private void testGetModel() {
		UMLModel model = handler.getModel();
		System.out.println("Model: " + model.getName());
	}

	private UMLModel testGetModel(String modelName) {
		UMLModel model = handler.getModel(modelName);
		System.out.println("Model: " + model.getName());
		return model;
	}

	private void testLoadModel(String f) {
		try {
			handler.load(f);
		} catch (XmiException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void testSaveModel() {
		try {
			handler.save(filename + newFileExtension);
		} catch (Exception e){
			e.printStackTrace();
		} // end of try-catch
	}

	private void printInColor(short color, String text) {
		if(noColor)
			System.out.print(text);
		else
			System.out.print((char)27 + "[0;" + color + ";40m" + text + (char)27 + "[0;37;40m");
	}

	private void suite() {

		init();

		UMLModel model = testGetModel(modelName);

		printModel(model);

		testFindClass(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment");

		testFindAttribute(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment.amount");

		testGetFullPackageName(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment");

		testGetSuperclasses(model);

		addTaggedValueToAll(model);

		addDependency(model);

		testRemoveTaggedValue(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment", "myClassTaggedValue");

		testSaveModel();

		testLoadModel(filename + newFileExtension);
		model = testGetModel(modelName);
		printModel(model);


	}


	private void testGetSuperclasses(UMLModel model) {
		UMLClass[] classes = testGetSuperclasses(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Cash");
		Assert.assertTrue(classes.length == 1);
		Assert.assertTrue(classes[0].getName().equals("Payment"));
		System.out.println("Found superclass for Cash: " + classes[0].getName());

		classes = testGetSuperclasses(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment");
		Assert.assertTrue(classes.length == 0);
		System.out.println("Correctly found no superclass for Payment");

	}

	private UMLClass[] testGetSuperclasses(UMLModel model, String className) {
		UMLClass clazz = ModelUtil.findClass(model, className);
		return ModelUtil.getSuperclasses(clazz);
	}

	private void testGetFullPackageName(UMLModel model, String className) {
		String pkgName = className.substring(0, className.lastIndexOf("."));

		UMLClass clazz = ModelUtil.findClass(model, className);
		String result = ModelUtil.getFullPackageName(clazz);

		Assert.assertTrue("Testing GetFullPackageName - Found Wrong Package Name: " + result + "   For Class : " + className, pkgName.equals(result));

		System.out.println("Testing GetFullPackageName - Correct Package: " + result);
	}

	private void testFindClass(UMLModel model, String fullClassName) {

		UMLClass clazz = ModelUtil.findClass(model, fullClassName);

		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		Assert.assertNotNull("Class not found -- " + fullClassName + " -- " + className, clazz);

		Assert.assertTrue("Class not found -- " + fullClassName + " -- " + className, clazz.getName().equals(className));

		System.out.println("Found Class: " + clazz.getName());

	}

	private void testFindAttribute(UMLModel model, String fullAttName) {

		UMLAttribute att = ModelUtil.findAttribute(model, fullAttName);

		String attName = fullAttName.substring(fullAttName.lastIndexOf(".") + 1);

		Assert.assertNotNull("Attrubute not found -- " + fullAttName + " -- " + attName, att);

		Assert.assertTrue("Attribute not found -- " + fullAttName + " -- " + attName, att.getName().equals(attName));

		System.out.println("Found Attribute: " + att.getName());

	}


	private void addDependency(UMLModel model) {
		UMLClass client = null,
		supplier = null;

		client = findClass(model, "Human");
		supplier = findClass(model, "Mammal");

		UMLDependency dep = model.createDependency(client, supplier, "is a type of");
		dep = model.addDependency(dep);

		dep.addTaggedValue("ea_type", "Dependency");
		dep.addTaggedValue("direction", "Source -&gt; Destination");
		dep.addTaggedValue("style", "3");
		
		dep.addStereotype("myTestStereotype");
		dep.removeStereotype("myTestStereotype");
	}

	private UMLClass findClass(UMLModel model, String className) 
	{
		for(UMLPackage pkg : model.getPackages()) {
			UMLClass c = findClass(pkg, className);
			if(c != null)
				return c;
		}    
		return null;
	}

	private UMLClass findClass(UMLPackage pkg, String className) {
		for(UMLClass clazz : pkg.getClasses()) {
			if(clazz.getName().equals(className))
				return clazz;
		}
		for(UMLPackage _pkg : pkg.getPackages()) {
			UMLClass c = findClass(_pkg, className);
			if(c != null)
				return c;
		}
		return null;
	}

	private void testRemoveTaggedValue(UMLModel model, String fullName, String tvName) {
		UMLClass clazz = ModelUtil.findClass(model, fullName);

		if(clazz != null)
			clazz.removeTaggedValue(tvName);
		else {
			UMLAttribute att = ModelUtil.findAttribute(model, fullName);
			att.removeTaggedValue(tvName);
		}
	}

	private void addTaggedValueToAll(UMLModel model) {
		for(UMLPackage pkg : model.getPackages()) {
			addTaggedValueToAll(pkg);
		}
		for(UMLClass clazz : model.getClasses()) {
			addTaggedValueToAll(clazz);
		}
	}

	private void addTaggedValueToAll(UMLPackage pkg) {
		pkg.addTaggedValue("myPackageTaggedValue", "test package tagged Value");

		for(UMLPackage _pkg : pkg.getPackages()) {
			addTaggedValueToAll(_pkg);
		}
		for(UMLClass clazz : pkg.getClasses()) {
			addTaggedValueToAll(clazz);
		}    
	}

	private void addTaggedValueToAll(UMLClass clazz) {
		clazz.addTaggedValue("myClassTaggedValue", "test class tagged value");

		for(UMLAttribute att : clazz.getAttributes()) {
			addTaggedValueToAll(att);
		}

		for(UMLAssociation assoc : clazz.getAssociations()) {
			addTaggedValueToAll(assoc);
		}
	}

	private void addTaggedValueToAll(UMLAttribute att) {
		att.addTaggedValue("myAttributeTaggedValue", "test attribute tagged value");
		
	}

	private void addTaggedValueToAll(UMLAssociation assoc) {
		assoc.addTaggedValue("myAssociationTaggedValue", "test association tagged value");
		
		for(UMLAssociationEnd end : assoc.getAssociationEnds()) {
			end.addTaggedValue("myAssociationEndTaggedValue", "test associationEnd tagged value");
		}

	}

	private void printModel(UMLModel model) {
		System.out.println(model.getName());
		for(UMLPackage pkg : model.getPackages()) {
			printPackage(pkg, 0);
		}
		for(UMLClass clazz : model.getClasses()) {
			printClass(clazz, 0);
		}
	}
	private void printPackage(UMLPackage pkg, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");

		System.out.println(pkg.getName());

		for(UMLTaggedValue tv : pkg.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}

		for(UMLClass clazz : pkg.getClasses()) {
			printClass(clazz, pkgDepth);
		}
		
		for(UMLInterface interfaze : pkg.getInterfaces()) {
			printInterface(interfaze, pkgDepth);
		}
		
		for(UMLPackage _pkg : pkg.getPackages()) {
			printPackage(_pkg, pkgDepth + 1);
		}
	}
	
	private void printClass(UMLClass clazz, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  Class: ");

		if(clazz.getVisibility() != null)
			printInColor(RED, clazz.getVisibility().getName());

		System.out.println(" " + clazz.getName());    

		for(UMLTaggedValue tv : clazz.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}

		for(UMLAttribute att : clazz.getAttributes()) {
			printAttribute(att, pkgDepth + 1);
		}

		for(UMLGeneralization gen : clazz.getGeneralizations()) {
			printGeneralization(gen, pkgDepth + 1);
		}

		System.out.println("");

		for(UMLDependency dep : clazz.getDependencies()) {
			printDependency(dep, pkgDepth + 1);
		}

		System.out.println("");

		for(UMLAssociation assoc : clazz.getAssociations()) {
			printAssociation(assoc, pkgDepth + 1);
		}

	}
	
	private void printInterface(UMLInterface interfaze, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  Interface: ");

		System.out.println(" " + interfaze.getName());    

		for(UMLTaggedValue tv : interfaze.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}

		for(UMLAttribute att : interfaze.getAttributes()) {
			printAttribute(att, pkgDepth + 1);
		}

		for(UMLGeneralization gen : interfaze.getGeneralizations()) {
			printGeneralization(gen, pkgDepth + 1);
		}

		System.out.println("");

		for(UMLDependency dep : interfaze.getDependencies()) {
			printDependency(dep, pkgDepth + 1);
		}

		System.out.println("");

		for(UMLAssociation assoc : interfaze.getAssociations()) {
			printAssociation(assoc, pkgDepth + 1);
		}

	}	

	private void printAssociation(UMLAssociation assoc, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");


		UMLAssociationEnd srcEnd = null, targetEnd = null;
		for(UMLAssociationEnd assocEnd : assoc.getAssociationEnds()) {
			if(srcEnd == null)
				srcEnd = assocEnd;
			else
				targetEnd = assocEnd;
		}
		printInColor(GREEN,
				"Association: "
				+ ((UMLClass)srcEnd.getUMLElement()).getName() 
				+ "(" + srcEnd.getRoleName() + ")"
				+ "[" + srcEnd.getLowMultiplicity() + ".."
				+ srcEnd.getHighMultiplicity() + "]"
				+ (srcEnd.isNavigable()?"<":"")
				+ "--"
				+ (targetEnd.isNavigable()?">":"")
				+ ((UMLClass)targetEnd.getUMLElement()).getName() 
				+ "(" + targetEnd.getRoleName() + ")"
				+ "[" + targetEnd.getLowMultiplicity() + ".."
				+ targetEnd.getHighMultiplicity() + "]"
		);

		System.out.println("");

		for(UMLTaggedValue tv : assoc.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}    

		System.out.println("");
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

		printInColor(GREEN, 
				"Association from Source:" 
				+ srcEnd.getOwningAssociation().getRoleName());

		System.out.println("");
		for(UMLTaggedValue tv : srcEnd.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}    

		System.out.println("");
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");
		printInColor(GREEN, 
				"Association from target:" 
				+ targetEnd.getOwningAssociation().getRoleName());

		System.out.println("");
		for(UMLTaggedValue tv : targetEnd.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}    


		System.out.println("");

	}


	private void printGeneralization(UMLGeneralization gen, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

		String subtypeName = null;
		String supertypeName = null;
		
		if (gen.getSubtype() instanceof UMLClass) {
			subtypeName = ((UMLClass)gen.getSubtype()).getName();
		} else {
			subtypeName = ((UMLInterface)gen.getSubtype()).getName();
		}
		
		if (gen.getSupertype() instanceof UMLClass) {
			supertypeName = ((UMLClass)gen.getSupertype()).getName();
		} else {
			supertypeName = ((UMLInterface)gen.getSupertype()).getName();
		}
		
		printInColor(GREEN, "Generalization: " + subtypeName + " --> " + supertypeName);
		System.out.println("");
	}

	private void printDependency(UMLDependency dep, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

		UMLDependencyEnd clientEnd = dep.getClient();
		UMLDependencyEnd supplierEnd = dep.getSupplier();
		
		String clientName = null;
		String supplierName = null;
		
		if (clientEnd instanceof UMLClass){
			clientName = ((UMLClass)(clientEnd)).getName();
		} else if (clientEnd instanceof UMLInterface){
			clientName = ((UMLInterface)(clientEnd)).getName();
		}
		
		if (supplierEnd instanceof UMLClass){
			supplierName = ((UMLClass)(supplierEnd)).getName();
		} else if (supplierEnd instanceof UMLInterface){
			supplierName = ((UMLInterface)(supplierEnd)).getName();
		}
		
		printInColor(GREEN, "Dependency: " + clientName + " --> " + supplierName + "; Stereotype: " + dep.getStereotype());	

		System.out.println("");

		for(UMLTaggedValue tv : dep.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}    
	}


	private void printAttribute(UMLAttribute att, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

		if(att.getVisibility() != null)
			printInColor(RED, att.getVisibility().getName());

		if(att.getDatatype() != null)
			printInColor(RED, " " + att.getDatatype().getName());

		System.out.println(" " + att.getName());    

		for(UMLTaggedValue tv : att.getTaggedValues()) {
			printTaggedValue(tv, pkgDepth);
		}

	}


	private void printTaggedValue(UMLTaggedValue tv, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("   ");

		printInColor(YELLOW, "tv " + tv.getName() + " : " + tv.getValue());

		System.out.println();
	}

	private void init() {
		try {
			handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.getHandlerEnumType(handlerEnumType));
			handler.load(filename);
		} catch (XmiException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNoColor(boolean b) {
		noColor = b;
	}

	public static void main(String[] args) {
		
		SDKTestModel_TestCase testCase = new SDKTestModel_TestCase(args[0], args[1], args[2], args[3]);

		if(Arrays.binarySearch(args, "--no-color") > -1)
			testCase.setNoColor(true);
		else 
			System.out.println("run with --no-color if you terminal does not support colors");

		testCase.suite();

	}

}