/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;

/**
 * Controller main FXML
 *
 * @author 813308
 */
public class MainViewController implements Initializable {

    private APIApplication mainApp;

    // Selected Environment
    private Environment currentEnvironment;

    // Environment
    @FXML
    private ChoiceBox<Environment> environmentList;
    @FXML
    private Button connectButtonId;

    // Log area
    @FXML
    private TextField logText;

    /**
     * Set mainApp
     * 
     * @param app API application to set
     */
    public void setMainApp(APIApplication app) {
        this.mainApp = app;
    }
    
    /**
     * Initialize main view parameters
     *
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set all environment in choice box
        this.mainApp.getListEnvironnement().stream().forEach((environment) -> {
            this.environmentList.getItems().add(environment);
        });
    }

    /**
     * Connect to M3
     */
    @FXML
    public void connectToM3() {
        // TO DO
    }

    /**
     * Select environment
     */
    @FXML
    public void changeEnvironment() {
        // Change view
        this.mainApp.openView("view/CRUDEnvironmentView.fxml", "Environment Manager");
    }

}
