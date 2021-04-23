package com.paymentbook.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDAO{

    List<Transaction> getAllTransactions();

    Transaction findTransactionById(int theId);

    Transaction saveTransaction(Transaction theTransaction);

    void deleteTransactionById(int theId);

}
