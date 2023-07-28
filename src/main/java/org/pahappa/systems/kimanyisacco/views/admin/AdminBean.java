package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.List;

import javax.faces.bean.ManagedBean;
import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Register;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionImpl;


@ManagedBean(name="adminBean")

public class AdminBean {
    private List<Register> members;
    private List<Transactions> transactions;
    
    UserServiceImpl userService = new UserServiceImpl();
    TransactionImpl transactionImpl = new TransactionImpl();

    public List<Register> getMembers() {
        members = userService.getUsersToVerify();
        return members;
    }
    public void setMembers(List<Register> members) {
        this.members = members;
    }

     public List<Transactions> getTransactions() {
        transactions = transactionImpl.getWithdrawsToApprove();
        return transactions;
    }
    
    List<Transactions> allTransactions = transactionImpl.getAllTransactions();

    public void setAllTransactions(List<Transactions> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public void verifyMember(int id){
        System.out.println(id);
        userService.verifyAccount(id);
    }
    public void approveWithdraw(int id){
        transactionImpl.approveWithdraw(id);
    }

    public double getDeposits(){
        double total_deposit = 0.0;
        for(Transactions t1: allTransactions){
            if(t1.getTransactionType().equals("DEPOSIT")){
                total_deposit += t1.getAmount();
            }
        }
        return total_deposit;
    }
    public double getWithdraws(){
        double total_withdraw = 0.0;
        for(Transactions t1: allTransactions){
            if(t1.getTransactionType().equals("WITHDRAW") && t1.getStatus()==1){
                total_withdraw += t1.getAmount();
            }
        }
        return total_withdraw;
    }

    public double getTheBalance(){
        double balance = 0.0;
        List<Account> accounts_balance = transactionImpl.getTheBalance();
        for(Account ac:accounts_balance){
            balance += ac.getBalance();
        }
        return balance;
    }

    public int getVerificationCount(){
        int counter = 0;
        List<Register> registers = userService.getUsersToVerify();
        for (Register register:registers ){
            counter ++;
        }
        return counter;

    }

    public int getApproveCount(){
        int counter = 0;
        List<Transactions> transact_withdraws = transactionImpl.getWithdrawsToApprove();
        for (Transactions transaction:transact_withdraws ){
            counter ++;
        }
        return counter;
    }

    public int getSaccoCount(){
        List<Register> sacco_count = userService.getUsers();
        int counter = 0;

        for(Register sacco: sacco_count){
            if(sacco.getStatus() == 1){
                counter++;
            }
            
        }
        return counter;
    }
    
}
