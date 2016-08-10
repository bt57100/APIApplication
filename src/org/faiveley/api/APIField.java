package org.faiveley.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Model API information class
 *
 * @author 813308
 */
public class APIField {

    /**
     * API information
     */
    private String name;
    private String description;
    private String length;
    private String type;
    private String mandatory;
    private String fromPosition;
    private String toPostion;

    /**
     * Set name
     *
     * @param name API code
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description
     *
     * @param description API description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set length
     *
     * @param length API length
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * Set type
     *
     * @param type API type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set mandatory
     *
     * @param mandatory API mandatory
     */
    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Set fromPosition
     *
     * @param fromPosition start position
     */
    public void setFromPosition(String fromPosition) {
        this.fromPosition = fromPosition;
    }

    /**
     * Set toPostion
     *
     * @param toPostion end position
     */
    public void setToPostion(String toPostion) {
        this.toPostion = toPostion;
    }

    /**
     * Get name
     *
     * @return API name
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
     * @return API description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get length
     *
     * @return API length
     */
    public String getLength() {
        return this.length;
    }

    /**
     * Get type
     *
     * @return API type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get mandatory 1 = Yes 0 = No
     *
     * @return API mandatory  1 = Yes 0 = No
     */
    public String getMandatory() {
        return this.mandatory.equals("1") ? "Yes" : "No";
    }

    /**
     * Get fromPosition
     *
     * @return start position
     */
    public String getFromPosition() {
        return this.fromPosition;
    }

    /**
     * Get toPostion
     *
     * @return end position
     */
    public String getToPostion() {
        return this.toPostion;
    }

    /**
     * Get headers columns title
     *
     * @return column title
     */
    public static List<String> getHeaders() {
        List<String> headers = new ArrayList<>();

        headers.add("Field Name");
        headers.add("Field Description");
        headers.add("Length");
        headers.add("Type");
        headers.add("From Position");
        headers.add("To Position");
        headers.add("Mandatory");

        return headers;
    }
}

/* Location:           F:\JavaApplication.jar
 * Qualified Name:     javaapplication.com.lttd.beans.APIField
 * JD-Core Version:    0.6.2
 */
