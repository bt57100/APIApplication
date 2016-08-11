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
     * Constructor
     */
    public AddEnvironmentViewController() {
    }

    /**
     * Set mainApp and prompt texts
     *
     * @param app API application to set
     */
    public void setMainApp(APIApplication app) {
        // Set main app
        this.mainApp = app;

        // Prompt texts
        this.newName.setPromptText("Enter environment ID");
        this.newHost.setPromptText("Enter IP address");
        this.newPort.setPromptText("Enter port");
        this.newLogin.setPromptText("Enter login");
        this.newPassword.setPromptText("Enter password");
    }

    /**
     * Save change Close CU environment view and open CRUD environment view
     */
    @FXML
    public void CUEnvironmentCompleted() {

        // Add new
        if ((!"".equals(this.newName.getText())) && (!"".equals(this.newPort.getText()))) {
            this.mainApp.getListEnvironnement().add(new Environment(
                    this.newName.getText(),
                    this.newHost.getText(),
                    Integer.parseInt(this.newPort.getText()),
                    this.newLogin.getText(),
                    this.newPassword.getText())
            );
        }

        // Change view
        this.mainApp.openCRUDEnvironmentView();

    }

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void CUEnvironmentCancel() {
        // Change view
        this.mainApp.openCRUDEnvironmentView();
    }

}
