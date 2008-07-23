package gov.nih.nci.ncicb.xmiinout.util;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class ModelUtil {

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




}
