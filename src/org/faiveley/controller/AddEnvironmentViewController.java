/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;

/**
 * Controller : create and add environment in AddEnvironmentView
 *
 * @author 813308
 */
public class AddEnvironmentViewController {

    File file;
    APIApplication mainApp;

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

    /**
     * Set mainApp
     * 
     * @param app API application to set
     */
    public void setMainApp(APIApplication app) {
        this.mainApp = app;
    }
    
    /**
     * Save change Close CU environment view and open CRUD environment view
     */
    @FXML
    public void CUEnvironmentCompleted() {
        // Charge
        this.mainApp.loadDataDirectory(this.file);
        
        // Add new
        this.mainApp.getListEnvironnement().add(new Environment(
                this.newName.getText(),
                this.newHost.getText(),
                Integer.parseInt(this.newPort.getText()),
                this.newLogin.getText(),
                this.newPassword.getText())
        );

        // Save
        this.mainApp.saveDataToFile(this.file);

        // Change view
        this.mainApp.openView("view/CRUDEnvironmentView.fxml", "Environment Manager");

    }

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void CUEnvironmentCancel() {
        // Change view
        this.mainApp.openView("view/CRUDEnvironmentView.fxml", "Environment Manager");
    }

}
