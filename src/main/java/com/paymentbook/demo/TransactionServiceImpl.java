package com.paymentbook.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionDAO transactionDAO;
    private static final String[] blackListEmails = new String[] {"blacklist1@gmail.com","blacklist2@gmail.com","blacklist3@gmail.com","blacklist4@gmail.com"};

    @Autowired
    public TransactionServiceImpl(@Qualifier("transactionDAOJpaImpl") TransactionDAO theTransactionDao){
        transactionDAO = theTransactionDao;
    }

    @Override
    @Transactional
    public List<Transaction> findAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    @Override
    @Transactional
    public String screenTransactionById(int theId) {
        Transaction theTransaction=transactionDAO.findTransactionById(theId);
        String dateInString = theTransaction.getDate();
        LocalDate localDate = LocalDate.parse(dateInString);
        LocalDate today = LocalDate.now();

        long difference=DAYS.between(localDate, today);
        boolean isInBlackList = Arrays.asList(blackListEmails).contains(theTransaction.getEmail());

        //return Reject if the email id is in blacklist and the transaction has been made in the last 30 days, otherwise return accept
        if(isInBlackList && difference<30) {
            return "REJECT";
        }
        else {
            return "ACCEPT";
        }

    }

    @Override
    @Transactional
    public Transaction findTransactionById(int theId) {
        return transactionDAO.findTransactionById(theId);
    }

    @Override
    @Transactional
    public Transaction saveTransaction(Transaction theTransaction) {
        return transactionDAO.saveTransaction(theTransaction);
    }

    @Override
    @Transactional
    public int deleteTransactionById(int theId) {
        transactionDAO.deleteTransactionById(theId);
        return theId;
    }
}