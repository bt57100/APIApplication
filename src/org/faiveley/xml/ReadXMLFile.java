/*
 * Faiveley Transport License
 */
package org.faiveley.xml;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.faiveley.model.Environment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Read an XML file and get the environment list
 * 
 * @author 813308
 */
public class ReadXMLFile {

    /**
     * Read an XML file and get the environment list
     * 
     * @param filePath path to XML file 
     * @param envList get environment list
     */
    public void readXMLFile(String filePath, List<Environment> envList) {
        try {
            // Create document builder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filePath);

            // Normalize the file if it is not well written
            doc.getDocumentElement().normalize();

            // Get all environment list
            NodeList nList = doc.getElementsByTagName("environment");

            /* Recup all environment */
            for (int temp = 0; temp < nList.getLength(); temp++) {
                // Get current environment
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Element to read
                    Element eElement = (Element) nNode;
                    
                    // Update return environment list
                    envList.add(new Environment(
                            eElement.getAttribute("environmentID"),
                            eElement.getElementsByTagName("host").item(0).getTextContent(),
                            Integer.parseInt(eElement.getElementsByTagName("port").item(0).getTextContent()),
                            eElement.getElementsByTagName("login").item(0).getTextContent(),
                            eElement.getElementsByTagName("password").item(0).getTextContent()));

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
        }
    }
}
