package com.DAO;

import com.user.entities.Transaction;

public interface TransactionDao {
	
	public boolean addDetails(Transaction t);
	
	public Transaction calculateTotals();
	
	public boolean updateTransaction(String transactionId, double credit, double debit);
	
	public boolean removeTransaction(String transactionId);
}
