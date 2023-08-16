package org.pahappa.systems.kimanyisacco.services.implement;

import org.pahappa.systems.kimanyisacco.services.UserService;
import org.pahappa.systems.kimanyisacco.models.Member;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.security.SecureRandom;
import org.pahappa.systems.kimanyisacco.models.Account;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.DAO.UserDAO;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAO();
   
    List<Member> memberList = new ArrayList<>();
    SecureRandom rand = new SecureRandom();
    int upper =100000;

    @Override
    public void authenticateUser(Member member) {

        memberList = userDAO.getUsers();
        Account random = new Account(("ACKS-" + ((Integer)rand.nextInt(upper))));

        member.setAccount(random);

        if(memberList.isEmpty()){
            String password = BCrypt.hashpw(member.getPassword(),BCrypt.gensalt());
            member.setPassword(password);
            member.setMemberStatus(MemberStatus.PENDING);
            userDAO.save(member);

        }else{
            for(Member use:memberList){
             if(member.getUsername().equals(use.getUsername())){
                break;
            }
            else {
                System.out.println(member.getUsername());
                userDAO.save(member);
                break;
            }
        
            }
        }
       
    }

    public List<Member> getMembers() {
        return userDAO.getUsers();
    }
    public List<Member> getMembersToDisplay() {
        return userDAO.getMembersToDisplay();
    }

    public List<Member> getMembersToVerify() {
        return userDAO.getMembersToVerify();
    }

    public void verifyAccount(int id){
        userDAO.changeStatusToVerified(id);
        Member email_member = userDAO.sendToMember(id);
        sendApprovalEmail(email_member.getEmail(), email_member.getLastName());
    }

    public void rejectAccount(int id){
        userDAO.changeStatusToRejected(id);
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
