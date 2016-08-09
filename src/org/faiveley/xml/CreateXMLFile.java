/*
 * Faiveley Transport License
 */
package org.faiveley.xml;

import java.io.File;
import java.net.URL;
import java.util.List;
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

/**
 * Create or overwrite a XML file with the environment list given
 * 
 * @author 813308
 */
public class CreateXMLFile {

    /**
     * Create or overwrite a XML file with the environment list given
     * 
     * @param filePath path to XML file
     * @param environmentList environment list to write in XML file
     */
    public void createXMLFile(String filePath, List<Environment> environmentList) {
        try {
            // Create document builder
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("Faiveley");
            doc.appendChild(root);

            /* Write all environments in the buffer */
            for (Environment environment : environmentList) {

                // Define new environment
                Element environments = doc.createElement("environment");
                root.appendChild(environments);

                // Set ID attribut
                environments.setAttribute("environmentID", environment.getName());

                // Set Host in current environment
                Element hosts = doc.createElement("host");
                hosts.appendChild(doc.createTextNode(String.valueOf(environment.getHost())));
                environments.appendChild(hosts);

                // Set Port in current environment
                Element ports = doc.createElement("port");
                ports.appendChild(doc.createTextNode(String.valueOf(environment.getPort())));
                environments.appendChild(ports);

                // Set Login in current environment
                Element logins = doc.createElement("login");
                logins.appendChild(doc.createTextNode(String.valueOf(environment.getLogin())));
                environments.appendChild(logins);

                // Set Password in current environment
                Element passwords = doc.createElement("password");
                passwords.appendChild(doc.createTextNode(String.valueOf(environment.getPassword())));
                environments.appendChild(passwords);

            }

            // Write the content of the buffer into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            // Display result
            System.out.println("File saved!");

        } catch (ParserConfigurationException | TransformerException pce) {
        }
    }
}
