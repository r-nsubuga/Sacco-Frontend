package org.pahappa.systems.kimanyisacco.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.models.Register;

@Entity
@Table(name="accounts")
public class Account {

    private  String accountNumber;
    private Set<Transactions> transactions; 
    private int balance;
    private Register owner;

   
    @Id
    @Column(name="account_number", nullable=false)
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // @OneToMany(mappedBy = "accNumber")
    // public Set<Transactions> getTransactions() {
    //     return transactions;
    // }
    // public void setTransactions(Set<Transactions> transactions) {
    //     this.transactions = transactions;
    // }

    @Column(name="balance", columnDefinition = "integer default 0")
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @OneToOne(mappedBy = "accountNumber")
    public Register getOwner() {
        return owner;
    }
    public void setOwner(Register owner) {
        this.owner = owner;
    }

    public Account(){}

    public Account(String accNumber){
        this.accountNumber = accNumber;

    }
    
}
