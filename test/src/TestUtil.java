/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
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


public class TestUtil {

	static final boolean noColor = true;

	static final short RED = 31, GREEN = 32,
	BLACK = 30, YELLOW = 33, BLUE = 34,
	MAGENTA = 35, CYAN = 36, WHITE = 37; 

	public static UMLClass findClass(UMLModel model, String className) 
	{
		for(UMLPackage pkg : model.getPackages()) {
			UMLClass c = findClass(pkg, className);
			if(c != null)
				return c;
		}    
		return null;
	}

	public static UMLClass findClass(UMLPackage pkg, String className) {
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

	public static void printModel(UMLModel model) {
		System.out.println(model.getName());
		for(UMLPackage pkg : model.getPackages()) {
			printPackage(pkg, 0);
		}
		for(UMLClass clazz : model.getClasses()) {
			printClass(clazz, 0);
		}
	}
	public static void printPackage(UMLPackage pkg, int pkgDepth) {
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
	
	public static void printClass(UMLClass clazz, int pkgDepth) {
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
	
	public static void printInterface(UMLInterface interfaze, int pkgDepth) {
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

	public static void printAssociation(UMLAssociation assoc, int pkgDepth) {
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


	public static void printGeneralization(UMLGeneralization gen, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("  ");

		String subtypeName = gen.getSubtype().getName();
		String supertypeName = gen.getSupertype().getName();
		
		printInColor(GREEN, "Generalization: " + subtypeName + " --> " + supertypeName);
		System.out.println("");
	}

	public static void printDependency(UMLDependency dep, int pkgDepth) {
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


	public static void printAttribute(UMLAttribute att, int pkgDepth) {
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


	public static void printTaggedValue(UMLTaggedValue tv, int pkgDepth) {
		for(int i = 0; i < pkgDepth; i++)
			System.out.print("  ");
		System.out.print("   ");

		printInColor(YELLOW, "tv " + tv.getName() + " : " + tv.getValue());

		System.out.println();
	}

	public static void printInColor(short color, String text) {
		if(noColor)
			System.out.print(text);
		else
			System.out.print((char)27 + "[0;" + color + ";40m" + text + (char)27 + "[0;37;40m");
	}


}
