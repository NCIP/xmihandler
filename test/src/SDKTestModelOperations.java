/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;
import gov.nih.nci.ncicb.xmiinout.util.ModelUtil;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class SDKTestModelOperations extends TestCase {

	private static Logger logger = Logger.getLogger(SDKTestModel_TestCase.class.getName());

	private XmiInOutHandler handler = null;
	private String filename;
	private String handlerEnumType;
	private String newFileExtension;
	private String modelName;

	public SDKTestModelOperations(String filename, String handlerEnumType, String newFileExtension, String modelName) {
		this.filename = filename;
		this.handlerEnumType = handlerEnumType;
		this.newFileExtension = newFileExtension;
		this.modelName = modelName;
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


	private void testGetModel() {
		UMLModel model = handler.getModel();
		System.out.println("Model: " + model.getName());
	}

	private UMLModel testGetModel(String modelName) {
		UMLModel model;
		if(modelName == null)
			model = handler.getModel();
		else
			model = handler.getModel(modelName);

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

	public void suite() {

		init();

		//UMLModel model = testGetModel(modelName);
		UMLModel model = testGetModel(null);
		//TestUtil.printModel(model);

		testGetFullPackageName(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site");
		testSite(model);
		testTestOperation(model);

		testSaveModel();

		testLoadModel(filename + newFileExtension);
		//model = testGetModel(modelName);
		model = testGetModel(null);
		testGetFullPackageName(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site");
		testSite(model);
		testTestOperation(model);
		//TestUtil.printModel(model);


	}


	private void testSite(UMLModel model)
	{
		testFindClass(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site");
		testFindOperations(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site", "setActivityStatus", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.Site", "validateStatus", false);
	}

	private void testTestOperation(UMLModel model)
	{
		testFindClass(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass");
		testFindOperations(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", "testOperation1", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", "testOperation2", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", "testOperation3", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", "testOperation4", false);
		assertOperation(model, "Logical View.Logical Model.gov.nih.nci.cacoresdk.domain.operations.TestOperationClass", "testOperation5", false);
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

	private void testFindOperations(UMLModel model, String fullClassName, boolean interfaze) {

		List<UMLOperation> operations;
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		if(interfaze)
		{
			UMLInterface clazz = ModelUtil.findInterface(model, fullClassName);
			Assert.assertNotNull("Class not found -- " + fullClassName + " -- " + className, clazz);
			operations = clazz.getOperations();
		}
		else
		{
			UMLClass clazz = ModelUtil.findClass(model, fullClassName);
			Assert.assertNotNull("Class not found -- " + fullClassName + " -- " + className, clazz);
			operations = clazz.getOperations();
		}

		System.out.println("Operations for class: "+fullClassName);

		for(UMLOperation operation : operations)
		{
			System.out.println("Operation signature: "+ModelUtil.getOperationSignature(operation, true));
			System.out.println("Operation body: "+ModelUtil.getOperationBody(operation));
		}
	}

	private void assertOperation(UMLModel model, String fullClassName, String operationName, boolean interfaze) {

		List<UMLOperation> operations;
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		if(interfaze)
		{
			UMLInterface clazz = ModelUtil.findInterface(model, fullClassName);
			Assert.assertNotNull("Class not found -- " + fullClassName + " -- " + className, clazz);
			operations = clazz.getOperations();
		}
		else
		{
			UMLClass clazz = ModelUtil.findClass(model, fullClassName);
			Assert.assertNotNull("Class not found -- " + fullClassName + " -- " + className, clazz);
			operations = clazz.getOperations();
		}

		System.out.println("Operations for class: "+fullClassName);
		boolean found = false;

		for(UMLOperation operation : operations)
		{
			if(operation.getName().equals(operationName))
			{
				found = true;
				if(operation.getName().equals("setActivityStatus"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public void setActivityStatus(String status) ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					Assert.assertEquals("activityStatus=status;\nvalidateStatus(activityStatus);\n", opBody);
				}
				else if(operation.getName().equals("validateStatus"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public void validateStatus(String status) throws Exception", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					Assert.assertEquals("if(status == null) throw new Exception(\"Status cannot be null\");\nelse if(status.length() == 0)  throw new Exception(\"Status cannot be empty\");", opBody);
				}
				else if(operation.getName().equals("testOperation1"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public abstract Address testOperation1() ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					Assert.assertNull(opBody);
				}
				else if(operation.getName().equals("testOperation2"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public void testOperation2(Address address) ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					Assert.assertNull(opBody);
				}
				else if(operation.getName().equals("testOperation3"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public String[] testOperation3() ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					//Assert.assertEquals("if(status == null) throw new Exception(\"Status cannot be null\");\nelse if(status.length() == 0)  throw new Exception(\"Status cannot be empty\");", opBody);
					Assert.assertNull(opBody);
				}
				else if(operation.getName().equals("testOperation4"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public synchronised Address testOperation4(String value) ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					//Assert.assertEquals("if(status == null) throw new Exception(\"Status cannot be null\");\nelse if(status.length() == 0)  throw new Exception(\"Status cannot be empty\");", opBody);
					Assert.assertNull(opBody);
				}
				else if(operation.getName().equals("testOperation5"))
				{
					String opSign = ModelUtil.getOperationSignature(operation, true);
					Assert.assertEquals("public final static synchronised void testOperation5(boolean value) ", opSign);
					String opBody = ModelUtil.getOperationBody(operation);
					Assert.assertEquals("return;", opBody);

				}
			}

		}
		Assert.assertTrue("Operatation name: "+operationName + " cannot be found in class: "+fullClassName, found);
	}



/*	public static void main(String[] args) {

		String[] arg = {"C:\\Prasad\\OM\\xmihandler\\test\\testdata\\sdk_New_6.xmi", "EADefault", "new", "EA Model"};
		SDKTestModelOperations testCase = new SDKTestModelOperations(arg[0], arg[1], arg[2], arg[3]);
		testCase.suite();

	}
*/
	public static void main(String[] args) {

		String[] arg = {"C:\\DEV\\GIT-WORK\\xmihandler\\test\\testdata\\sdk_test.uml", "ArgoUMLDefault", "new", "EA Model"};
		SDKTestModelOperations testCase = new SDKTestModelOperations(arg[0], arg[1], arg[2], arg[3]);
		testCase.suite();

	}

}