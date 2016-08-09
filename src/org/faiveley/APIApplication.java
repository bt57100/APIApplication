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
    private final ObservableList<Environment> listEnvironnement = FXCollections.observableArrayList();

    
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public ObservableList<Environment> getListEnvironnement() {
        return listEnvironnement;
    }
    
    /**
     * Start main view
     *
     * @param mainStage main stage
     */
    @Override
    public void start(Stage mainStage) {
        File file = getDataDirectoryPath();
        if (file != null) {
            loadDataDirectory(file);
        }
        try {

            openView("view/MainView.fxml", "API Application");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getDataDirectoryPath() {
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setRegistryFilePath(File file) {
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
    }

    public void loadDataDirectory(File directory) {
        JAXBContext context;
        Unmarshaller um;
        try {
            if (directory.exists() && directory.isDirectory()) {
                File[] listFile = directory.listFiles();
                for (File f : listFile) {
                    if (f.getName().startsWith("environment")) {
                        context = JAXBContext.newInstance(EnvironmentWrapper.class);
                        um = context.createUnmarshaller();
                        EnvironmentWrapper wrapper = (EnvironmentWrapper) um.unmarshal(f);
                        listEnvironnement.clear();
                        listEnvironnement.addAll(wrapper.getEnvironments());
                    }
                }
            }
            setRegistryFilePath(directory);
        } catch (Exception e) {
        }
    }

    public void saveDataToFile(File file) {
        try {
            saveEnvironment(file);
            setRegistryFilePath(file);
        } catch (Exception e) {
        }
    }

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
     * Hide Delete Environment View
     */
    public void hideCurrentView() {
        //Hide Delete current DEnvironment view
        this.primaryStage.getScene().getWindow().hide();
    }

    private void saveEnvironment(File file) throws PropertyException, JAXBException {
        if (listEnvironnement.size() > 0) {
            JAXBContext context = JAXBContext.newInstance(EnvironmentWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            EnvironmentWrapper wrapper = new EnvironmentWrapper();
            wrapper.setEnvironments(listEnvironnement);
            File fileData = new File(file.getAbsoluteFile() + System.getProperty("file.separator") + "environment.xml");
            m.marshal(wrapper, fileData);
        }
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
