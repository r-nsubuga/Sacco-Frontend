package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionImpl;


@ManagedBean(name="adminBean")
@ViewScoped
public class AdminBean {
    private List<Member> members;
    private List<Transactions> transactions;
    private Member selectedMember;

    UserServiceImpl userService = new UserServiceImpl();
    TransactionImpl transactionImpl = new TransactionImpl();

    public List<Member> getMembers() {
        members = userService.getUsersToVerify();
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    //DIALOG BOX
    public Member getSelectedMember() {
        return selectedMember;
    }
    public void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    public void initSelectedMember(Member selectedMember){
        setSelectedMember(selectedMember);
    }

    //GET LIST OF WITHDRAWS TO APPROVE
     public List<Transactions> getTransactions() {
        transactions = transactionImpl.getWithdrawsToApprove();
        return transactions;
    }
    
    List<Transactions> allTransactions = transactionImpl.getAllTransactions();

    public void setAllTransactions(List<Transactions> allTransactions) {
        this.allTransactions = allTransactions;
    }

    //USER VERIFICATION
    public void verifyMember(int id){
        System.out.println(id);
        userService.verifyAccount(id);
    }

    public void rejectMember(int id){
        System.out.println(id);
        userService.rejectAccount(id);
    }

    //WITHDRAW APPROVAL OR REJECTION
    public void approveWithdraw(int id){
        transactionImpl.approveWithdraw(id);
    }

    public void rejectWithdraw(int id){
        transactionImpl.rejectWithdraw(id);
    }

    //ADMIN ACCOUNT CARD-DISPLAY
    public double getDeposits(){
        double totalDeposit = 0.0;
        for(Transactions t1: allTransactions){
            if(t1.getTransactionType().equals("DEPOSIT")){
                totalDeposit += t1.getAmount();
            }
        }
        return totalDeposit;
    }
    public double getWithdraws(){
        double totalWithdraw = 0.0;
        for(Transactions t1: allTransactions){
            if(t1.getTransactionType().equals("WITHDRAW") && t1.getStatus()==1){
                totalWithdraw += t1.getAmount();
            }
        }
        return totalWithdraw;
    }

    public double getTheBalance(){
        double balance = 0.0;
        List<Account> accountBalance = transactionImpl.getTheBalance();
        for(Account ac:accountBalance){
            balance += ac.getBalance();
        }
        return balance;
    }

    public int getVerificationCount(){
        int counter = 0;
        List<Member> users = userService.getUsersToVerify();
        for (Member user:users ){
            counter ++;
        }
        return counter;

    }

    public int getApproveCount(){
        int counter = 0;
        List<Transactions> withdrawsToApprove = transactionImpl.getWithdrawsToApprove();
        for (Transactions transaction:withdrawsToApprove ){
            counter ++;
        }
        return counter;
    }

    public int getSaccoCount(){
        List<Member> saccoCount = userService.getUsers();
        int counter = 0;

        for(Member sacco: saccoCount){
            if(sacco.getStatus() == 1){
                counter++;
            }
            
        }
        return counter;
    }

    //TABULATED DATA FOR THE ADMIN
    public List<Member> getAllMembers(){
        List<Member> allMemberList = userService.getUsersToDisplay();
        return allMemberList; 
    }

    public List<Transactions> getAllTransactions(){
        List<Transactions> adminDisplayTransaction = transactionImpl.getAllTransactions();
        List<Transactions> put = new ArrayList<>();
        for(Transactions transaction: adminDisplayTransaction){
            if(transaction.getStatus()==1){
                put.add(transaction);
            }
        }
        return put;
    }

    
}
