/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.faiveley.APIApplication;
import org.faiveley.api.APIApplicationControler;
import org.faiveley.api.APITransaction;
import org.faiveley.api.M3Api;
import org.faiveley.api.M3ApiConnector;
import org.faiveley.model.Environment;

/**
 * Controller main FXML
 *
 * @author 813308
 */
public class MainViewController implements Initializable {

    // Main application
    private APIApplication mainApp;

    // M3 connection
    APIApplicationControler apiApplication;

    // Environment
    private Environment selectedEnvironment;
    private final List<Environment> environments;
    @FXML
    private ChoiceBox<String> environmentList;

    // API
    private M3Api selectedApi;
    private final List<M3Api> apis;
    @FXML
    private ChoiceBox<String> apiList;

    // Transaction
    private APITransaction selectedTransaction;
    private final List<APITransaction> transactions;
    @FXML
    private ChoiceBox<String> transactionList;

    // Log area
    @FXML
    private TextField logText;

    // Connection buttons
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;

    /**
     * Constructor
     */
    public MainViewController() {
        this.environments = new ArrayList<>();
        this.apis = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    /**
     * Set mainApp
     *
     * @param app API application to set
     */
    public void setMainApp(APIApplication app) {
        // Set main application
        this.mainApp = app;

        // Set environment to choice box
        this.environments.addAll(this.mainApp.getListEnvironnement());
        this.environments.stream().forEach((environment) -> {
            this.environmentList.getItems().add(environment.getName());
        });
    }

    /**
     * Set main stage
     *
     * @param stage stage to set
     */
    public void setStage(Stage stage) {
        this.mainApp.setPrimaryStage(stage);
    }

    /**
     * Initialize main view parameters
     *
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Change to environment manager view
     */
    @FXML
    public void changeEnvironment() {
        // Change to environment manager view
        this.mainApp.openCRUDEnvironmentView();
    }

    /**
     * Set selected environment
     */
    public void setSelectedEnvironment() {
        for (int i = 0; i < this.environments.size(); i++) {
            if (this.environmentList.getSelectionModel().getSelectedItem().equals(this.environments.get(i).getName())) {
                this.selectedEnvironment = this.environments.get(i);
            }
        }
    }

    /**
     * Set selected API
     */
    public void setSelectedApi() {
        for (int i = 0; i < this.apis.size(); i++) {
            if (this.apiList.getSelectionModel().getSelectedItem().equals(this.apis.get(i).getName())) {
                this.selectedApi = this.apis.get(i);
                this.apiApplication.setApiName(this.selectedApi.getName());
            }
        }
    }

    /**
     * Set selected transaction
     */
    public void setSelectedTransaction() {
        for (int i = 0; i < this.transactions.size(); i++) {
            if (this.transactionList.getSelectionModel().getSelectedItem().equals(this.transactions.get(i).getName())) {
                this.selectedTransaction = this.transactions.get(i);
            }
        }
    }

    /**
     * Set API choice box
     */
    public void setApiChoiceBox() {
        // Get APIs from M3 database
        this.apis.addAll(this.apiApplication.LstPrograms());

        // Set API name in choice box
        this.apis.stream().forEach((currentApi) -> {
            this.apiList.getItems().add(currentApi.getName());
        });

        // Set selected API 
        setSelectedApi();
    }

    /**
     * Set transaction choice box
     */
    public void setTransactionChoiceBox() {
        // Get transactions from M3 database
        this.transactions.addAll(this.apiApplication.LstTransactions(true));

        // Set ransaction name in choice box
        this.transactions.stream().forEach((currentTransaction) -> {
            this.transactionList.getItems().add(currentTransaction.getName());
        });

        // Set selected API 
        setSelectedTransaction();
    }

    /**
     * Connect to M3
     */
    @FXML
    public void connectToM3() {
        // Get selected environment
        setSelectedEnvironment();

        // Connector
        M3ApiConnector connector = new M3ApiConnector(
                this.selectedEnvironment.getHost(),
                this.selectedEnvironment.getPort(),
                this.selectedEnvironment.getLogin(),
                this.selectedEnvironment.getPassword(),
                true);

        // Connect
        this.apiApplication = new APIApplicationControler(connector);

        // Disable connection button
        this.connectButton.setDisable(true);

        // Enable disconnection button
        this.disconnectButton.setDisable(false);

        setApiChoiceBox();
        setTransactionChoiceBox();
    }

    /**
     * Disconnect to M3
     */
    @FXML
    public void disconnectFromM3() {
        // Disconnect from M3
        this.apiApplication.disconnectFromM3();

        // Disable connection button
        this.disconnectButton.setDisable(true);

        // Enable connection button
        this.connectButton.setDisable(false);
    }

}
