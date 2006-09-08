package gov.nih.nci.ncicb.xmiinout.util;

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class ModelUtil {

  
  /**
   * Util method to find a class in a model given it's fully qualified name. <br>
   * Packages are separated by '.'. <br> Sample query string could be:
   * <br> "gov.nih.nci.xmiinout.domain.UMLModel"
   */
  public static UMLClass findClass(UMLModel model, String fullClassName) {
    String[] queries = fullClassName.split("\\.");

    UMLPackage pkg = null;
    
    for(String query : queries) {
      if(pkg == null) {
        pkg = model.getPackage(query);
      } else {
        UMLPackage newPkg = pkg.getPackage(query);
        if(newPkg != null)
          pkg = newPkg;
        else {
          return pkg.getClass(query);
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
   * returns an array of classes this class extends from, or an empty array if none.
   */ 
  public static UMLClass[] getSuperclasses(UMLClass clazz) {

    if(clazz == null)
      return null;

    List<UMLGeneralization> gens = clazz.getGeneralizations();

    List<UMLClass> classes = new ArrayList<UMLClass>();
    for(UMLGeneralization gen : gens) {
      if(gen.getSupertype() != clazz) 
        classes.add(gen.getSupertype());
    }

    UMLClass[] result = new UMLClass[classes.size()];
    result = classes.toArray(result);

    return result;
  }



//   /**
//    * returns the full name for an association end.
//    * <br> including package name, other end's class name and end role name
//    * <br> returns null if the end is not navigable or the role name is not set.
//    */
//   public static String getFullName(UMLAssociationEnd end) {

//     StringBuilder sb = new StringBuilder(getFullPackageName(clazz));

//     sb.append(".");
//     sb.append(clazz.getName());
    
//     return sb.toString();
    
//   }

}
