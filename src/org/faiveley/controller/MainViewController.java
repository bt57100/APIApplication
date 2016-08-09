/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;
import org.faiveley.xml.ReadXMLFile;

/**
 * Controller main FXML
 *
 * @author 813308
 */
public class MainViewController implements Initializable {

    // Position of XML file
    public String filePath = System.getProperty("user.dir") + "\\src\\org\\faiveley\\environments.xml";
    
    // Selected Environment
    private Environment currentEnvironment;

    // Pane
    @FXML
    private AnchorPane mainView;
    @FXML
    private HBox connectionPane;
    @FXML
    private HBox apisTransactionsPane;
    @FXML
    private StackPane entriesPane;
    @FXML
    private StackPane logsPane;

    // Environment
    @FXML
    private ChoiceBox<String> environmentList;
    @FXML
    private Button connectButtonId;
    @FXML
    private Button changeEnvironmentButton;

    // Log area
    @FXML
    private TextField logText;

    /**
     * Initialize main view parameters
     *
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getEnvironmentFromXML();
        this.logText = new TextField("");
        System.out.println(filePath);
    }

    /**
     * Get environment from XLS file
     */
    private void getEnvironmentFromXML() {
        ReadXMLFile Rxml = new ReadXMLFile();
        List<Environment> environments = new ArrayList<>();

        // Read from file
        Rxml.readXMLFile(this.filePath, environments);

        // Add environment to choice box
        environments.stream().forEach((environment) -> {
            this.environmentList.getItems().add(environment.getName());
        });

        // Default setting choice box 
        this.environmentList.getSelectionModel().selectFirst();
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
        try {
            VBox CRUDEnvironmentView = (VBox) FXMLLoader.load(APIApplication.class.getResource("view/CRUDEnvironmentView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Environment Manager");
            stage.setScene(new Scene(CRUDEnvironmentView, 600, 450));
            stage.show();

            hideMainView();
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hide Main View
     */
    public void hideMainView() {
        this.changeEnvironmentButton.getScene().getWindow().hide();
    }

}
