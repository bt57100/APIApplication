/*
 * Faiveley Transport License
 */
package org.faiveley;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.faiveley.controller.AddEnvironmentViewController;
import org.faiveley.controller.CRUDEnvironmentViewController;
import org.faiveley.controller.MainViewController;
import org.faiveley.controller.RootLayoutController;
import org.faiveley.model.Environment;
import org.faiveley.model.EnvironmentWrapper;

/**
 * Main class contains main(String[] args)
 *
 * @author 813308
 */
public class APIApplication extends Application {

    // Primary stage
    private Stage primaryStage;

    /**
     * Get Preferences settings
     */
    public Preferences prefs = Preferences.userNodeForPackage(APIApplication.class);

    // Environment list
    private final ObservableList<Environment> listEnvironment = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public APIApplication() {
        this.primaryStage = new Stage();
    }

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
     * Get file from preference file path
     *
     * @return file of preference file path
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
     * Set file path in preference
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
     * @param file File to save data to
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
     * Open main view
     */
    public void openMainView() {
        try {
            // Root layout view
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("API Application");
            FXMLLoader loaderRoot = new FXMLLoader();
            loaderRoot.setLocation(APIApplication.class.getResource("view/RootLayout.fxml"));
            BorderPane rootLayout = loaderRoot.load();
            this.primaryStage.setScene(new Scene(rootLayout, 750, 500));

            // Set root layout controller
            RootLayoutController rootController = loaderRoot.getController();
            rootController.setMainApp(this);

            // Main view
            FXMLLoader loaderMain = new FXMLLoader();
            loaderMain.setLocation(APIApplication.class.getResource("view/MainView.fxml"));
            AnchorPane MainView = loaderMain.load();

            // Set main view controller
            MainViewController mainController = loaderMain.getController();
            mainController.setMainApp(this);

            // Add main view to root layout view
            rootLayout.setCenter(MainView);

            // Display view
            this.primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(APIApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close current view and open environment manager view
     */
    public void openCRUDEnvironmentView() {
        try {
            // Close current view
            this.primaryStage.close();

            // Open environment manager view
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Environment Manager");
            FXMLLoader loaderCRUD = new FXMLLoader();
            loaderCRUD.setLocation(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));
            VBox rootLayout = loaderCRUD.load();
            this.primaryStage.setScene(new Scene(rootLayout, 750, 500));

            // Set environment manager view controller
            CRUDEnvironmentViewController CRUDEnvironmentController = loaderCRUD.getController();
            CRUDEnvironmentController.setMainApp(this);

            // Display view
            this.primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(APIApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close current view and open add environment view
     */
    public void openAddEnvironmentView() {
        try {
            // Close current view
            this.primaryStage.close();

            // Open add environment view
            this.primaryStage = new Stage();
            this.primaryStage.setTitle("Update Environment");
            FXMLLoader loaderCRUD = new FXMLLoader();
            loaderCRUD.setLocation(APIApplication.class.getResource("view/AddEnvironmentView.fxml"));
            GridPane rootLayout = loaderCRUD.load();
            this.primaryStage.setScene(new Scene(rootLayout, 400, 400));

            // Set add environment view controller
            AddEnvironmentViewController addEnvironmentController = loaderCRUD.getController();
            addEnvironmentController.setMainApp(this);

            // Display view
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
        this.openMainView();
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
