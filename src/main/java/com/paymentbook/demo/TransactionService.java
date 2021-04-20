package com.paymentbook.demo;

import java.util.List;

public interface TransactionService {

    public List<Transaction> findAllTransactions();

    public String screenTransactionById(int theId);

    public Transaction findTransactionById(int theId);

    public Transaction saveTransaction(Transaction theTransaction);

    public int deleteTransactionById(int theId);

}