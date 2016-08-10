package org.faiveley.api;

import java.util.List;

/**
 * Model API transaction class
 *
 * @author 813308
 */
public class APITransaction {

    /**
     * API Transaction variables
     */
    private String name;
    private String description;
    private String inputPrefix;
    private String outputPrefix;
    private List<APIField> inputFields;
    private List<APIField> ouputFields;

    /**
     * Set name
     *
     * @param name Transaction name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description
     *
     * @param description Transaction description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set inputPrefix
     *
     * @param inputPrefix Transaction input prefix
     */
    public void setInputPrefix(String inputPrefix) {
        this.inputPrefix = inputPrefix;
    }

    /**
     * Set outputPrefix
     *
     * @param outputPrefix Transaction output prefix
     */
    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    /**
     * Set inputFields
     *
     * @param inputFields Transaction input field list
     */
    public void setInputFields(List<APIField> inputFields) {
        this.inputFields = inputFields;
    }

    /**
     * Set ouputFields
     *
     * @param ouputFields Transaction output field list
     */
    public void setOuputFields(List<APIField> ouputFields) {
        this.ouputFields = ouputFields;
    }

    /**
     * Get name
     *
     * @return Transaction name
     */
    public String getName() {
        if (null == this.name) {
            this.name = "";
        }
        return this.name;
    }

    /**
     * Get description
     *
     * @return Transaction description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get inputPrefix
     *
     * @return Transaction input prefix
     */
    public String getInputPrefix() {
        return this.inputPrefix;
    }

    /**
     * Get outputPrefix
     *
     * @return Transaction output prefix
     */
    public String getOutputPrefix() {
        return this.outputPrefix;
    }

    /**
     * Get inputFields
     *
     * @return Transaction input field list
     */
    public List<APIField> getInputFields() {
        return this.inputFields;
    }

    /**
     * Get ouputFields
     *
     * @return Transaction output field list
     */
    public List<APIField> getOuputFields() {
        return this.ouputFields;
    }

}

/* Location:           F:\JavaApplication.jar
 * Qualified Name:     javaapplication.com.lttd.beans.APITransaction
 * JD-Core Version:    0.6.2
 */
