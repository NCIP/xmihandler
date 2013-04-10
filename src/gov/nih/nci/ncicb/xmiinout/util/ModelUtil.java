/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.util;

import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.handler.impl.EABaseImpl;

import java.util.*;

import org.apache.log4j.Logger;

public class ModelUtil {

  private static Logger logger = Logger.getLogger(ModelUtil.class.getName());
  /**
   * Util method to find a package in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain"
   */
  public static UMLPackage findPackage(UMLModel model, String fullName) {
    String[] queries = fullName.split("\\.");

    UMLPackage pkg = null;
    
    for(int i = 0; i<queries.length; i++) {
      if(pkg == null) {
        pkg = model.getPackage(queries[i]);
        if(pkg == null)
          return null;
        if(i == queries.length - 1) {
          return pkg;
        }
      } else {
        if(i == queries.length - 1) {
          return pkg.getPackage(queries[i]);
        } else {
          UMLPackage newPkg = pkg.getPackage(queries[i]);
          if(newPkg != null)
            pkg = newPkg;
          else return null;
        }
      }
    }

    return null;
  }
  

  
  /**
   * Util method to find a class in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel"
   */
  public static UMLClass findClass(UMLModel model, String fullClassName) {
    String[] queries = fullClassName.split("\\.");

    UMLPackage pkg = null;
    
    for(int i = 0; i<queries.length; i++) {
      if(pkg == null) {
        pkg = model.getPackage(queries[i]);
        if(pkg == null)
          return null;
      } else {
        UMLPackage newPkg = pkg.getPackage(queries[i]);
        if(newPkg != null)
          pkg = newPkg;
        else if(i == queries.length -1){
          return pkg.getClass(queries[i]);
        } else
          return null;
      }
    }
    return null;
  }

  /**
   * Util method to find a class in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel"
   */
  public static UMLInterface findInterface(UMLModel model, String fullInterfaceName) {
    String[] queries = fullInterfaceName.split("\\.");

    UMLPackage pkg = null;
    
    for(int i = 0; i<queries.length; i++) {
      if(pkg == null) {
        pkg = model.getPackage(queries[i]);
        if(pkg == null)
          return null;
      } else {
        UMLPackage newPkg = pkg.getPackage(queries[i]);
        if(newPkg != null)
          pkg = newPkg;
        else if(i == queries.length -1){
          return pkg.getInterface(queries[i]);
        } else
          return null;
      }
    }
    return null;
  }
  
  /**
   * Util method to find an Attribute in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel.name"
   */
  public static UMLAttribute findAttribute(UMLModel model, String fullAttName) {
    String[] queries = fullAttName.split("\\.");

    UMLPackage pkg = null;
    UMLClass clazz = null;    
    for(String query : queries) {
      if(clazz != null) {
        return clazz.getAttribute(query);
      } else if(pkg == null) {
          pkg = model.getPackage(query);
      } else {
        UMLPackage newPkg = pkg.getPackage(query);
        if(newPkg != null)
          pkg = newPkg;
        else {
          clazz = pkg.getClass(query);
        }
      }
    }
    return null;
  }

  /**
   * Util method to find an Attribute in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel.name"
   */
  public static UMLOperation findClassOperation(UMLModel model, String fullOperName) {
    String[] queries = fullOperName.split("\\.");

    UMLPackage pkg = null;
    UMLClass clazz = null;    
    for(String query : queries) {
      if(clazz != null) {
        return clazz.getOperation(query);
      } else if(pkg == null) {
          pkg = model.getPackage(query);
      } else {
        UMLPackage newPkg = pkg.getPackage(query);
        if(newPkg != null)
          pkg = newPkg;
        else {
          clazz = pkg.getClass(query);
        }
      }
    }
    return null;
  }

  
  /**
   * Util method to find an Attribute in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel.name"
   */
  public static UMLOperation findInterfaceOperation(UMLModel model, String fullOperName) {
    String[] queries = fullOperName.split("\\.");

    UMLPackage pkg = null;
    UMLInterface clazz = null;    
    for(String query : queries) {
      if(clazz != null) {
        return clazz.getOperation(query);
      } else if(pkg == null) {
          pkg = model.getPackage(query);
      } else {
        UMLPackage newPkg = pkg.getPackage(query);
        if(newPkg != null)
          pkg = newPkg;
        else {
          clazz = pkg.getInterface(query);
        }
      }
    }
    return null;
  }
  
  /**
   * Util method to return a package name given a package
   * <br> e.g "java.lang" if package is "lang"
   */
  public static String getFullPackageName(UMLPackage pkg) {

    StringBuilder sb = new StringBuilder();

    while(pkg != null) {
      if(sb.length() > 0)
        sb.insert(0, ".");
      sb.insert(0, pkg.getName());
      
      pkg = pkg.getParent();
    }
    
    return sb.toString();
    
  }

  /**
   * Util method to return a package name given a class
   * <br> e.g "java.lang" if class is "java.lang.Math"
   */
  public static String getFullPackageName(UMLClass clazz) {

    if(clazz == null)
    	return null;
    
	StringBuilder sb = new StringBuilder();

    UMLPackage pkg = clazz.getPackage();
    while(pkg != null) {
      if(sb.length() > 0)
        sb.insert(0, ".");
      sb.insert(0, pkg.getName());
      
      pkg = pkg.getParent();
    }
    
    return sb.toString();
    
  }
  /**
   * Util method to return a package name given an interface
   * <br> e.g "java.lang" if class is "java.lang.Serializable"
   */
  public static String getFullPackageName(UMLInterface interfaze) {

    StringBuilder sb = new StringBuilder();

    UMLPackage pkg = interfaze.getPackage();
    while(pkg != null) {
      if(sb.length() > 0)
        sb.insert(0, ".");
      sb.insert(0, pkg.getName());
      
      pkg = pkg.getParent();
    }
    
    return sb.toString();
    
  }

  /**
   * Util method to return a full name given a class
   * <br> e.g "java.lang.Math"
   */
  public static String getFullName(UMLClass clazz) {

    StringBuilder sb = new StringBuilder(getFullPackageName(clazz));

    sb.append(".");
    sb.append(clazz.getName());
    
    return sb.toString();
    
  }
  
  /**
   * Util method to return a full name given an interface
   * <br> e.g "java.lang.Serializable"
   */
  public static String getFullName(UMLInterface interfaze) {

    StringBuilder sb = new StringBuilder(getFullPackageName(interfaze));

    sb.append(".");
    sb.append(interfaze.getName());
    
    return sb.toString();
    
  }


  /**
   * returns an array of classes this class extends from, or an empty array if none.
   */ 
  public static UMLClass[] getSuperclasses(UMLClass clazz) {

    if(clazz == null)
      return null;

    List<UMLGeneralization> gens = clazz.getGeneralizations();

    List<UMLClass> classes = new ArrayList<UMLClass>();
    for(UMLGeneralization gen : gens) {
      if(gen.getSupertype() instanceof UMLClass && ((UMLClass)gen.getSupertype()) != clazz) 
        classes.add((UMLClass)gen.getSupertype());
    }

    UMLClass[] result = new UMLClass[classes.size()];
    result = classes.toArray(result);

    return result;
  }
  
  /**
   * returns an array of interfaces this Interface extends from, or an empty array if none.
   */ 
  public static UMLInterface[] getSuperInterfaces(UMLInterface interfaze) {

    if(interfaze == null)
      return null;

    List<UMLGeneralization> gens = interfaze.getGeneralizations();

    List<UMLInterface> interfaces = new ArrayList<UMLInterface>();
    for(UMLGeneralization gen : gens) {
      if(gen.getSupertype() instanceof UMLInterface && ((UMLInterface)gen.getSupertype()) != interfaze) 
    	  interfaces.add((UMLInterface)gen.getSupertype());
    }

    UMLInterface[] result = new UMLInterface[interfaces.size()];
    result = interfaces.toArray(result);

    return result;
  }  
  
  /**
   * returns an array of classes this class extends from, or an empty array if none.
   */ 
  public static UMLInterface[] getInterfaces(UMLClass clazz) {

    if(clazz == null)
      return null;

    Set<UMLDependency> deps = clazz.getDependencies();

    List<UMLInterface> interfaces = new ArrayList<UMLInterface>();
    for(UMLDependency dep : deps) {
      if(dep.getSupplier() instanceof UMLInterface) //&& dep.getStereotype().equalsIgnoreCase( "realize ) 
    	  interfaces.add((UMLInterface)dep.getSupplier());
    }

    UMLInterface[] result = new UMLInterface[interfaces.size()];
    result = interfaces.toArray(result);

    return result;
  }

	public static boolean isOperationStatic(UMLOperation operation)
	{
		UMLTaggedValue isStaticTag = operation.getTaggedValue("static", false);
		
		if(isStaticTag != null && isStaticTag.getValue().equals("1"))
			return true;
		
		return false;
	}

	public static boolean isOperationAbstract(UMLOperation operation)
	{
		UMLTaggedValue isAbstractTag = operation.getTaggedValue("isAbstract", false);
		
		if(isAbstractTag != null && isAbstractTag.getValue().equals("1"))
			return true;
		
		return false;
	}

	public static boolean isOperationFinal(UMLOperation operation)
	{
		UMLTaggedValue isFinalTag = operation.getTaggedValue("const", false);
		
		if(isFinalTag != null && isFinalTag.getValue().equals("true"))
			return true;
		
		return false;
	}
	
	public static String getOperationReturnType(UMLOperation operation)
	{
		List<UMLOperationParameter> params = operation.getParameters();
		for(UMLOperationParameter param : params)
		{
			if((param.getName() != null && param.getName().equals("return")) || (param.getKind() != null && param.getKind().equals("return")))
				return param.getDataType().getName();
		}

		return "void";
	}
	
	public static String getOperationExceptions(UMLOperation operation)
	{
		UMLTaggedValue returnTypeTag = operation.getTaggedValue("throws", false);
		
		if(returnTypeTag != null)
			return returnTypeTag.getValue();
		
		return null;
	}

	public static boolean isOperationArrayReturnType(UMLOperation operation)
	{
		UMLTaggedValue returnArrayTypeTag = operation.getTaggedValue("returnarray", false);
		
		if(returnArrayTypeTag != null && returnArrayTypeTag.getValue().equals("1"))
			return true;
		
		return false;
	}
	
	
	public static boolean isOperationSynchronised(UMLOperation operation)
	{
		UMLTaggedValue isSyncTag = operation.getTaggedValue("synchronised", false);
		
		if(isSyncTag != null && isSyncTag.getValue().equals("1"))
			return true;
		
		return false;
	}
	
	//Modifier(s), return type, method name, parameter list, exception list, method body
	public static String getOperationSignature(UMLOperation operation, boolean includeException)
	{
		StringBuffer str = new StringBuffer();
		if(operation.getVisibility() != null)
			str.append(operation.getVisibility().getName());
		
		if(operation.getAbstractModifier().isAbstract())
			str.append(" abstract");
		
		if(operation.getFinalModifier().isFinal())
			str.append(" final");

		if(operation.getStaticModifier().isStatic())
			str.append(" static");
		
		if(operation.getSynchronizedModifier().isSynchronized())
			str.append(" synchronized");
		
		str.append(" "+getOperationReturnType(operation));
		if(isOperationArrayReturnType(operation))
			str.append("[]");
		
		str.append(" " + getOperationName(operation, true));
		if(includeException)
		{
			String exceptions = getOperationExceptions(operation);
			
			if(exceptions != null)
			{
				str.append(" throws ");
				str.append(exceptions);
			}
		}
		return str.toString();
	}

	//Modifier(s), return type, method name, parameter list, exception list, method body
	public static String getOperationName(UMLOperation operation, boolean actualParamName)
	{
		logger.debug("Finding operation name for: "+operation.getName());
		StringBuffer str = new StringBuffer();
		str.append(operation.getName());
		str.append("(");
		List<UMLOperationParameter> params = operation.getParameters();
		boolean hasParams = false;
		if(params != null && params.size() > 0)
		{
			int i = 0;
			for(UMLOperationParameter param : params)
			{
				String paramText = null;
				String paramTypeText = null;
				if(actualParamName)
					paramText = getOperationParameterText(param);
				else
				{
					paramTypeText = getOperationParameterType(param);
					if(paramTypeText == null)
						continue;
					logger.debug("Finding operation name for: "+param.getKind());
					logger.debug("Finding operation name for: "+param.getDataType());
					logger.debug("Finding operation name paramTypeText: "+paramTypeText);
					paramText = paramTypeText + " param"+i;
				}
				
				if(paramText != null)
				{
					if(hasParams)
						str.append(",");
					str.append(paramText);
					hasParams = true;
				}
			}
		}
		str.append(")");
		return str.toString();
	}
	
	public static String getOperationBody(UMLOperation operation)
	{
		if(operation == null)
			return null;
		
		return operation.getSpecification();
	}

	
	public static String getOperationParameterText(UMLOperationParameter param)
	{
		String type = getOperationParameterType(param);
		if(type != null)
			return type+ " " + param.getName();
		
		return null; 	
	}

	public static String getOperationParameterType(UMLOperationParameter param)
	{
		if(param.getKind() != null && param.getKind().equals("return"))
			return null;
		else if(param.getKind() != null && param.getKind().equals("in"))
			return param.getTaggedValue("type").getValue();
		else if(param.getKind() == null && param.getDataType() != null )
			return param.getDataType().getName();
		
		return null; 	
	}
	
}
