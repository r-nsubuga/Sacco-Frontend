package org.pahappa.systems.kimanyisacco.models;

import java.util.Date;

import javax.persistence.*;

import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Account;

@Entity
@Table(name="transactions")
public class Transaction {
    private int transactionID;
    private double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private Account account;
    private Date transactionDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    @Column(name="amount",nullable=false)
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type", nullable=false)
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_status")
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Column(name="transaction_date" )
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @ManyToOne
    @JoinColumn(name="account_fk", referencedColumnName = "account_number")
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    @Override
    public String toString() {
        return "Transactions [transID=" + transactionID + ", amount=" + amount + ", transactionType=" + transactionType
                + ", transactionStatus=" + transactionStatus + ", transactionDate=" + transactionDate + ", account=" + account + "]";
    }

    

}
