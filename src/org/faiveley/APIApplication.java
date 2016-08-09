/*
 * Faiveley Transport License
 */
package org.faiveley;

import com.sun.javaws.Main;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

    public Preferences prefs = Preferences.userNodeForPackage(APIApplication.class);
    private final ObservableList<Environment> listEnvironnement = FXCollections.observableArrayList();

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
            // Charge main view
            AnchorPane MainView = (AnchorPane) FXMLLoader.load(APIApplication.class.getResource("view/MainView.fxml"));

            // Set up a scene
            Scene scene = new Scene(MainView, 700, 400);

            // Settings main stage
            mainStage.setTitle("API Application");
            mainStage.setScene(scene);
            mainStage.show();

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

    public ObservableList<Environment> getListEnvironnement() {
        return listEnvironnement;
    }

}
