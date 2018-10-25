package com.softcaribbean.orm.factory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuracion {

  public static final String SOFTCARIBBEAN_CONFIGURATION    = "softcaribbean-configuration";
  public static final String SESSION_FACTORIA               = "sesion-factoria";
  public static final String SOFTCARIBBEAN_DRIVER_CLASS     = "softcaribbean.driver_class";
  public static final String SOFTCARIBBEAN_URL              = "softcaribbean.connection.url";
  public static final String SOFTCARIBBEAN_USERNAME         = "softcaribbean.connection.username";
  public static final String SOFTCARIBBEAN_PASSWORD         = "softcaribbean.connection.password";
  
  public static final String ERROR_SOFTCARIBBEAN_CONFIGURATION = "El xml debe iniciar con la etiqueta ";
  public static final String ERROR_SESSION_FACTORIA = "Despues de la etiqueta debe contener la etiqueta softcaribbean-configuration";
  
  private DatosConexion datosConexion;
  public Properties properties;

  
  public Configuracion(String fileName) {

    
    
    File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

    try {

      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

      if (!doc.getDocumentElement().getNodeName().equals(SOFTCARIBBEAN_CONFIGURATION)) {
        throw new RuntimeException(ERROR_SOFTCARIBBEAN_CONFIGURATION);
      }
      
      if (!doc.getDocumentElement().getChildNodes().item(1).getNodeName().equals(SESSION_FACTORIA)) {
        throw new RuntimeException();
      }

      if (doc.hasChildNodes()) {
        printNote(doc.getChildNodes());
      }

    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }


  private void printNote(NodeList nodeList) {

    for (int count = 0; count < nodeList.getLength(); count++) {

      Node tempNode = nodeList.item(count);

      // make sure it's element node.
      if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

        // get node name and value
        System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
        // System.out.println("Node Value =" + tempNode.getTextContent());

        if (tempNode.hasAttributes()) {

          // get attributes names and values
          NamedNodeMap nodeMap = tempNode.getAttributes();

          for (int i = 0; i < nodeMap.getLength(); i++) {
            Node node = nodeMap.item(i);
            
            if(node.getNodeName().equals("")){
              
            }
            
            System.out.println("attr name : " + node.getNodeName());
            System.out.println("attr value : " + node.getNodeValue());
          }
        }

        if (tempNode.hasChildNodes()) {
          // loop again if has child nodes
          printNote(tempNode.getChildNodes());

        }
        System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
      }
    }
  }
  
  
  public SesionFactoria crearSesionFactoria(){
    return new SesionFactoria();
  }

  public static void main(String[] args) {
    Configuracion c = new Configuracion("application.xml");
  }


}
