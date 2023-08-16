package org.pahappa.systems.kimanyisacco.DAO;

import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Register;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;



public class UserDAO {
    public void save(Register user) {
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Register> getUsers(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Register.class).list();
    }

    public List<Register> getUsersToVerify(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Register.class);
        criteria.add(Restrictions.eq("status", 0));
        return criteria.list();
    }

    public void verifyStatus(int id){
       Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Register v_user = (Register) session.get(Register.class, id);
            v_user.setStatus(1);
            session.update(v_user);
            transaction.commit();
            
        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } 

    }

    public Register sendToMember(int id){
       Transaction transaction = null;
       
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Register member = (Register) session.get(Register.class, id);
            transaction.commit();
            return member;
    }

    
    
}
