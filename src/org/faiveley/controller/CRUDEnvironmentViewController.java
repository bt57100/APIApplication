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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;
import org.faiveley.xml.CreateXMLFile;
import org.faiveley.xml.ReadXMLFile;

/**
 * Controller Create Read Update Delete Environment view
 *
 * @author 813308
 */
public class CRUDEnvironmentViewController implements Initializable {

    // Position of XML file
    public String filePath = System.getProperty("user.dir") + "\\src\\org\\faiveley\\environments.xml";

    // Environment List
    private List<Environment> environmentList;

    // Manage environment
    @FXML
    private TableView<Environment> environmentTable;
    @FXML
    private TableColumn<Environment, String> nameCol;
    @FXML
    private TableColumn<Environment, String> hostCol;
    @FXML
    private TableColumn<Environment, Integer> portCol;
    @FXML
    private TableColumn<Environment, String> loginCol;
    @FXML
    private TableColumn<Environment, String> passwordCol;
    @FXML
    private Button okManageButton;

    /**
     * Initialize CRUDEnvironment view
     *
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Charge table view
        tableViewInit();
    }

    /**
     * Charge tableView from xml file
     */
    public void tableViewInit() {
        // Read from XML
        ReadXMLFile xml = new ReadXMLFile();
        List<Environment> envList = new ArrayList<>();
        xml.readXMLFile(this.filePath, envList);

        // Prepare data for tableView
        ObservableList<Environment> data = FXCollections.observableArrayList();
        envList.stream().forEach((env) -> {
            data.add(new Environment(env.getName(), env.getHost(), env.getPort(), env.getLogin(), env.getPassword()));
        });

        /* Set format of data */
        // Name
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.nameCol.setOnEditCommit((CellEditEvent<Environment, String> changedName) -> {
            ((Environment) changedName.getTableView().getItems().get(changedName.getTablePosition().getRow())).setName(changedName.getNewValue());
        });

        // Host
        this.hostCol.setCellValueFactory(new PropertyValueFactory<>("host"));
        this.hostCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.hostCol.setOnEditCommit((CellEditEvent<Environment, String> changedHost) -> {
            ((Environment) changedHost.getTableView().getItems().get(changedHost.getTablePosition().getRow())).setHost(changedHost.getNewValue());
        });

        // Port
        this.portCol.setCellValueFactory(new PropertyValueFactory<>("port"));
        this.portCol.setCellFactory(TextFieldTableCell.<Environment, Integer>forTableColumn(new IntegerStringConverter()));
        this.portCol.setOnEditCommit((CellEditEvent<Environment, Integer> changedPort) -> {
            ((Environment) changedPort.getTableView().getItems().get(changedPort.getTablePosition().getRow())).setPort(changedPort.getNewValue());
        });

        // Login
        this.loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        this.loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.loginCol.setOnEditCommit((CellEditEvent<Environment, String> changedLogin) -> {
            ((Environment) changedLogin.getTableView().getItems().get(changedLogin.getTablePosition().getRow())).setLogin(changedLogin.getNewValue());
        });

        // Password
        this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.passwordCol.setOnEditCommit((CellEditEvent<Environment, String> changedPassword) -> {
            ((Environment) changedPassword.getTableView().getItems().get(changedPassword.getTablePosition().getRow())).setPassword(changedPassword.getNewValue());
        });

        // Set to tableView
        this.environmentTable.setItems(data);
    }

    /**
     * Open Create environment pane
     */
    @FXML
    public void addEnvironmentPane() {
        try {
            // CUEnvironment view
            GridPane root = (GridPane) FXMLLoader.load(APIApplication.class.getResource("view/CUEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Add Environment");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            // Hide current CRUDEnvironment view
            hideCRUDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open Update environment pane
     */
    @FXML
    public void updateEnvironmentPane() {
        try {
            // CUEnvironment view
            GridPane root = (GridPane) FXMLLoader.load(APIApplication.class.getResource("view/CUEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Update Environment");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            // Hide current CRUDEnvironment view
            hideCRUDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open Delete environment pane
     */
    @FXML
    public void deleteEnvironmentPane() {
        try {
            // DEnvironment view
            HBox root = (HBox) FXMLLoader.load(APIApplication.class.getResource("view/DEnvironmentView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("Delete Environment");
            stage.setScene(new Scene(root, 450, 100));
            stage.show();

            // Hide current CRUDEnvironment view
            hideCRUDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close and update CRUD environment view and open main view
     */
    @FXML
    public void CRUDEnvironmentCompleted() {
        try {
            // Get environment from table view
            this.environmentList = new ArrayList<>();
            environmentTable.getItems().stream().forEach((environment) -> {
                this.environmentList.add(environment);
            });

            // Create new XML
            CreateXMLFile xml = new CreateXMLFile();
            xml.createXMLFile(this.filePath, this.environmentList);

            // Main view
            AnchorPane MainView = (AnchorPane) FXMLLoader.load(APIApplication.class.getResource("view/MainView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("API Application");
            stage.setScene(new Scene(MainView, 700, 400));
            stage.show();

            // Hide current CRUDEnvironment view
            hideCRUDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close CRUD environment view and open main view
     */
    @FXML
    public void CRUDEnvironmentCancel() {
        try {
            // Main view
            AnchorPane MainView = (AnchorPane) FXMLLoader.load(APIApplication.class.getResource("view/MainView.fxml"));

            // Set up stage
            Stage stage = new Stage();
            stage.setTitle("API Application");
            stage.setScene(new Scene(MainView, 700, 400));
            stage.show();

            // Hide current CRUDEnvironment view
            hideCRUDEnvironmentView();

        } catch (IOException ex) {
            Logger.getLogger(CRUDEnvironmentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hide CRUD Environment View
     */
    public void hideCRUDEnvironmentView() {
        // Hide current CRUDEnvironment view
        this.okManageButton.getScene().getWindow().hide();
    }

}
