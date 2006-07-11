package gov.nih.nci.ncicb.xmiinout.writer;

public interface UMLWriter {
  
//   public void write(UMLModel model);
//   public void write(UMLPackage pkg);
//   public void write(UMLClass clazz);
//   public void write(UMLAttribute attribute);
//   public void write(UMLTaggedValue tv);

  public UMLClassWriter getUMLClassWriter();
  public UMLAttributeWriter getUMLAttributeWriter();

  public UMLTaggedValueWriter getUMLTaggedValueWriter();

  public UMLModelWriter getUMLModelWriter();

  public UMLPackageWriter getUMLPackageWriter();

  public UMLDependencyWriter getUMLDependencyWriter();
  
}