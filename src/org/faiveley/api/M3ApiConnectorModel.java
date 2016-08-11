package org.faiveley.api;

/**
 * Model connection information for M3 API class
 *
 * @author 813308
 */
public class M3ApiConnectorModel {

    /**
     * Connection variables
     */
    private String host;
    private int port;
    private String user;
    private String password;
    private boolean loadOutputs;

    /**
     * Constructor
     *
     * @param host M3 API host
     * @param port M3 API port
     * @param user M3 API username
     * @param password M3 API password
     * @param loadOutputs M3 API if outputs have been loaded
     */
    public M3ApiConnectorModel(String host, int port, String user, String password, boolean loadOutputs) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.loadOutputs = loadOutputs;
    }

    /**
     * Set host
     *
     * @param host M3 API host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set port
     *
     * @param port M3 API port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set user
     *
     * @param user M3 API username
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Set password
     *
     * @param password M3 API password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set loadOutputs
     *
     * @param loadOutputs M3 API load outputs
     */
    public void setLoadOutputs(boolean loadOutputs) {
        this.loadOutputs = loadOutputs;
    }

    /**
     * Get host
     *
     * @return M3 API host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Get port
     *
     * @return M3 API port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Get user
     *
     * @return M3 API username
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Get password
     *
     * @return M3 API password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Get loadOutputs
     *
     * @return if M3 API has been loaded
     */
    public boolean isLoadOutputs() {
        return this.loadOutputs;
    }

}
