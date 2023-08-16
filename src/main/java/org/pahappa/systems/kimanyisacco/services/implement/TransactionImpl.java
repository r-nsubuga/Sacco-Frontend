package org.pahappa.systems.kimanyisacco.services.implement;

import org.pahappa.systems.kimanyisacco.DAO.TransactionDAO;
import org.pahappa.systems.kimanyisacco.DAO.UserDAO;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.services.TransactionService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TransactionImpl implements TransactionService{

    TransactionDAO transDAO = new TransactionDAO();
    UserDAO userDAO = new UserDAO();
    private List<Transactions> list_trans;
    private List<Transactions> withdraw_list;
    private List<Account> account_list;
    private Account account  ;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void makeDeposit(Transactions transactions) {
        transactions.setTransactionType("DEPOSIT");
        transactions.setStatus(1);
        transDAO.makeDeposit(transactions);
    
    }

    public List<Transactions> getWithdrawsToApprove(){
        withdraw_list = transDAO.getTransactions();
        return withdraw_list;
    }

    public void addToAccount(Account account){
        transDAO.addToAccount(account);
    }

    @Override
    public void makeWithdraw(Transactions transaction) {
        transaction.setTransactionType("WITHDRAW");
        transDAO.makeDeposit(transaction);
    }

    public void approveWithdraw(int id){
        boolean state = transDAO.approveStatus(id);

        if(state){
            Transactions transactions = transDAO.getTransactionByID(id);
            this.account = transactions.getAccNumber();
            this.account.setBalance(this.account.getBalance() - transactions.getAmount());
            transDAO.addToAccount(account);

            String email = transactions.getAccNumber().getOwner().getEmail();
            String lastName = transactions.getAccNumber().getOwner().getLastName();
            String contact = transactions.getAccNumber().getOwner().getContact();

            sendApprovalEmail(email, lastName, transactions.getAmount(), contact);

        }
    }
    
    public List<Transactions> getList(Account account){
        list_trans = transDAO.getByAccountID(account);
        return list_trans;
    } 
    
    private void sendApprovalEmail(String recipientEmail, String lastName, int amount, String contact) {
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
            message.setText("Dear "+ lastName + ",\nYour request to withdraw "+ amount +"has been approved "+ 
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
    }

    public List<Transactions> getAllTransactions(){
         List<Transactions> transactions_list = transDAO.getAllTransactions();
         return transactions_list;
    }

    public List<Account> getTheBalance(){
        account_list = transDAO.getTheBalance();
        return account_list;
    }


}
