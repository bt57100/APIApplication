/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.faiveley.APIApplication;
import org.faiveley.api.M3ApiConnectorTask;
import org.faiveley.api.APITransaction;
import org.faiveley.api.M3Api;
import org.faiveley.api.M3ApiConnectorModel;
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
    Service<String> serviceM3Connection;
    private M3ApiConnectorModel connector;
    private M3ApiConnectorTask apiConnector;

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
    private TextArea logText;
    @FXML
    private HBox apisTransactionsPane;
    // Connection Disconnect Cancel button
    @FXML
    private Button connectButton;

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

        // Hide API bar
        this.apisTransactionsPane.setVisible(false);
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
        if (!this.environmentList.getSelectionModel().isEmpty()) {
            for (int i = 0; i < this.environments.size(); i++) {
                if (this.environmentList.getSelectionModel().getSelectedItem().equals(this.environments.get(i).getName())) {
                    this.selectedEnvironment = this.environments.get(i);
                }
            }
        }
    }

    /**
     * Set selected API
     */
    public void setSelectedApi() {
        if (!this.apiList.getSelectionModel().isEmpty()) {
            for (int i = 0; i < this.apis.size(); i++) {
                if (this.apiList.getSelectionModel().getSelectedItem().equals(this.apis.get(i).getName())) {
                    this.selectedApi = this.apis.get(i);
                    this.apiConnector.setApiName(this.selectedApi.getName());
                }
            }
        }
    }

    /**
     * Set selected transaction
     */
    public void setSelectedTransaction() {
        if (!this.transactionList.getSelectionModel().isEmpty()) {
            for (int i = 0; i < this.transactions.size(); i++) {
                if (this.transactionList.getSelectionModel().getSelectedItem().equals(this.transactions.get(i).getName())) {
                    this.selectedTransaction = this.transactions.get(i);
                }
            }
        }
    }

    /**
     * Set API choice box
     */
    public void setApiChoiceBox() {
        // Get APIs from M3 database
        this.apis.addAll(this.apiConnector.LstPrograms());

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
        this.transactions.addAll(this.apiConnector.LstTransactions(true));

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
        if (!this.environmentList.getSelectionModel().isEmpty()) {
            switch (connectButton.getText()) {
                /* Case button "Connect" */
                case "Connect":
                    //Initialization
                    initializeConnectionToM3();

                    // If task failed
                    serviceM3Connection.setOnFailed((WorkerStateEvent event) -> {
                        // Change connection button
                        connectButton.setText("Connect");
                        // Unbind and show in log text
                        MainViewController.this.logText.textProperty().unbind();
                        MainViewController.this.logText.appendText("Server may not be available. Try again later.\n");
                    });

                    // If task succeed
                    serviceM3Connection.setOnSucceeded((WorkerStateEvent event) -> {
                        // If connection succeed
                        if (MainViewController.this.apiConnector.isConnected() == true) {
                            // Show API bar
                            this.apisTransactionsPane.setVisible(true);
                            // Set APIs
                            setApiChoiceBox();
                            // Unbind and show in log text
                            MainViewController.this.logText.textProperty().unbind();
                            MainViewController.this.logText.appendText(MainViewController.this.selectedApi.getName() + " selected \n");
                            // Set transactions
                            setTransactionChoiceBox();

                            /**
                             * TO DO what append when transaction selected
                             */
                            
                            // Change connection button
                            connectButton.setText("Disconnect");
                        } else {
                            // Change connection button
                            connectButton.setText("Connect");
                            // Unbind and show error in log text
                            MainViewController.this.logText.textProperty().unbind();
                            MainViewController.this.logText.appendText("\n" + MainViewController.this.apiConnector.getError());
                        }
                    });
                    break;

                /* Case button "Cancel" */
                case "Cancel":
                    // Cancel service
                    serviceM3Connection.cancel();
                    // Unbind and show in log text
                    MainViewController.this.logText.textProperty().unbind();
                    // Change connection button
                    connectButton.setText("Connect");
                    break;

                /* Case button "Disconnect" */
                case "Disconnect":
                    // Disconnect from M3
                    this.apiConnector.disconnectFromM3();
                    // Change connection button
                    connectButton.setText("Connect");
                    // Unbind and show in log text
                    MainViewController.this.logText.textProperty().unbind();
                    break;

                default:
                    break;
            }
            // Start service
            serviceM3Connection.restart();
        }
    }

    /**
     * Initialize connection variable and service
     */
    public void initializeConnectionToM3() {
        // Reset log text
        this.logText.setText("");
        // Change connection button
        connectButton.setText("Cancel");
        // Get selected environment
        setSelectedEnvironment();
        // Connector
        this.connector = new M3ApiConnectorModel(
                this.selectedEnvironment.getHost(),
                this.selectedEnvironment.getPort(),
                this.selectedEnvironment.getLogin(),
                this.selectedEnvironment.getPassword(),
                true);
        // Set M3 API connector
        this.apiConnector = new M3ApiConnectorTask(connector);
        // Service follow task connection
        this.serviceM3Connection = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return apiConnector;
            }
        };
        // Bind logs to service message
        this.logText.textProperty().bind(serviceM3Connection.messageProperty());
    }
}
