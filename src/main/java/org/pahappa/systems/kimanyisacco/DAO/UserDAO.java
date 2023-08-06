package org.pahappa.systems.kimanyisacco.DAO;

import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Member;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;




public class UserDAO {
    //creating a Member object
    public void save(Member user) {
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

    //gets a list of all users from the database
    public List<Member> getUsers(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Member.class).list();
    }

    public List<Member> getUsersToDisplay(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Member.class);
        criteria.add(Restrictions.eq("status", 1));
        return criteria.list();
    }

    public List<Member> getUsersToVerify(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Member.class);
        criteria.add(Restrictions.eq("status", 0));
        return criteria.list();
    }

    public void verifyStatus(int id){
       Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member v_user = (Member) session.get(Member.class, id);
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

    public void rejectStatus(int id){
       Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member v_user = (Member) session.get(Member.class, id);
            v_user.setStatus(2);
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

    public Member sendToMember(int id){
       Transaction transaction = null;
       
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member member = (Member) session.get(Member.class, id);
            transaction.commit();
            return member;
    }

    
    
}
