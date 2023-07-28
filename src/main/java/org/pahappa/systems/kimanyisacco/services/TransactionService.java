package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Transactions;

import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Account;

public interface TransactionService {
    
    void makeDeposit(Transactions transactions);
    void addToAccount(Account account);
    void approveWithdraw(int id);
    void makeWithdraw(Transactions transactions);
    List<Transactions> getList(Account account);
}
