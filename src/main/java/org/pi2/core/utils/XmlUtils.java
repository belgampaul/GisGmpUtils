package org.pi2.core.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class XmlUtils {


  public static String prettyFormat(String input, int indent, boolean isOmitXmlDeclaration) {
    try {
      Source xmlInput = new StreamSource(new StringReader(input));
      StringWriter stringWriter = new StringWriter();
      StreamResult xmlOutput = new StreamResult(stringWriter);
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      transformerFactory.setAttribute("indent-number", indent);
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      if (isOmitXmlDeclaration) {
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      }
      transformer.transform(xmlInput, xmlOutput);
      return xmlOutput.getWriter().toString();
    } catch (Exception e) {
      throw new RuntimeException(e); // simple exception handling, please review it
    }
  }

  public static String prettyFormat(String input) {
    return prettyFormat(input, 2, true);
  }

  /**
   * src:http://www.journaldev.com/1237/java-convert-string-to-xml-document-and-xml-document-to-string
   * @param doc
   * @return
   */
  public static String convertDocumentToString(Document doc) {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer;
    try {
      transformer = tf.newTransformer();
      // below code to remove XML declaration
      // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(doc), new StreamResult(writer));
      String output = writer.getBuffer().toString();
      return output;
    } catch (TransformerException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * src:http://www.journaldev.com/1237/java-convert-string-to-xml-document-and-xml-document-to-string
   * @param xmlStr
   * @return
   */
  public static Document convertStringToDocument(String xmlStr) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    try {
      builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
      return doc;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String convertToString(Node node) throws ParserConfigurationException {
    return convertDocumentToString(convertToDocument(node));
  }
  public static Document convertToDocument(Node node) throws ParserConfigurationException {



    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(false);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document newDocument = builder.newDocument();
    newDocument.setXmlStandalone(true);
    Node importedNode = newDocument.importNode(node, true);
    newDocument.appendChild(importedNode);
    return newDocument;
  }
}
