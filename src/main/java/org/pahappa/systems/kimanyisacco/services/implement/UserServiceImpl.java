package org.pahappa.systems.kimanyisacco.services.implement;

import org.pahappa.systems.kimanyisacco.services.UserService;
import org.pahappa.systems.kimanyisacco.models.Register;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.security.SecureRandom;
import org.pahappa.systems.kimanyisacco.models.Account;
//import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.DAO.UserDAO;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAO();
    
    @Override
    public void authenticateUser(Register user) {
        List<Register> use_list = new ArrayList<>();
        use_list = userDAO.getUsers();

        SecureRandom rand = new SecureRandom();
        int upper =1000;
        Account random = new Account(("ACKS-" + ((Integer)rand.nextInt(upper))));

        user.setAccountNumber(random);

        String password = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(password);


        if(use_list.isEmpty()){
            userDAO.save(user);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User registered successfully");
            FacesContext.getCurrentInstance().addMessage("myForm:messages", message);

        }else{
            for(Register use:use_list){
                System.out.println(use.getUsername());
                System.out.println(user.getUsername());
             if(user.getUsername().equals(use.getUsername())){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "username already exists!");
                FacesContext.getCurrentInstance().addMessage("myForm:messages", message);

                String base_url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(base_url + Hyperlinks.register);
                } catch (IOException e) {
                    e.printStackTrace();
                }
    
                break;
            }
            else {
                System.out.println(user.getUsername());
                userDAO.save(user);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User registered successfully");
                FacesContext.getCurrentInstance().addMessage("myForm:messages", message);
                break;
            }
        
            }
        }
       
    }

    public List<Register> getUsers() {
        return userDAO.getUsers();
    }

    public List<Register> getUsersToVerify() {
        return userDAO.getUsersToVerify();
    }

    public void verifyAccount(int id){
        userDAO.verifyStatus(id);
        Register email_member = userDAO.sendToMember(id);
        sendApprovalEmail(email_member.getEmail(), email_member.getLastName());
    }

    private void sendApprovalEmail(String recipientEmail, String lastName) {
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
            message.setSubject("Membership Verification Successful");
            message.setText("Dear "+ lastName + ",\nYour request has been approved. You can now log in to the system. "+ 
            " \nThank you for choosing us, we are grateful." + 
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
    
}