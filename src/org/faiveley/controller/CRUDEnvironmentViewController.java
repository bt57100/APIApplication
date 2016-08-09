/*
 * Faiveley Transport License
 */
package org.faiveley.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.faiveley.APIApplication;
import org.faiveley.model.Environment;

/**
 * Controller Create Read Update Delete environment in CRUDEnvironmentView
 *
 * @author 813308
 */
public class CRUDEnvironmentViewController implements Initializable {

    private File file;
    private APIApplication mainApp;

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

    
    /**
     * Set mainApp and table view
     * 
     * @param app API application to set
     */
    public void setMainApp(APIApplication app) {
        // Set mainApp
        mainApp = app;
        
        // Set table view
        this.environmentTable.setItems(mainApp.getListEnvironnement());
    }
    
    /**
     * Initialize CRUDEnvironment view
     *
     * @param url url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

    /**
     * Open Update environment pane
     */
    @FXML
    public void updateEnvironmentPane() {
        this.mainApp.openView("view/CUEnvironmentView.fxml", "Update Environment");
    }

    /**
     * Delete selected environment
     */
    @FXML
    public void deleteSelectedEnvironment() {
        // Get selected environment
        List  selectedEnvironments = environmentTable.getSelectionModel().getSelectedItems();

        // Delete selected environment
        this.mainApp.getListEnvironnement().removeAll(selectedEnvironments);
        
        // Save to file
        this.mainApp.saveDataToFile(this.file);
    }

    /**
     * Close and update CRUD environment view and open main view
     */
    @FXML
    public void CRUDEnvironmentCompleted() {
        // Save to file
        this.mainApp.saveDataToFile(this.file);
        
        // Change view
        this.mainApp.openView("view/MainView.fxml", "API Application");
    }

    /**
     * Close CRUD environment view and open main view
     */
    @FXML
    public void CRUDEnvironmentCancel() {
        // Change view
        this.mainApp.openView("view/MainView.fxml", "API Application");
    }

}
