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

  

}
