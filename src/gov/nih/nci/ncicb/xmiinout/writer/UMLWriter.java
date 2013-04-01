/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright Science Applications International Corporation
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/xmihandler/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.xmiinout.writer;

public interface UMLWriter {
  
  public UMLClassWriter getUMLClassWriter();
  
  public UMLInterfaceWriter getUMLInterfaceWriter();

  public UMLAttributeWriter getUMLAttributeWriter();

  public UMLAssociationWriter getUMLAssociationWriter();

  public UMLAssociationEndWriter getUMLAssociationEndWriter();

  public UMLTaggedValueWriter getUMLTaggedValueWriter();

  public UMLModelWriter getUMLModelWriter();

  public UMLPackageWriter getUMLPackageWriter();

  public UMLDependencyWriter getUMLDependencyWriter();
  
  public UMLStereotypeWriter getUMLStereotypeWriter();  
  
  public UMLOperationWriter getUMLOperationWriter();
  
  public UMLOperationParameterWriter getUMLOperationParameterWriter();
}