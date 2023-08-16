package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Account;

public interface TransactionService {
    
    void makeDeposit(Transaction transaction);
    List<Transaction> getWithdrawsToApprove();
    void addToAccount(Account account);
    void approveWithdraw(int id);
    void makeWithdraw(Transaction transaction);
    void rejectWithdraw(int id);
    List<Transaction> getAllTransactions();
    List<Account> getTheBalance();
    List<Transaction> getTransactionsByID(Account account);
    Transaction unapprovedWithdraw(Account accountNumber);
}
