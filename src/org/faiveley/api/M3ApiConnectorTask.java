package org.faiveley.api;

import MvxAPI.MvxSockJ;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

/**
 * Control and retrieve information from M3 class
 *
 * @author 813308
 */
public class M3ApiConnectorTask extends Task<String> {

    /**
     * Control variables
     */
    // Authorized connection to M3
    private static MvxSockJ MRS001Socket;
    // Connection information
    private final M3ApiConnectorModel connector;
    // Connection enable
    private boolean connected;
    // API name
    private static String APIName;
    // Error message
    private String error;

    /**
     * Constructor, connection to M3 API
     *
     * @param m3ApiConnector M3 connection information
     */
    public M3ApiConnectorTask(M3ApiConnectorModel m3ApiConnector) {
        this.connector = m3ApiConnector;
        APIName = "";
        // Default not connected
        this.connected = false;
    }

    /**
     * Constructor with API name
     *
     * @param m3ApiConnector M3 connection information
     * @param apiName API code
     */
    public M3ApiConnectorTask(M3ApiConnectorModel m3ApiConnector, String apiName) {
        this.connector = m3ApiConnector;
        APIName = apiName;
        // Default not connected
        this.connected = false;
    }

    /**
     * Get error message
     *
     * @return error message
     */
    public String getError() {
        return error;
    }

    /**
     * Set API name
     *
     * @param APIName API selected
     */
    public void setApiName(String APIName) {
        M3ApiConnectorTask.APIName = APIName;
    }

    /**
     * Create a new API
     *
     * @param loadOutputs boolean to know if output should be loaded
     * @return API
     */
    public M3Api buildApi(boolean loadOutputs) {
        M3Api api = new M3Api();
        api.setName(APIName);
        api.setTransactions(LstTransactions(loadOutputs));
        return api;
    }

    /**
     * Connect to M3 MRS001MI
     */
    private void CreateMRS001Instance() {
        int n;
        // Connect
        if ((!this.connector.getHost().equals("")) && (this.connector.getPort() != 0)) {
            MRS001Socket = new MvxSockJ(this.connector.getHost(), this.connector.getPort(), "", 0, "");
            this.connected = true;
        }
        // Test connection
        if ((n = MRS001Socket.mvxInit("Host", this.connector.getUser(), this.connector.getPassword(), "MRS001MI")) > 0) {
            this.error = "CreateMRS001Instance - mvxInit() returned " + n + "\n" + MRS001Socket.mvxGetLastError();
            this.connected = false;
        }
    }

    /**
     * List all API information in MRS001MI
     *
     * @param transaction transaction name
     * @param inOut M3 properties to be set
     * @return
     */
    private List<APIField> lstFields(String transaction, String inOut) {
        int n;

        // API list variable
        List<APIField> fields = new ArrayList<>();

        // Set M3 API properties
        MRS001Socket.mvxSetField("MINM", APIName);
        MRS001Socket.mvxSetField("TRNM", transaction);
        MRS001Socket.mvxSetField("TRTP", inOut);

        /* Try to access to list fields*/
        // If cannot access, print error
        if ((n = MRS001Socket.mvxAccess("LstFields")) > 0) {
            System.out.println("mvxAccess() returned " + n + " Errortext: " + MRS001Socket.mvxGetLastError());
        } else {
            // else if allowed, get M3 API information
            while (MRS001Socket.mvxMore()) {
                APIField field = new APIField();
                field.setName(MRS001Socket.mvxGetField("FLNM"));
                field.setDescription(MRS001Socket.mvxGetField("FLDS"));
                field.setLength(MRS001Socket.mvxGetField("LENG"));
                field.setType(MRS001Socket.mvxGetField("TYPE"));
                field.setMandatory(MRS001Socket.mvxGetField("MAND"));
                field.setFromPosition(MRS001Socket.mvxGetField("FRPO"));
                field.setToPostion(MRS001Socket.mvxGetField("TOPO"));

                MRS001Socket.mvxAccess("LstFields");
                fields.add(field);
            }
        }

        // Return fields 
        return fields;
    }

    /**
     * List all Programs in API
     *
     * @return API list
     */
    public List<M3Api> LstPrograms() {
        int n;

        // API list variable
        List<M3Api> apis = new ArrayList<>();

        /* Connection to API */
        // Set Field
        MRS001Socket.mvxSetField("MRCD", "0");
        // If cannot access, print error
        if ((n = MRS001Socket.mvxAccess("SetLstMaxRec")) > 0) {
            System.out.println("LstPrograms - mvxAccess() returned " + n + " Errortext: " + MRS001Socket.mvxGetLastError());
        }

        // If cannot access, print error
        if ((n = MRS001Socket.mvxAccess("LstPrograms")) > 0) {
            System.out.println("LstPrograms - mvxAccess() returned " + n + " Errortext: " + MRS001Socket.mvxGetLastError());
        } else {
            // else if allowed, get program information
            while (MRS001Socket.mvxMore()) {
                M3Api api = new M3Api();
                api.setName(MRS001Socket.mvxGetField("MINM"));
                api.setDescription(MRS001Socket.mvxGetField("MIDS"));

                MRS001Socket.mvxAccess("LstPrograms");
                apis.add(api);
            }
        }

        // Return APIs
        return apis;
    }

    /**
     * List all Transactions in Programs
     *
     * @param loadOutputs boolean to know if output should be loaded
     * @return transaction list for the current API
     */
    public List<APITransaction> LstTransactions(boolean loadOutputs) {
        int n;

        // API transaction list variable
        List<APITransaction> transactions = new ArrayList<>();

        // Set API name
        MRS001Socket.mvxSetField("MINM", APIName);
        // If cannot access, print error
        if ((n = MRS001Socket.mvxAccess("LstTransactions")) > 0) {
            System.out.println("mvxAccess() returned " + n + " Errortext: " + MRS001Socket.mvxGetLastError());
        } else {
            // else if allowed, get transaction information
            while (MRS001Socket.mvxMore()) {
                APITransaction transaction = new APITransaction();
                transaction.setName(MRS001Socket.mvxGetField("TRNM"));
                transaction.setDescription(MRS001Socket.mvxGetField("TRDS"));
                transaction.setInputPrefix(MRS001Socket.mvxGetField("PRFI"));
                transaction.setOutputPrefix(MRS001Socket.mvxGetField("PRFO"));
                transaction.setInputFields(lstFields(transaction.getName(), "I"));
                if (loadOutputs) {
                    transaction.setOuputFields(lstFields(transaction.getName(), "O"));
                }

                MRS001Socket.mvxAccess("LstTransactions");
                transactions.add(transaction);
            }
        }

        // Return API transactions
        return transactions;
    }

    /**
     * Disconnect from M3
     */
    public void disconnectFromM3() {
        MRS001Socket.mvxClose();
        this.connected = false;
    }

    /**
     * Is connected
     *
     * @return if connected
     */
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    protected String call() throws Exception {
        updateMessage("Connecting to M3...");
        CreateMRS001Instance();
        if (this.connected == true) {
            updateMessage("Connection succeed.");

            return "SUCCEEDED";
        } else {
            updateMessage("Connection failed.");
            return "FAILED";
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessage("Connection succeed.");
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Connection cancelled.");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Connection failed.");
    }
}
