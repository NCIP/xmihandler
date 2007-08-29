package gov.nih.nci.ncicb.xmiinout.writer;

public interface UMLWriter {
  
  public UMLClassWriter getUMLClassWriter();

  public UMLAttributeWriter getUMLAttributeWriter();

  public UMLAssociationWriter getUMLAssociationWriter();

  public UMLAssociationEndWriter getUMLAssociationEndWriter();

  public UMLTaggedValueWriter getUMLTaggedValueWriter();

  public UMLModelWriter getUMLModelWriter();

  public UMLPackageWriter getUMLPackageWriter();

  public UMLDependencyWriter getUMLDependencyWriter();
  
}