/*
 * Faiveley Transport License
 */
package org.faiveley.model;

/**
 * Environment class contains name, host, port, login, password
 * 
 * @author 813308
 */
public class Environment extends AbstractCRUD {

    // Environment name
    private String name;
    
    // Serveur information
    private String host;
    private int port;
    
    // User information
    private String login;
    private String password;

    /**
     * Constructor without parameter
     */
    public Environment() {
        this.name = "";
        this.host = "";
        this.port = 0;
        this.login = "";
        this.password = "";
    }
    
    /**
     * Constructor with parameters
     *
     * @param name environment ID
     * @param host host IP address
     * @param port port
     * @param login login
     * @param password password
     */
    public Environment(String name, String host, int port, String login, String password) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    /**
     * Get environment ID
     *
     * @return environment ID
     */
    public String getName() {
        return name;
    }

    /**
     * Set environment ID
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get host IP address
     *
     * @return host IP address
     */
    public String getHost() {
        return host;
    }

    /**
     * Set host IP address
     *
     * @param host host IP address
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Get port
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Set Port
     *
     * @param port port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get login
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set login
     *
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Create a new environment
     *
     */
    @Override
    public void create() {
        // TO DO
    }

    /**
     * Read the environment
     */
    @Override
    public void read() {
    }
    
    /**
     * Update environment
     */
    @Override
    public void update() {
    }
    
    /**
     * Delete environment
     */
    @Override
    public void delete() {
        this.host = "";
        this.port = 0;
        this.login = "";
        this.password = "";
    }
}
