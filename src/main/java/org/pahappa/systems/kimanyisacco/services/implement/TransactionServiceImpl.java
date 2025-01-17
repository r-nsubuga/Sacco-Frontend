package org.pahappa.systems.kimanyisacco.services.implement;

import org.pahappa.systems.kimanyisacco.DAO.TransactionDAO;
import org.pahappa.systems.kimanyisacco.DAO.UserDAO;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.services.TransactionService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TransactionServiceImpl implements TransactionService{

    TransactionDAO transDAO = new TransactionDAO();
    UserDAO userDAO = new UserDAO();
    private List<Transaction> transactionList;
    private List<Transaction> withdrawList;
    private List<Account> accountList;
    private Account account  ;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void makeDeposit(Transaction transactions) {
        transactions.setTransactionType(TransactionType.DEPOSIT);
        transactions.setTransactionStatus(TransactionStatus.APPROVED);
        transDAO.makeTransaction(transactions);
    
    }

    public List<Transaction> getWithdrawsToApprove(){
        withdrawList = transDAO.getTransactions();
        return withdrawList;

    }

    public void addToAccount(Account account){
        transDAO.addToAccount(account);
    }

    @Override
    public void makeWithdraw(Transaction transaction) {
        transaction.setTransactionType(TransactionType.WITHDRAW); 
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transDAO.makeTransaction(transaction);
    }

    public void approveWithdraw(int id){
        boolean state = transDAO.approveStatus(id);

        if(state){
            Transaction transactions = transDAO.getTransactionByID(id);
            this.account = transactions.getAccount();
            this.account.setBalance(this.account.getBalance() - transactions.getAmount());
            transDAO.addToAccount(account);

            String email = transactions.getAccount().getOwner().getEmail();
            String lastName = transactions.getAccount().getOwner().getLastName();
            String contact = transactions.getAccount().getOwner().getContact();

            sendApprovalEmail(email, lastName, transactions.getAmount(), contact);

        }
    }

    public void rejectWithdraw(int id){
        transDAO.rejectStatus(id);
        Transaction transactions = transDAO.getTransactionByID(id);
        String email = transactions.getAccount().getOwner().getEmail();
        String lastName = transactions.getAccount().getOwner().getLastName();
           
        sendRejectionEmail(email, lastName);
        
    }
    
    public List<Transaction> getTransactionsByID(Account account){
        transactionList = transDAO.getByAccountID(account);
        return transactionList;
    } 
    
    private void sendApprovalEmail(String recipientEmail, String lastName, double amount, String contact) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rayrmond.kizito@gmail.com", "igndljbhaywxfbre");
            }
        });
    
        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rayrmond.kizito@gmail.com", "Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Withdraw Approval");
            message.setText("Dear "+ lastName + ",\nYour request to withdraw "+ amount +" has been approved "+ 
            " \nThe money will be sent to you shortly on mobile money via " + contact +  
            " \n\nBest regards," +
            "\nKimwanyiSACCO Management");
    
            // Send the email
            Transport.send(message);
    
            System.out.println("Email sent successfully!");
    
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    } private void sendRejectionEmail(String recipientEmail, String lastName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rayrmond.kizito@gmail.com", "igndljbhaywxfbre");
            }
        });
    
        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rayrmond.kizito@gmail.com", "Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Withdraw Rejected");
            message.setText("Dear "+ lastName + ",\nYour request to withdraw has been rejected"+  
            "\nPlease try again!!! "+
            " \n\nBest regards, " +
            "\nKimwanyiSACCO Management");
    
            // Send the email
            Transport.send(message);
    
            System.out.println("Email sent successfully!");
    
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }



    public List<Transaction> getAllTransactions(){
         List<Transaction> transactionsList = transDAO.getAllTransactions();
         return transactionsList;
    }

    public List<Account> getTheBalance(){
        accountList = transDAO.getTheBalance();
        return accountList;
    }

    public Transaction unapprovedWithdraw(Account accountNumber){
        System.out.println("called");
        Transaction userTransactions = transDAO.getWithdrawByID(accountNumber);
        return userTransactions;
        
    }


}
