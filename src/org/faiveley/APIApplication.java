/*
 * Faiveley Transport License
 */
package org.faiveley;

import com.sun.javaws.Main;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.faiveley.model.Environment;
import org.faiveley.model.EnvironmentWrapper;

/**
 * Main class contains main(String[] args)
 *
 * @author 813308
 */
public class APIApplication extends Application {

    private Stage primaryStage;
    public Preferences prefs = Preferences.userNodeForPackage(APIApplication.class);
    private final ObservableList<Environment> listEnvironment = FXCollections.observableArrayList();

    
    /**
     * Get current stage
     * 
     * @return current stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Set current stage
     * 
     * @param primaryStage stage to set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Get all environment list
     * 
     * @return all environment list
     */
    public ObservableList<Environment> getListEnvironnement() {
        return listEnvironment;
    }

    /**
     * Get file from pref file path
     * 
     * @return file of pref file path
     */
    public File getDataDirectoryPath() {
        // Get file path from pref
        String filePath = prefs.get("filePath", null);
        
        // Return file if found
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Set file path in pref
     * 
     * @param file file to get path from
     */
    public void setRegistryFilePath(File file) {
        // If file found set path
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
    }

    /**
     * Load data from file in the given directory according to wrapper
     * 
     * @param directory directory of the file to retrieve data from
     */
    public void loadDataDirectory(File directory) {
        // Context
        JAXBContext context;
        
        // Reader
        Unmarshaller um;
        try {
            if (directory.exists() && directory.isDirectory()) {
                // All files in directory
                File[] listFile = directory.listFiles();
                for (File f : listFile) {
                    if (f.getName().startsWith("environment")) {
                        // Context
                        context = JAXBContext.newInstance(EnvironmentWrapper.class);
                        
                        // Reader
                        um = context.createUnmarshaller();
                        EnvironmentWrapper wrapper = (EnvironmentWrapper) um.unmarshal(f);
                        
                        // Add all environment found
                        listEnvironment.clear();
                        listEnvironment.addAll(wrapper.getEnvironments());
                    }
                }
            }
            // Set path in pref
            setRegistryFilePath(directory);
        } catch (Exception e) {
        }
    }

    /**
     * Save data in file
     * 
     * @param file 
     */
    public void saveDataToFile(File file) {
        try {
            // Save environment
            saveEnvironment(file);
            
            // Set path in pref
            setRegistryFilePath(file);
        } catch (Exception e) {
        }
    }

    /**
     * Save environment in XML file
     * 
     * @param file file to save in
     * @throws PropertyException Property Exception
     * @throws JAXBException JAXB Exception 
     */
    private void saveEnvironment(File file) throws PropertyException, JAXBException {
        if (listEnvironment.size() > 0) {
            // Contexte
            JAXBContext context = JAXBContext.newInstance(EnvironmentWrapper.class);
            
            // Writter
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            // Wrapper
            EnvironmentWrapper wrapper = new EnvironmentWrapper();
            wrapper.setEnvironments(listEnvironment);
            
            // Write data in file
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "environment.xml");
            m.marshal(wrapper, fileData);
        }
    }

    /**
     * Close current view and open a new one
     * 
     * @param viewPath path to view to open
     * @param viewName window title
     */
    public void openView(String viewPath, String viewName) {
        try {
            // Hide Delete current DEnvironment view
            hideCurrentView();

            // Open new view
            this.primaryStage.setTitle(viewName);
            this.primaryStage.setScene(new Scene(FXMLLoader.load(APIApplication.class.getResource(viewPath)), 800, 600));
            this.primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(APIApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Start main view and charge XML file
     *
     * @param mainStage main stage
     */
    @Override
    public void start(Stage mainStage) {
        // Get file path
        File file = getDataDirectoryPath();
        
        // Get data if file found
        if (file != null) {
            loadDataDirectory(file);
        }
        
        // Open main view
        openView("view/MainView.fxml", "API Application");
    }

    /**
     * Hide Delete Environment View
     */
    public void hideCurrentView() {
        //Hide Delete current DEnvironment view
        this.primaryStage.getScene().getWindow().hide();
    }
    /**
     * Main launch application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Launch application
        Application.launch(APIApplication.class, (java.lang.String[]) null);
    }

}
