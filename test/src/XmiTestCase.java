
import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.handler.*;

import java.io.IOException;

import java.util.Arrays;

public class XmiTestCase {

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
      handler.load(f);
    } catch (XmiException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
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

  private void testAddTaggedValue() {
    
  }

  private void suite() {
    
    init();
    
    UMLModel model = testGetModel("EA Model");

    printModel(model);

    addTaggedValueToAll(model);

    addDependency(model);

    testSaveModel();

    testLoadModel(filename + ".new.xmi");
    model = testGetModel("EA Model");
    printModel(model);

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
  }

  private void addTaggedValueToAll(UMLAttribute att) {
    att.addTaggedValue("myTaggedValue", "A boring, highly uninteresting value");
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
                 + srcEnd.getUMLClass().getName() 
                 + "(" + srcEnd.getRoleName() + ")"
                 + "[" + srcEnd.getLowMultiplicity() + ".."
                 + srcEnd.getHighMultiplicity() + "]"
                 + (srcEnd.isNavigable()?"<":"")
                 + "--"
                 + (targetEnd.isNavigable()?">":"")
                 + targetEnd.getUMLClass().getName() 
                 + "(" + targetEnd.getRoleName() + ")"
                 + "[" + targetEnd.getLowMultiplicity() + ".."
                 + targetEnd.getHighMultiplicity() + "]"
                 );

    System.out.println("");
  }

  private void printGeneralization(UMLGeneralization gen, int pkgDepth) {
    for(int i = 0; i < pkgDepth; i++)
      System.out.print("  ");
    System.out.print("  ");

    printInColor(GREEN, "Generalization: " + gen.getSubtype().getName() + " --> " + gen.getSupertype().getName());
    System.out.println("");
  }

  private void printDependency(UMLDependency dep, int pkgDepth) {
    for(int i = 0; i < pkgDepth; i++)
      System.out.print("  ");
    System.out.print("  ");

    printInColor(GREEN, "Dependency: " + ((UMLClass)dep.getClient()).getName() + " --> " + ((UMLClass)dep.getSupplier()).getName());
    System.out.println("");
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
    
    XmiTestCase testCase = new XmiTestCase(args[args.length - 1]);
    
    if(Arrays.binarySearch(args, "--no-color") > -1)
      testCase.setNoColor(true);
    else 
      System.out.println("run with --no-color if you terminal does not support colors");
      

    testCase.suite();

  }

}