package org.faiveley.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.faiveley.APIApplication;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Jérémy Chaut
 */
public class RootLayoutController {

    private APIApplication mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp main application give a reference back
     */
    public void setMainApp(APIApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        //mainApp.clearData();
        //mainApp.setRegistryFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        DirectoryChooser fileChooser = new DirectoryChooser();
        // Show save file dialog
        File file = fileChooser.showDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadDataDirectory(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File directory = mainApp.getDataDirectoryPath();
        if (directory != null) {
            mainApp.saveDataToFile(directory);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        DirectoryChooser fileChooser = new DirectoryChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        //fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.saveDataToFile(file);
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /**
     * Display main view.
     */
    public void showMainView() {
    }

    /**
     * Opens customer overview.
     */
    @FXML
    public void showCustomerOverview() {
    }

    /**
     * Opens VPN access overview.
     */
    @FXML
    public void showVPNAccessOverview() {
    }

    /**
     * Opens user server overview.
     */
    @FXML
    public void showUserServerOverview() {
    }

    /**
     * Opens developer overview.
     */
    @FXML
    public void showDeveloperOverview() {
    }

    /**
     * Opens server type overview.
     */
    @FXML
    public void showTypeServerOverview() {
    }

    /**
     * Opens server overview.
     */
    @FXML
    public void showServerOverview() {
    }

    /**
     * Opens environment overview.
     */
    @FXML
    public void showEnvironmentOverview() {
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    }
}
