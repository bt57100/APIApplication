/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;
import org.faiveley.xml.UpdateXMLFile;

/**
 * Controller Delete Environment view
 *
 * @author 813308
 */
public class DEnvironmentViewController {

    // Position of XML file
    public String filePath = System.getProperty("user.dir") + "\\src\\org\\faiveley\\environments.xml";

    // Delete environment
    @FXML
    private TextField environmentNameToDelete;
    @FXML
    private Button okDeleteButton;

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void DEnvironmentCompleted() {
        // XML variables
        UpdateXMLFile xml = new UpdateXMLFile();

        try {
            // If at least environment name
            if (!this.environmentNameToDelete.getText().equals("")) {
                // Update XML
                xml.updateXMLFile(this.filePath, false, this.environmentNameToDelete.getText(), new Environment());
            }

            // CRUDEnvironment view
            VBox CRUDEnvironmentView = (VBox) FXMLLoader.load(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Environment Manager");
            stage.setScene(new Scene(CRUDEnvironmentView, 600, 450));
            stage.show();

            //Hide current DEnvironment view
            hideDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void DEnvironmentCancel() {
        try {
            // CRUDEnvironment view
            VBox CRUDEnvironmentView = (VBox) FXMLLoader.load(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Environment Manager");
            stage.setScene(new Scene(CRUDEnvironmentView, 600, 450));
            stage.show();

            //Hide Delete current DEnvironment view
            hideDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hide Delete Environment View
     */
    public void hideDEnvironmentView() {
        //Hide Delete current DEnvironment view
        this.okDeleteButton.getScene().getWindow().hide();
    }
}
