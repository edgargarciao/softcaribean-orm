package com.softcaribbean.orm.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.softcaribbean.orm.constants.ORM;

public class XmlMgr {

  public Properties getProperties(String xmlName) {
    Properties properties = new Properties();
    File file = new File(getClass().getClassLoader().getResource(xmlName).getFile());

    try {

      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

      if (!doc.getDocumentElement().getNodeName().equals(ORM.SOFTCARIBBEAN_CONFIGURATION)) {
        throw new RuntimeException(ORM.ERROR_SOFTCARIBBEAN_CONFIGURATION);
      }

      if (!doc.getDocumentElement().getChildNodes().item(1).getNodeName()
          .equals(ORM.SESSION_FACTORIA)) {
        throw new RuntimeException();
      }

      if (doc.hasChildNodes()) {
        printNote(doc.getChildNodes(),properties);
      }

    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }

    return properties;
  }

  private void printNote(NodeList nodeList, Properties propiedades) {

    for (int count = 0; count < nodeList.getLength(); count++) {

      Node tempNode = nodeList.item(count);

      // make sure it's element node.
      if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

        // get node name and value
        //System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
        // System.out.println("Node Value =" + tempNode.getTextContent());

        if ((tempNode.getNodeName().equals(ORM.PROPIEDAD) ||  tempNode.getNodeName().equals(ORM.MAPEADOR))
            && tempNode.hasAttributes()) {

          // get attributes names and values
          NamedNodeMap nodeMap = tempNode.getAttributes();

          for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            
            if (node.getNodeValue().equals(ORM.SOFTCARIBBEAN_DRIVER_CLASS)
                || node.getNodeValue().equals(ORM.SOFTCARIBBEAN_URL)
                || node.getNodeValue().equals(ORM.SOFTCARIBBEAN_USERNAME)
                || node.getNodeValue().equals(ORM.SOFTCARIBBEAN_PASSWORD)
                ) {
              propiedades.setProperty(node.getNodeValue(), tempNode.getTextContent());
            }else if(tempNode.getNodeName().equals(ORM.MAPEADOR)) {
              propiedades.setProperty(ORM.MAPEADOR, node.getNodeValue());
            }
          }
          
        }
        //System.out.println("value --> "+tempNode.getTextContent());
        if (tempNode.hasChildNodes()) {
          // loop again if has child nodes
          printNote(tempNode.getChildNodes(), propiedades);

        }
      }
    }
  }

}
