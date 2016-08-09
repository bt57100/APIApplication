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
 * Controller Create Update Environment view
 *
 * @author 813308
 */
public class UpdateEnvironmentViewController {

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
     * Save change Close CU environment view and open CRUD environment view
     */
    @FXML
    public void CUEnvironmentCompleted() {

        this.mainApp.getListEnvironnement().add(new Environment(
                this.newName.getText(),
                this.newHost.getText(),
                Integer.parseInt(this.newPort.getText()),
                this.newLogin.getText(),
                this.newPassword.getText())
        );

        this.mainApp.saveDataToFile(this.file);

        this.mainApp.openView("view/CRUDEnvironmentView.fxml", "Environment Manager");

    }

    /**
     * Close CU environment view and enable main view to be seen
     */
    @FXML
    public void CUEnvironmentCancel() {
        this.mainApp.openView("view/CRUDEnvironmentView.fxml", "Environment Manager");
    }

}
