/*
 * Faiveley Transport License
 */
package org.faiveley.xml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.faiveley.model.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Update an XML file
 *
 * @author 813308
 */
public class UpdateXMLFile {

    /**
     * Update or delete environment in XML file
     *
     * @param filePath path to XML file
     * @param state true = update, false = delete
     * @param idToUpdate id to update or delete
     * @param updatedEnvironment environment list to write in xml file
     */
    public void updateXMLFile(String filePath, boolean state, String idToUpdate, Environment updatedEnvironment) {
        try {
            // Initialisation
            int positionToUpdate = -1;

            // Create document builder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filePath);

            /* Get the environment to modify by id */
            // All environment list
            NodeList environmentList = doc.getElementsByTagName("environment");
            for (int i = 0; i < environmentList.getLength(); i++) {
                // Current environment
                Element currentEnvironment = (Element) environmentList.item(i);
                // Save position if same ID as ID to update
                if (idToUpdate.equals(currentEnvironment.getAttribute("environmentID"))) {
                    positionToUpdate = i;
                }

            }

            /* If position to update found, update data */
            if (positionToUpdate < 0) {

                System.out.println("ID to update not found");

                /* Add new environment */
                Element root = doc.getDocumentElement();

                // Define new environment
                Element environments = doc.createElement("environment");

                // Set ID attribut
                environments.setAttribute("environmentID", updatedEnvironment.getName());

                // Set Host in current environment
                Element hosts = doc.createElement("host");
                hosts.appendChild(doc.createTextNode(String.valueOf(updatedEnvironment.getHost())));
                environments.appendChild(hosts);

                // Set Port in current environment
                Element ports = doc.createElement("port");
                ports.appendChild(doc.createTextNode(String.valueOf(updatedEnvironment.getPort())));
                environments.appendChild(ports);

                // Set Login in current environment
                Element logins = doc.createElement("login");
                logins.appendChild(doc.createTextNode(String.valueOf(updatedEnvironment.getLogin())));
                environments.appendChild(logins);

                // Set Password in current environment
                Element passwords = doc.createElement("password");
                passwords.appendChild(doc.createTextNode(String.valueOf(updatedEnvironment.getPassword())));
                environments.appendChild(passwords);

                // Add
                root.appendChild(environments);
                System.out.println("New environment added");

            } else {

                // Get environment to update with position
                Node environment = doc.getElementsByTagName("environment").item(positionToUpdate);

                // Update mode
                if (state == true) {
                    // Get components of environment to update
                    NodeList environmentComponents = environment.getChildNodes();

                    // Update data
                    for (int i = 0; i < environmentComponents.getLength(); i++) {
                        // Get current component to update
                        Node currentElement = environmentComponents.item(i);

                        if ("environmentID".equals(currentElement.getNodeName())) {
                            currentElement.setTextContent(updatedEnvironment.getName());
                        }
                        if ("host".equals(currentElement.getNodeName())) {
                            currentElement.setTextContent(updatedEnvironment.getHost());
                        }
                        if ("port".equals(currentElement.getNodeName())) {
                            currentElement.setTextContent(Integer.toString(updatedEnvironment.getPort()));
                        }
                        if ("login".equals(currentElement.getNodeName())) {
                            currentElement.setTextContent(updatedEnvironment.getLogin());
                        }
                        if ("password".equals(currentElement.getNodeName())) {
                            currentElement.setTextContent(updatedEnvironment.getPassword());
                        }
                    }
                } // Delete mode
                else {

                    Node faiveley = doc.getElementsByTagName("Faiveley").item(0);
                    faiveley.removeChild(environment);

                }
            }
            // Write the update into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("Update done.");
        } catch (ParserConfigurationException | TransformerException pce) {
        } catch (SAXException | IOException ex) {
            Logger.getLogger(UpdateXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
