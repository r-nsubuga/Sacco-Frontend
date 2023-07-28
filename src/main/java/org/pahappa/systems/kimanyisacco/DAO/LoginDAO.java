package org.pahappa.systems.kimanyisacco.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Register;

public class LoginDAO {
    public Register checkByUsernameAndPassword(String username, String password) {
        Transaction transaction = null;
        Register user = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            user = (Register) session.createQuery("From Register WHERE username=:username AND password=:password")
            .setParameter("username", username)
            .setParameter("password", password)
            .uniqueResult();
            transaction.commit();
            
        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public List<Register> getAllUsers(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Register.class).list();
    }
}    
    

