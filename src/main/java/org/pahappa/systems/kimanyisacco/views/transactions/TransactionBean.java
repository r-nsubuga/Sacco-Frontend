package org.pahappa.systems.kimanyisacco.views.transactions;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionServiceImpl;

@ManagedBean(name = "transbean")
@SessionScoped
public class TransactionBean {
    private Transaction transaction;
    private Account account;
    private Member currentMember;
    private Date systemTime;

    TransactionService transactionService = new TransactionServiceImpl();

    private List<Transaction> transactionsList;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public  TransactionBean() {
        this.transaction = new Transaction();
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    public Member getCurrentMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (Member) externalContext.getSessionMap().get("LoggedMember");
    }

    public void setCurrentMember(Member currentMember) {
        this.currentMember = currentMember;
    }

    public void doDeposit() {
        Member member = getCurrentMember();
        this.systemTime = new Date();
        this.account = member.getAccount();

        if (this.transaction.getAmount() >= 202) {
            this.transaction.setAccount(this.account);
            this.transaction.setTransactionDate(this.systemTime);
            transactionService.makeDeposit(this.transaction);
            this.account.setBalance(this.account.getBalance() + this.transaction.getAmount());
            transactionService.addToAccount(this.account);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Deposit successful", "Success"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter valid value, Cannot deposit less than 210", "Failure"));
        }

    }

    public void doWithdraw() {
        Member member = getCurrentMember();
        System.out.println(member.getUsername());
        System.out.println(member.getAccount().getAccountNumber());

        this.systemTime = new Date();

        this.account = member.getAccount();
        System.out.println(account.getAccountNumber());
        this.transaction.setAccount(this.account);

        this.transaction.setTransactionDate(this.systemTime);

        System.out.println(this.account.getBalance());
        System.out.println(this.transaction.getAmount());

        Transaction unapprovedWithdraws = transactionService.unapprovedWithdraw(this.transaction.getAccount());

        if (unapprovedWithdraws==null) {
            System.out.println("Iam a null operation");
            if (this.transaction.getAmount() >= 200) {
                if (this.transaction.getAmount() < (this.account.getBalance() - 2)) {
                    transactionService.makeWithdraw(this.transaction);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Request sent, Wait for approval email",
                                    "Success"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Insufficient balance, withdraw smaller amount",
                                    "Failed"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect value, can't withdraw less than 200", "Failed"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "wait for pending withdraw to be approved",
                            "Failed"));
        }

    }

    public List<Transaction> getTransactionsList() {
        Member member = getCurrentMember();
        this.account = member.getAccount();
        transactionsList = transactionService.getTransactionsByID(this.account);
        System.out.println(transactionsList);
        return transactionsList;
    }

    public void setTransactionsList(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public double getUserInfo() {
        Member member = getCurrentMember();
        this.account = member.getAccount();
        double userBalance = this.account.getBalance();
        return userBalance;

    }

    public double getUserDeposit() {
        Member member = getCurrentMember();
        this.account = member.getAccount();

        transactionsList = transactionService.getTransactionsByID(this.account);
        int sum = 0;
        for (Transaction item : transactionsList) {
            if (item.getTransactionType().equals(TransactionType.DEPOSIT)) {
                sum += item.getAmount();
            }
        }
        return sum;

    }

    public double getUserWithdraw() {
        Member member = getCurrentMember();
        this.account = member.getAccount();

        transactionsList = transactionService.getTransactionsByID(this.account);
        int sum = 0;
        for (Transaction item : transactionsList) {
            if (item.getTransactionType().equals(TransactionType.WITHDRAW) && item.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
                sum += item.getAmount();
            }
        }
        return sum;

    }

    public String getFName() {
        Member member = getCurrentMember();
        String fname = member.getFirstName();
        return fname;
    }

    public String getLName() {
        Member member = getCurrentMember();
        String lname = member.getLastName();
        return lname;
    }

    public String getMemberAccountNumber() {
        Member member = getCurrentMember();
        String ac_number = member.getAccount().getAccountNumber();
        return ac_number;
    }

}