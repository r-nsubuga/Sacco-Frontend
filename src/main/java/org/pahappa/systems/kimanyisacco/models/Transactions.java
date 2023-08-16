package org.pahappa.systems.kimanyisacco.models;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.Table;
import org.pahappa.systems.kimanyisacco.models.Account;

@Entity
@Table(name="transaction_table")
public class Transactions {
    private int transID;
    private int amount;
    private String transactionType;
    private int status;
    private Account accNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getTransID() {
        return transID;
    }
    public void setTransID(int transID) {
        this.transID = transID;
    }

    @Column(name="amount",nullable=false)
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Column(name="transaction_type", nullable=false)
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name="transaction_status", columnDefinition = "integer default 0")
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name="account_fk", referencedColumnName = "account_number")
    public Account getAccNumber() {
        return accNumber;
    }
    public void setAccNumber(Account accNumber) {
        this.accNumber = accNumber;
    }
    @Override
    public String toString() {
        return "Transactions [transID=" + transID + ", amount=" + amount + ", transactionType=" + transactionType
                + ", status=" + status + ", accNumber=" + accNumber + "]";
    }

    

}