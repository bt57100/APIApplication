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
 * Controller Create Update Environment view
 *
 * @author 813308
 */
public class CUEnvironmentViewController {

    // Position of XML file
    public String filePath = System.getProperty("user.dir") + "\\src\\org\\faiveley\\environments.xml";

    // Create Update environment
    @FXML
    private TextField newName;
    @FXML
    private TextField newHost;
    @FXML
    private TextField newPort;
    @FXML
    private TextField newLogin;
    @FXML
    private TextField newPassword;
    @FXML
    private Button okAddButton;

    /**
     * Save change Close CU environment view and open CRUD environment view
     */
    @FXML
    public void CUEnvironmentCompleted() {
        // XML variables
        UpdateXMLFile xml = new UpdateXMLFile();

        try {
            // If at least environment name
            if (!this.newName.getText().equals("") && !this.newPort.getText().equals("")) {
                // Update XML
                xml.updateXMLFile(this.filePath, true, this.newName.getText(),
                        new Environment(this.newName.getText(),
                                this.newHost.getText(),
                                Integer.parseInt(this.newPort.getText()),
                                this.newLogin.getText(),
                                this.newPassword.getText()));
            }

            // CRUDEnvironment view
            VBox CRUDEnvironmentView = (VBox) FXMLLoader.load(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Environment Manager");
            stage.setScene(new Scene(CRUDEnvironmentView, 600, 450));
            stage.show();
            //Hide current CUEnvironment view
            hideCUEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void CUEnvironmentCancel() {
        try {
            // CRUDEnvironment view
            VBox CRUDEnvironmentView = (VBox) FXMLLoader.load(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Environment Manager");
            stage.setScene(new Scene(CRUDEnvironmentView, 600, 450));
            stage.show();

            //Hide current CUEnvironment view
            hideCUEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hide CU Environment View
     */
    public void hideCUEnvironmentView() {
        //Hide current CUEnvironment view
        this.okAddButton.getScene().getWindow().hide();
    }
}
