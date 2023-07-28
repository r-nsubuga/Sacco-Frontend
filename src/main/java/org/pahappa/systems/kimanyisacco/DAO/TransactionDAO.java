package org.pahappa.systems.kimanyisacco.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Register;
import org.pahappa.systems.kimanyisacco.models.Transactions;

public class TransactionDAO {

    Session session = SessionConfiguration.getSessionFactory().openSession();

    public void makeDeposit(Transactions trans) {
        Transaction transaction = null;
        //Session session = null;
        try{
            //Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(trans);
            transaction.commit();
        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            if( session != null){
                session.clear();
            }
        }
    }

    public void addToAccount(Account account) {
        Transaction transaction = null;
        try{
            session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(account);
            transaction.commit();
        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            if( session != null){
                session.clear();
            }
        }
    }

    public List<Transactions> getTransactions(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria =  session.createCriteria(Transactions.class);
        criteria.add(Restrictions.eq("transactionType", "WITHDRAW"));
        criteria.add(Restrictions.eq("status", 0));
        return criteria.list();
    }

    public List<Transactions> getAllTransactions(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Transactions.class).list();
    }

    public  List<Transactions> getByAccountID(Account account){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Transactions.class);
        criteria.add(Restrictions.eq("accNumber", account));
        criteria.add(Restrictions.eq("status", 1));
        return criteria.list();
    }

    public boolean approveStatus(int id){
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Transactions w_user = (Transactions) session.get(Transactions.class, id);
            w_user.setStatus(1);
            session.update(w_user);
            transaction.commit();

        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return true;
    }

    public Transactions getTransactionByID(int id){
        Transaction transaction = null;
        
        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Transactions w_transaction = (Transactions) session.get(Transactions.class, id);
        transaction.commit();
        return w_transaction;
    
    }

    public List<Account> getTheBalance(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Account.class).list();
    }
}
