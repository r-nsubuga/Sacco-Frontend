package org.pahappa.systems.kimanyisacco.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
//import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Transaction;

public class TransactionDAO {

    Session session = SessionConfiguration.getSessionFactory().openSession();

    public void makeTransaction(Transaction memberTransactionInProcess) {
        org.hibernate.Transaction transaction = null;
        //Session session = null;
        try{
            //Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(memberTransactionInProcess);
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
        org.hibernate.Transaction transaction = null;
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

    public List<Transaction> getTransactions(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria =  session.createCriteria(Transaction.class);
        criteria.add(Restrictions.eq("transactionType", TransactionType.WITHDRAW));
        criteria.add(Restrictions.eq("transactionStatus", TransactionStatus.PENDING));
        return criteria.list();
    }

    public List<Transaction> getAllTransactions(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Transaction.class).list();
    }

    public Transaction getWithdrawByID(Account accountNumber){
        Session session = SessionConfiguration.getSessionFactory().openSession();
    
        Criteria criteria = session.createCriteria(Transaction.class);
        criteria.add(Restrictions.eq("account", accountNumber));
        criteria.add(Restrictions.eq("transactionStatus", TransactionStatus.PENDING));
        return (Transaction) criteria.uniqueResult();
    }

    public  List<Transaction> getByAccountID(Account account){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Transaction.class);
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("transactionStatus", TransactionStatus.APPROVED));
        return criteria.list();
    }

    public boolean approveStatus(int id){
        org.hibernate.Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Transaction withdrawTransactionForUser = (Transaction) session.get(Transaction.class, id);
            withdrawTransactionForUser.setTransactionStatus(TransactionStatus.APPROVED);
            session.update(withdrawTransactionForUser);
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

    public void rejectStatus(int id){
        org.hibernate.Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Transaction withdrawTransactionForUser = (Transaction) session.get(Transaction.class, id);
            withdrawTransactionForUser.setTransactionStatus(TransactionStatus.REJECTED);
            session.update(withdrawTransactionForUser);
            transaction.commit();

        }
        catch(Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Transaction getTransactionByID(int id){
        org.hibernate.Transaction transaction = null;
        
        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Transaction w_transaction = (Transaction) session.get(Transaction.class, id);
        transaction.commit();
        return w_transaction;
    
    }

    public List<Account> getTheBalance(){
        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Account.class).list();
    }
}
