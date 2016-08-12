package org.faiveley.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Model transactions with M3 class
 *
 * @author 813308
 */
public class M3Api {

    /**
     * M3 API variables
     */
    private String name;
    private String description;
    private List<APITransaction> transactions;
    private List<APITransaction> cachedTransactions;

    /**
     * Set name
     *
     * @param name M3 API name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set description
     *
     * @param description M3 API description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set transactions
     *
     * @param transactions M3 API transaction list
     */
    public void setTransactions(List<APITransaction> transactions) {
        this.transactions = transactions;
        setCachedTransactions(transactions);
    }

    /**
     * Set cachedTransactions
     *
     * @param transactions M3 API cached transaction list
     */
    private void setCachedTransactions(List<APITransaction> transactions) {
        this.cachedTransactions = transactions;
    }

    /**
     * Get name
     *
     * @return M3 API name
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
     * @return M3 API description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get transactions
     *
     * @return M3 API transaction list
     */
    public List<APITransaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Get cached transactions
     *
     * @return M3 API cached transaction list
     */
    public List<APITransaction> getCachedTransactions() {
        return this.cachedTransactions;
    }

    /**
     * Add transaction, reset if resetList = true
     *
     * @param transaction transaction to add to current transaction list
     * @param resetList true = reset current transaction list
     */
    public void addTransaction(APITransaction transaction, boolean resetList) {
        // Reset transactions to empty array
        if ((null == this.transactions) || (resetList)) {
            resetTransactions();
        }

        // Add new transaction
        this.transactions.add(transaction);
    }

    /**
     * Reset transactions to empty array
     */
    public void resetTransactions() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Back up transaction from cache
     */
    public void alimTransactionsFromCache() {
        this.transactions = this.cachedTransactions;
    }

}
