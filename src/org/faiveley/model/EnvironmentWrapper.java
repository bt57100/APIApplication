/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.faiveley.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JCHAUT
 */
@XmlRootElement(name = "environments")
public class EnvironmentWrapper {

    private List<Environment> environments;

    /**
     * Get environment wrapper
     * 
     * @return  environment list
     */
    @XmlElement(name = "environment")
    public List<Environment> getEnvironments() {
        return this.environments;
    }

    /**
     * Set environment wrapper
     * 
     * @param environments environment wrapper to set
     */
    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }
}
