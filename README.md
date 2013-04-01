Welcome to the XMI Handler Project!
=====================================

The XMI Handler provides a set of API to read from and write into XMI files. XML Metadata Interchange (XMI) is an interchange format for UML models. The XMI Handler parses a given XMI file and represent the model in Java objects. The API works with both ArgoUML and Enterprise Architect created XMI models. 
 
A UML Class Diagram is the only kind of UML model that the XMI Handler currently supports. It supports class diagrams representing the static items that exist in a system, their structure, their operations, thier tag values and their relationships. Class diagrams are usually used to depict the logical (e.g. logical model) and physical (e.g. data model) design of the system. The XMI Handler is distributed under the BSD 3-Clause License.
Please see the NOTICE and LICENSE files for details.

You will find more details about XMI Handler in the following links:

 * [NCI OSDI] (https://wiki.nci.nih.gov/display/caBIGCommunityCode/CBIIT+Open+Source+Development+Initiative)
 * [Forum] (https://cabig-kc.nci.nih.gov/CaGrid/forums/viewforum.php?f=31&sid=317abcd4b7ae1f07e547e12f9c2b9059)
 * [Issue Tracker] (https://tracker.nci.nih.gov/browse/SDK)
 * [Code Repository] (https://github.com/NCIP/xmihandler)

Please join us in further developing and improving XMI Handler.

# Prerequisites
 * JDK 1.6.x
 * Ant 1.6.x
 
# Build
 * ant dist