package com.paymentbook.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path="/demo")
public class TransactionRestController {

    private TransactionService transactionService;

    @Autowired
    public TransactionRestController(TransactionService thetransactionservice){
        transactionService = thetransactionservice;

    }

    //For getting all the transactions
    @RequestMapping(value = "/transactions", method= RequestMethod.GET)
    public ResponseEntity<List<Transaction>> findAll(){
        System.out.println(transactionService.findAllTransactions().size());
        return new ResponseEntity<List<Transaction>>(transactionService.findAllTransactions(), HttpStatus.OK);

    }

    //For getting whether the transaction with a given id is rejected or accepted
    @RequestMapping(value = "/transactions/{transactionId}", method = RequestMethod.GET)
    public String screenTransaction(@PathVariable int transactionId)  {
        String theTransaction = transactionService.screenTransactionById(transactionId);

        return theTransaction;
    }

    //For adding a transaction
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public Transaction addTransaction(@RequestBody Transaction theTransaction){

        return (transactionService.saveTransaction(theTransaction));
    }

    //For updating a transaction
    @RequestMapping(value = "/transactions", method = RequestMethod.PUT)
    public Transaction updateTransaction(@RequestBody Transaction theTransaction){
        Transaction transaction = transactionService.findTransactionById(theTransaction.getId());
        if (transaction == null) {
            throw new RuntimeException("Transaction to update doesn't exist");
        }
        return (transactionService.saveTransaction(theTransaction));
    }

    //For deleting a transaction
    @RequestMapping(value = "/transactions/{transactionId}", method = RequestMethod.DELETE)
    public String deleteTransaction(@PathVariable int transactionId){
        Transaction tempTransaction = transactionService.findTransactionById(transactionId);
        if(tempTransaction == null){
            throw new RuntimeException("Transaction Id not found");
        }
        transactionService.deleteTransactionById(transactionId);
        return "deleted transaction id " + transactionId;

    }
}