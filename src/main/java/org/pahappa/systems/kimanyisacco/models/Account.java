package org.pahappa.systems.kimanyisacco.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.pahappa.systems.kimanyisacco.models.Member;

@Entity
@Table(name="accounts")
public class Account {

    private  String accountNumber;
    private double balance;
    private Member owner;

   
    @Id
    @Column(name="account_number", nullable=false)
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name="balance", columnDefinition = "integer default 0")
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @OneToOne(mappedBy = "account")
    public Member getOwner() {
        return owner;
    }
    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public Account(){}

    public Account(String accNumber){
        this.accountNumber = accNumber;

    }
    
}
