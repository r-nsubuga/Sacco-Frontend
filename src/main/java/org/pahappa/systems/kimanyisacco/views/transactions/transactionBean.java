package org.pahappa.systems.kimanyisacco.views.transactions;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

//import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.models.Register;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionImpl;

@ManagedBean(name="transbean")
@ViewScoped
public class transactionBean{
    private Transactions transactions;
    private Account account;   
    private Register currentUser;

    TransactionImpl transact = new TransactionImpl();

    private List<Transactions> transactions_list; 

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }

    public transactionBean() {
        
        this.transactions = new Transactions();
        
    }

    public Register getCurrentUser() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (Register) externalContext.getSessionMap().get("LoggedUser");
    }
     public void setCurrentUser(Register currentUser) {
        this.currentUser = currentUser;
    }


    public void doDeposit(){
        Register user = getCurrentUser();
       
        this.account = user.getAccountNumber(); 
        
        this.transactions.setAccNumber(this.account);
        transact.makeDeposit(this.transactions);

        this.account.setBalance(this.account.getBalance() + this.transactions.getAmount());
        transact.addToAccount(this.account);

        FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_INFO,"Deposit successful","Success"));
        
     }
    
    public void doWithdraw(){
        Register user = getCurrentUser();
        System.out.println(user.getUsername());
        System.out.println(user.getAccountNumber().getAccountNumber());

        this.account = user.getAccountNumber(); 
        System.out.println(account.getAccountNumber());
        this.transactions.setAccNumber(this.account);

        System.out.println(this.account.getBalance());
        System.out.println(this.transactions.getAmount());
        if(this.transactions.getAmount() >= 0){
        if(this.transactions.getAmount() < (this.account.getBalance()-2)){
            transact.makeWithdraw(this.transactions); 
        
            FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Request sent, Wait for approval email", "Success"));
        }
        else {
           FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient balance, withdraw smaller amount","Failed")); 
        }
    }else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect value, put correct amount","Failed")); 
    }
        
    }
    
    public List<Transactions> getTransactions_list() {
        Register user = getCurrentUser();
        this.account = user.getAccountNumber();
        transactions_list = transact.getList(this.account);
        System.out.println(transactions_list);
        return transactions_list;
    }

    public void setTransactions_list(List<Transactions> transactions_list) {
        this.transactions_list = transactions_list;
    }


    public double getUserInfo(){
        Register user2 = getCurrentUser();
        this.account = user2.getAccountNumber();
        double user_balance = this.account.getBalance();
        return user_balance;

    }

    public double getUserDeposit(){
        Register user3 = getCurrentUser();
        this.account = user3.getAccountNumber();
        
        transactions_list = transact.getList(this.account);
        int sum = 0;
        for(Transactions item:transactions_list){
            if(item.getTransactionType().equals("DEPOSIT")){
                sum += item.getAmount();
            }
        }
        return sum;
        
    }

    public double getUserWithdraw(){
        Register user3 = getCurrentUser();
        this.account = user3.getAccountNumber();
        
        transactions_list = transact.getList(this.account);
        int sum = 0;
        for(Transactions item:transactions_list){
            if(item.getTransactionType().equals("WITHDRAW") && item.getStatus() == 1){
                sum += item.getAmount();
            }
        }
        return sum;
        
    }

    public String getFName(){
         Register user4 = getCurrentUser();
         String fname = user4.getFirstName();
         return fname;
    }
    public String getLName(){
         Register user4 = getCurrentUser();
         String lname = user4.getLastName();
         return lname;
    }
    public String getGen(){
         Register user4 = getCurrentUser();
         String gender = user4.getGender();
         return gender;
    }
    public String getANumber(){
         Register user4 = getCurrentUser();
         String ac_number  = user4.getAccountNumber().getAccountNumber();
         return ac_number;
    }
    
}