package org.pahappa.systems.kimanyisacco.views.admin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.pahappa.systems.kimanyisacco.services.implement.UserServiceImpl;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionServiceImpl;


@ManagedBean(name="adminBean")
@ViewScoped
public class AdminBean {
    private List<Member> members;
    private List<Transaction> transactions;
    private Member selectedMember;

    UserServiceImpl userService = new UserServiceImpl();
    TransactionService transactionService = new TransactionServiceImpl();

    public List<Member> getMembers() {
        members = userService.getMembersToVerify();
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
     public List<Transaction> getTransactions() {
        transactions = transactionService.getWithdrawsToApprove();
        return transactions;
    }
    
    List<Transaction> allTransactions = transactionService.getAllTransactions();

    public void setAllTransactions(List<Transaction> allTransactions) {
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
        transactionService.approveWithdraw(id);
    }

    public void rejectWithdraw(int id){
        transactionService.rejectWithdraw(id);
    }

    //ADMIN ACCOUNT CARD-DISPLAY
    public double getDeposits(){
        double totalDeposit = 0.0;
        for(Transaction t1: allTransactions){
            if(t1.getTransactionType().equals(TransactionType.DEPOSIT)){
                totalDeposit += t1.getAmount();
            }
        }
        return totalDeposit;
    }
    public double getWithdraws(){
        double totalWithdraw = 0.0;
        for(Transaction t1: allTransactions){
            if(t1.getTransactionType().equals(TransactionType.WITHDRAW) && t1.getTransactionStatus().equals(TransactionStatus.APPROVED)){
                totalWithdraw += t1.getAmount();
            }
        }
        return totalWithdraw;
    }

    public double getTheBalance(){
        double balance = 0.0;
        List<Account> accountBalance = transactionService.getTheBalance();
        for(Account ac:accountBalance){
            balance += ac.getBalance();
        }
        return balance;
    }

    public int getVerificationCount(){
        int counter = 0;
        List<Member> listOfUsersToVerify = userService.getMembersToVerify();
        for (Member member:listOfUsersToVerify ){
            counter ++;
        }
        return counter;

    }

    public int getApproveCount(){
        int counter = 0;
        List<Transaction> withdrawsToApprove = transactionService.getWithdrawsToApprove();
        for (Transaction transaction:withdrawsToApprove ){
            counter ++;
        }
        return counter;
    }

    public int getSaccoCount(){
        List<Member> saccoCount = userService.getMembers();
        int counter = 0;

        for(Member sacco: saccoCount){
            if(sacco.getMemberStatus().equals(MemberStatus.APPROVED)){
                counter++;
            }
            
        }
        return counter;
    }

    //TABULATED DATA FOR THE ADMIN
    public List<Member> getAllMembers(){
        List<Member> allMemberList = userService.getMembersToDisplay();
        return allMemberList; 
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> adminDisplayTransaction = transactionService.getAllTransactions();
        List<Transaction> transactionsForAdmin = new ArrayList<>();
        for(Transaction transaction: adminDisplayTransaction){
            if(transaction.getTransactionStatus().equals(TransactionStatus.APPROVED)){
                transactionsForAdmin.add(transaction);
            }
        }
        return transactionsForAdmin;
    }

    
}
