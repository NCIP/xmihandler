/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */


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
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggableElement;
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

public class XmiTestCase extends TestCase {

  private static Logger logger = Logger.getLogger(XmiTestCase.class.getName());

  private XmiInOutHandler handler = null;
  private String filename;

  private boolean noColor = false;

  static final short RED = 31, GREEN = 32,
    BLACK = 30, YELLOW = 33, BLUE = 34,
    MAGENTA = 35, CYAN = 36, WHITE = 37;

  public XmiTestCase(String filename) {
    this.filename = filename;
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
      handler.load("test/testdata/" + f);
    } catch (XmiException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void testSaveModel(String filename) {
    try {
      handler.save("test/testdata/" + filename + ".new.xmi");
    } catch (Exception e){
      e.printStackTrace();
    } // end of try-catch
  }


	private void testSaveModel() {
		try {
			handler.save(filename + ".new.xmi");
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

 		UMLModel model = testGetModel("EA Model");

 		printModel(model);

                testFindPackage(model, "Logical View.Logical Model.com.ludet.hr.domain");

 		testFindClass(model, "Logical View.Logical Model.com.ludet.hr.domain.Employee");

 		testFindAttribute(model, "Logical View.Logical Model.com.ludet.hr.domain.Employee.firstName");

 		testGetFullPackageName(model, "Logical View.Logical Model.com.ludet.hr.domain.Employee");

 		testGetSuperclasses(model);

 		addTaggedValueToAll(model);

 		addDependency(model);

		testRemoveTaggedValue(model, "Logical View.Logical Model.com.ludet.hr.common.DomainObject", "HUMAN_REVIEWED");
 		testRemoveTaggedValue(model, "Logical View.Logical Model.com.ludet.hr.common.DomainObject", "ea_ntype");

 		testRemoveTaggedValue(model, "Logical View.Logical Model.com.ludet.hr.domain.Employee.firstName", "HUMAN_REVIEWED");

 		printModel(model);

//          testClassRemoveTaggedValue();
//
//         testPackageTaggedValue();

//          testModelRemoveTaggedValue();

	}



  private void testClassRemoveTaggedValue() {
    init("XMI_Handler_TEST.xmi");

    UMLModel model = testGetModel("EA Model");

    testTaggedValuePresent(model, "Domain Model.hr.domain.Company", "CADSR_Description", true);

    testRemoveTaggedValue(model, "Domain Model.hr.domain.Company", "CADSR_Description");

    testSaveModel("XMI_Handler_TEST.xmi");

    testLoadModel("XMI_Handler_TEST.xmi.new.xmi");

    model = testGetModel("EA Model");

    testTaggedValuePresent(model, "Domain Model.hr.domain.Company", "CADSR_Description", false);

  }

  private void testPackageTaggedValue() {
    logger.info("Package Test Suite");

    init("XMI_Handler_TEST.xmi");

    UMLModel model = testGetModel("EA Model");

    // testing Presence
    logger.info("Test TV Presence");
    testTaggedValuePresent(model, "Domain Model.hr", "TEST_PACKAGE_TV", "test hr");
    testTaggedValuePresent(model, "Domain Model.hr.domain", "TEST_PACKAGE_TV", "test domain");
    testTaggedValuePresent(model, "Domain Model.hr.domain.companies", "TEST_PACKAGE_TV", "test companies");
    testTaggedValuePresent(model, "Domain Model.hr.common", "TEST_PACKAGE_TV", "test common");
    testTaggedValuePresent(model, "Domain Model", "TEST_PACKAGE_TV", "test Domain Model");


    // testing Remove
    logger.info("Test TV Remove");
    testRemoveTaggedValue(model, "Domain Model.hr", "TEST_PACKAGE_TV");
    testRemoveTaggedValue(model, "Domain Model.hr.domain.companies", "TEST_PACKAGE_TV");
    testRemoveTaggedValue(model, "Domain Model.hr.domain", "TEST_PACKAGE_TV");
    testRemoveTaggedValue(model, "Domain Model.hr.common", "TEST_PACKAGE_TV");
    testRemoveTaggedValue(model, "Domain Model", "TEST_PACKAGE_TV");

    testSaveModel("XMI_Handler_TEST.xmi");

    testLoadModel("XMI_Handler_TEST.xmi.new.xmi");
    model = testGetModel("EA Model");

    testTaggedValuePresent(model, "Domain Model", "TEST_PACKAGE_TV", false);
    testTaggedValuePresent(model, "Domain Model.hr", "TEST_PACKAGE_TV", false);
    testTaggedValuePresent(model, "Domain Model.hr.domain", "TEST_PACKAGE_TV", false);
    testTaggedValuePresent(model, "Domain Model.hr.domain.companies", "TEST_PACKAGE_TV", false);
    testTaggedValuePresent(model, "Domain Model.hr.common", "TEST_PACKAGE_TV", false);


    // testing ADD
    logger.info("Test TV Add");
    testLoadModel("XMI_Handler_TEST.xmi");
    model = testGetModel("EA Model");

    testAddTaggedValue(model, "Domain Model", "NEW_PACKAGE_TV", "new domain model");
    testAddTaggedValue(model, "Domain Model.hr", "NEW_PACKAGE_TV", "new hr");
    testAddTaggedValue(model, "Domain Model.hr.domain", "NEW_PACKAGE_TV", "new domain");
    testAddTaggedValue(model, "Domain Model.hr.domain.companies", "NEW_PACKAGE_TV", "new companies");
    testAddTaggedValue(model, "Domain Model.hr.common", "NEW_PACKAGE_TV", "new common");

    testSaveModel("XMI_Handler_TEST.xmi");

    testLoadModel("XMI_Handler_TEST.xmi.new.xmi");
    model = testGetModel("EA Model");

    testTaggedValuePresent(model, "Domain Model", "NEW_PACKAGE_TV", "new domain model");
    testTaggedValuePresent(model, "Domain Model.hr", "NEW_PACKAGE_TV", "new hr");
    testTaggedValuePresent(model, "Domain Model.hr.domain", "NEW_PACKAGE_TV", "new domain");
    testTaggedValuePresent(model, "Domain Model.hr.domain.companies", "NEW_PACKAGE_TV", "new companies");
    testTaggedValuePresent(model, "Domain Model.hr.common", "NEW_PACKAGE_TV", "new common");

  }


  private void testModelRemoveTaggedValue() {
//    init("gme_test1.xmi");
	    init("XMI_Handler_TEST.xmi");


    UMLModel model = testGetModel("EA Model");

    testTaggedValuePresent(model, "NCI_GME_XML_NAMESPACE", true);

    testRemoveTaggedValue(model, "NCI_GME_XML_NAMESPACE");

    testSaveModel("gme_test1.xmi");

    testLoadModel("gme_test1.xmi.new.xmi");

    model = testGetModel("EA Model");

    testTaggedValuePresent(model, "NCI_GME_XML_NAMESPACE", false);

  }


	private void testGetSuperclasses(UMLModel model) {
		UMLClass[] classes = testGetSuperclasses(model, "Logical View.Logical Model.com.ludet.hr.domain.Manager");
		Assert.assertTrue(classes.length == 1);
		Assert.assertTrue(classes[0].getName().equals("Employee"));
		System.out.println("Found superclass: " + classes[0].getName());

		classes = testGetSuperclasses(model, "Logical View.Logical Model.com.ludet.hr.common.DomainObject");
		Assert.assertTrue(classes.length == 0);
		System.out.println("Correctly found no superclass for DomainObject");

	}

	private UMLClass[] testGetSuperclasses(UMLModel model, String className) {
		UMLClass clazz = ModelUtil.findClass(model, className);
		return ModelUtil.getSuperclasses(clazz);
	}



	private void testGetFullPackageName(UMLModel model, String className) {
		String pkgName = className.substring(0, className.lastIndexOf("."));

		UMLClass clazz = ModelUtil.findClass(model, className);
		String result = ModelUtil.getFullPackageName(clazz);

		Assert.assertTrue("Found Wrong Package Name: " + result + "   For Class : " + className, pkgName.equals(result));

		System.out.println("Correct Package: " + result);
	}

  private void testFindPackage(UMLModel model, String fullName) {
    UMLPackage pkg = ModelUtil.findPackage(model, fullName);

    Assert.assertNotNull("Package not found -- " + fullName, pkg);

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

		client = findClass(model, "Employee");
		supplier = findClass(model, "Manager");

		UMLDependency dep = model.createDependency(client, supplier, "reports to");
		dep = model.addDependency(dep);

		dep.addTaggedValue("ea_type", "Dependency");
		dep.addTaggedValue("direction", "Source -&gt; Destination");
		dep.addTaggedValue("style", "3");
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


  private void testTaggedValuePresent(UMLModel model, String fullName, String tvName, String value) {
    UMLPackage pkg = ModelUtil.findPackage(model, fullName);
    if(pkg != null) {
      testTaggedValuePresent(pkg, tvName, value);
    } else {
      UMLClass clazz = ModelUtil.findClass(model, fullName);

      if(clazz != null) {
        testTaggedValuePresent(clazz, tvName, value);
      } else {
        UMLAttribute att = ModelUtil.findAttribute(model, fullName);
        if(att != null) {
          testTaggedValuePresent(att, tvName, value);
        } else {
          Assert.assertTrue("testTaggedValuePresent element can't be found: " + fullName , false);
        }
      }
    }
  }

  private void testTaggedValuePresent(UMLModel model, String fullName, String tvName, boolean present) {
    UMLPackage pkg = ModelUtil.findPackage(model, fullName);
    if(pkg != null) {
      testTaggedValuePresent(pkg, tvName, present);
    } else {
      UMLClass clazz = ModelUtil.findClass(model, fullName);

      if(clazz != null) {
        testTaggedValuePresent(clazz, tvName, present);
      } else {
        UMLAttribute att = ModelUtil.findAttribute(model, fullName);
        if(att != null) {
          testTaggedValuePresent(att, tvName, present);
        } else {
          Assert.assertTrue("testTaggedValuePresent element can't be found: " + fullName , false);
        }
      }
    }

  }

  private void testTaggedValuePresent(UMLTaggableElement elt, String tvName, String value) {
    UMLTaggedValue tv = elt.getTaggedValue(tvName);

    Assert.assertNotNull("testTaggedValuePresent Failed. " + elt + " -- " + tvName + " -- " + value, tv);
    Assert.assertEquals("testTaggedValuePresent Failed. " + elt + " -- " + tvName + " -- " + value, tv.getValue(), value);


  }

  private void testTaggedValuePresent(UMLTaggableElement elt, String tvName, boolean present) {
    UMLTaggedValue tv = elt.getTaggedValue(tvName);

    Assert.assertTrue("testTaggedValuePresent Failed. " + elt + " -- " + tvName + " -- " + present, (tv == null) != present);

  }


  private void testRemoveTaggedValue(UMLTaggableElement elt , String tvName) {
    elt.removeTaggedValue(tvName);
  }

  private void testRemoveTaggedValue(UMLModel model, String fullName, String tvName) {
    UMLPackage pkg = ModelUtil.findPackage(model, fullName);
    if(pkg != null) {
      testRemoveTaggedValue(pkg, tvName);
    } else {
      UMLClass clazz = ModelUtil.findClass(model, fullName);
      if(clazz != null) {
        testRemoveTaggedValue(clazz, tvName);
      } else {
        UMLAttribute att = ModelUtil.findAttribute(model, fullName);
        if(att != null) {
          testRemoveTaggedValue(att, tvName);
        } else {
          Assert.assertTrue("testRemoveTaggedValue element can't be found: " + fullName , false);
        }
      }
    }
  }

  private void testAddTaggedValue(UMLTaggableElement elt, String tvName, String tvValue) {
    elt.addTaggedValue(tvName, tvValue);
  }

  private void testAddTaggedValue(UMLModel model, String fullName, String tvName, String tvValue) {
    UMLPackage pkg = ModelUtil.findPackage(model, fullName);
    if(pkg != null) {
      testAddTaggedValue(pkg, tvName, tvValue);
    } else {
      UMLClass clazz = ModelUtil.findClass(model, fullName);
      if(clazz != null) {
        testAddTaggedValue(clazz, tvName, tvValue);
      } else {
        UMLAttribute att = ModelUtil.findAttribute(model, fullName);
        if(att != null) {
          testAddTaggedValue(att, tvName, tvValue);
        } else {
          Assert.assertTrue("testAddTaggedValue element can't be found: " + fullName , false);
        }
      }
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
		clazz.addTaggedValue("myClassTaggedValue", "test 123");

		for(UMLAttribute att : clazz.getAttributes()) {
			addTaggedValueToAll(att);
		}

		for(UMLAssociation assoc : clazz.getAssociations()) {
			addTaggedValueToAll(assoc);
		}
	}

	private void addTaggedValueToAll(UMLAttribute att) {
		att.addTaggedValue("myAttributeTaggedValue", "A boring, highly uninteresting value");
	}

	private void addTaggedValueToAll(UMLAssociation assoc) {
		assoc.addTaggedValue("myAssociationTaggedValue", "Fill in Assoc TV here.");
		for(UMLAssociationEnd end : assoc.getAssociationEnds()) {
			end.addTaggedValue("myAssociationEndTaggedValue", "Fill in AssocEND TV here.");
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
		for(UMLPackage _pkg : pkg.getPackages()) {
			printPackage(_pkg, pkgDepth + 1);
		}
	}
	private void printClass(UMLClass clazz, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

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

		String subtypeName = gen.getSubtype().getName();
		String supertypeName = gen.getSupertype().getName();

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



  private void init(String filename) {
    try {
      handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
      handler.load("test/testdata/" + filename);
    } catch (XmiException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


	private void init() {
		try {
			handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
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

		//XmiTestCase testCase = new XmiTestCase(args[args.length - 1]);
		String fileName="C:\\DEV\\GIT-WORK\\xmihandler\\test\\testdata\\XMI_Handler_TEST.xmi";
		XmiTestCase testCase = new XmiTestCase(fileName);

		if(Arrays.binarySearch(args, "--no-color") > -1)
			testCase.setNoColor(true);
		else
			System.out.println("run with --no-color if you terminal does not support colors");


		testCase.suite();

	}

}