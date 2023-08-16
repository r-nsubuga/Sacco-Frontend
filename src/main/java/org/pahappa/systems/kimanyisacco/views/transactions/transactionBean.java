package org.pahappa.systems.kimanyisacco.views.transactions;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.pahappa.systems.kimanyisacco.constants.Gender;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.implement.TransactionImpl;

@ManagedBean(name = "transbean")
@SessionScoped
public class transactionBean {
    private Transactions transactions;
    private Account account;
    private Member currentUser;
    private Date systemTime;

    TransactionImpl transact = new TransactionImpl();

    private List<Transactions> transactionsList;

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

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    public Member getCurrentUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (Member) externalContext.getSessionMap().get("LoggedUser");
    }

    public void setCurrentUser(Member currentUser) {
        this.currentUser = currentUser;
    }

    public void doDeposit() {
        Member user = getCurrentUser();
        this.systemTime = new Date();
        this.account = user.getAccount();

        if (this.transactions.getAmount() >= 202) {
            this.transactions.setAccount(this.account);
            this.transactions.setTransactionDate(this.systemTime);
            transact.makeDeposit(this.transactions);
            this.account.setBalance(this.account.getBalance() + this.transactions.getAmount());
            transact.addToAccount(this.account);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Deposit successful", "Success"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter valid value, Cannot deposit less than 210", "Failure"));
        }

    }

    public void doWithdraw() {
        Member user = getCurrentUser();
        System.out.println(user.getUsername());
        System.out.println(user.getAccount().getAccountNumber());

        this.systemTime = new Date();

        this.account = user.getAccount();
        System.out.println(account.getAccountNumber());
        this.transactions.setAccount(this.account);

        this.transactions.setTransactionDate(this.systemTime);

        System.out.println(this.account.getBalance());
        System.out.println(this.transactions.getAmount());

        Transactions withdraw_user = transact.unapprovedWithdraw(this.transactions.getAccount());

        if (withdraw_user==null) {
            System.out.println("Iam a null operation");
            if (this.transactions.getAmount() >= 200) {
                if (this.transactions.getAmount() < (this.account.getBalance() - 2)) {
                    transact.makeWithdraw(this.transactions);

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

    public List<Transactions> getTransactionsList() {
        Member user = getCurrentUser();
        this.account = user.getAccount();
        transactionsList = transact.getList(this.account);
        System.out.println(transactionsList);
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public double getUserInfo() {
        Member user = getCurrentUser();
        this.account = user.getAccount();
        double userBalance = this.account.getBalance();
        return userBalance;

    }

    public double getUserDeposit() {
        Member user = getCurrentUser();
        this.account = user.getAccount();

        transactionsList = transact.getList(this.account);
        int sum = 0;
        for (Transactions item : transactionsList) {
            if (item.getTransactionType().equals("DEPOSIT")) {
                sum += item.getAmount();
            }
        }
        return sum;

    }

    public double getUserWithdraw() {
        Member user = getCurrentUser();
        this.account = user.getAccount();

        transactionsList = transact.getList(this.account);
        int sum = 0;
        for (Transactions item : transactionsList) {
            if (item.getTransactionType().equals("WITHDRAW") && item.getStatus() == 1) {
                sum += item.getAmount();
            }
        }
        return sum;

    }

    public String getFName() {
        Member user = getCurrentUser();
        String fname = user.getFirstName();
        return fname;
    }

    public String getLName() {
        Member user = getCurrentUser();
        String lname = user.getLastName();
        return lname;
    }

    public String getMemberAccountNumber() {
        Member user = getCurrentUser();
        String ac_number = user.getAccount().getAccountNumber();
        return ac_number;
    }

}