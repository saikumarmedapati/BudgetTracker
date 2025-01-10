package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DB.Connector;
import com.user.entities.Transaction;

public class TransactionDaoImpl implements TransactionDao {

	private Connection connection = Connector.getCon();

	public TransactionDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public TransactionDaoImpl() {
		super();
	}

	/**
	 * Saves a transaction to the database.
	 * 
	 * @param transaction The transaction object containing the details to save.
	 * @return true if the transaction is saved successfully, false otherwise.
	 */
	@Override
	public boolean addDetails(Transaction t) {
	    boolean f = false;
	    try {
	        String sql = "insert into transactions(txn_number, header, date, credit, debit) values (?, ?, ?, ?, ?)";
	        PreparedStatement ps = connection.prepareStatement(sql);

	        // Set the transaction ID (String)
	        ps.setString(1, t.getTransactionId());
	        
	        ps.setString(2, t.getHeader());

	     // Set the date as a VARCHAR (string) directly
	        String dateValue = t.getDate();
	        if (dateValue != null && !dateValue.trim().isEmpty()) {
	            ps.setString(3, dateValue);  // Set the date as a string
	        } else {
	            ps.setNull(3, java.sql.Types.VARCHAR);  // If date is null or empty, set it as NULL
	        }

	        ps.setDouble(4, t.getCredit());
	        ps.setDouble(5, t.getDebit());

	        int res = ps.executeUpdate();

	        if (res == 1) {
	            f = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return f;
	}


	/**
	 * Calculates the total credits, total debits, and balance.
	 * 
	 * @param transaction The transaction object to update with calculated values.
	 */
	public Transaction calculateTotals() {
		Transaction totals = new Transaction(); // Create a new instance for totals
	    try {
	        // Query to calculate the total credits and total debits
	        String sql = "SELECT SUM(credit) AS totalCredit, SUM(debit) AS totalDebit FROM transactions";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ResultSet res = ps.executeQuery();
	        if (res.next()) {
	            double totalCredit = res.getDouble("totalCredit"); // Get the total credit
	            double totalDebit = res.getDouble("totalDebit");   // Get the total debit
	            double balance = totalCredit - totalDebit;         // Calculate the balance

	            // Set the values in the Transaction object
	            totals.setTotalCredit(totalCredit);
	            totals.setTotalDebit(totalDebit);
	            totals.setBalance(balance);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log the exception for debugging
	    }
	    return totals; // Return the calculated totals
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT t_id, header, date, credit, debit FROM transactions";

		try {
			
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) 
			{
				Transaction t = new Transaction();
				t.setTransactionId(resultSet.getString("t_id"));
				t.setHeader(resultSet.getString("header"));
				t.setDate(resultSet.getString("date")); // Convert Date to String
				t.setCredit(parseDoubleOrZero(resultSet.getString("credit"))); // Handle VARCHAR credit
				t.setDebit(parseDoubleOrZero(resultSet.getString("debit"))); // Handle VARCHAR debit

				transactions.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace(); // Log the exception
		}

		return transactions;
	}

	private double parseDoubleOrZero(String value) {
		try {
			return value != null ? Double.parseDouble(value) : 0.0;
		} catch (NumberFormatException e) {
			return 0.0; // Return 0.0 if the value cannot be parsed
		}
	}
	
	//update transactions
	@Override
	public boolean updateTransaction(String transactionId, double credit, double debit) {
	    boolean f = false;
	    try {
	        // Update SQL query for updating credit and debit values based on the transaction ID
	        String sql = "UPDATE transactions SET credit = ?, debit = ? WHERE t_id = ?";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setDouble(1, credit);
	        ps.setDouble(2, debit);
	        ps.setString(3, transactionId); // Use transactionId to update the specific record

	        int i = ps.executeUpdate();

	        if (i == 1) {
	            f = true; // Transaction was updated successfully
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log any exceptions
	    }
	    return f;
	}
	
	//retrive transaction details by id
	public Transaction getTransactionById(String transactionId) {
	    Transaction transaction = null;
	    String query = "SELECT t_id, header, date, credit, debit FROM transactions WHERE t_id = ?";

	    try {
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setString(1, transactionId); // Set the transactionId parameter
	        ResultSet resultSet = ps.executeQuery();

	        if (resultSet.next()) {
	            transaction = new Transaction();
	            transaction.setTransactionId(resultSet.getString("t_id"));
	            transaction.setHeader(resultSet.getString("header"));
	            transaction.setDate(resultSet.getString("date")); // Convert Date to String
	            transaction.setCredit(parseDoubleOrZero(resultSet.getString("credit"))); // Handle VARCHAR credit
	            transaction.setDebit(parseDoubleOrZero(resultSet.getString("debit"))); // Handle VARCHAR debit
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return transaction;
	}

	
	@Override
	public boolean removeTransaction(String transactionId) {
	    boolean f = false;
	    try {
	        // Delete SQL query to remove a transaction by its transaction ID
	        String sql = "DELETE FROM transactions WHERE t_id = ?";
	        PreparedStatement ps = connection.prepareStatement(sql);
	        ps.setString(1, transactionId); // Use transactionId to remove the specific record

	        int i = ps.executeUpdate();

	        if (i == 1) {
	            f = true; // Transaction was deleted successfully
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Log any exceptions
	    }
	    return f;
	}
	
	public Transaction generateReport(String startDate, String endDate) {
	    Transaction transactionReport = new Transaction();  // Create a Transaction object to hold the aggregated report data
	    List<Transaction> transactionDetailsList = new ArrayList<>();  // List to hold individual transaction details

	    // SQL query to select total credits and debits between the start and end date
	    String querySummary = "SELECT SUM(credit) AS totalCredit, SUM(debit) AS totalDebit FROM transactions WHERE date BETWEEN ? AND ?";
	    String queryDetails = "SELECT t_id, header, date, credit, debit FROM transactions WHERE date BETWEEN ? AND ?";  // Query to fetch individual transactions

	    try {
	        // Prepare the statement for the summary (aggregated) query
	        PreparedStatement psSummary = connection.prepareStatement(querySummary);
	        psSummary.setString(1, startDate);  // Set the start date
	        psSummary.setString(2, endDate);    // Set the end date

	        // Execute the summary query and retrieve the results
	        ResultSet resultSetSummary = psSummary.executeQuery();

	        // Check if the result set has data and extract the totals
	        if (resultSetSummary.next()) {
	            double totalCredit = resultSetSummary.getDouble("totalCredit"); // Get total credit
	            double totalDebit = resultSetSummary.getDouble("totalDebit");   // Get total debit

	            // Set the values in the Transaction object
	            transactionReport.setTotalCredit(totalCredit);
	            transactionReport.setTotalDebit(totalDebit);
	            transactionReport.setBalance(totalCredit - totalDebit);  // Calculate balance
	        }

	        // Prepare the statement for the individual transaction details query
	        PreparedStatement psDetails = connection.prepareStatement(queryDetails);
	        psDetails.setString(1, startDate);  // Set the start date
	        psDetails.setString(2, endDate);    // Set the end date

	        // Execute the details query and retrieve the results
	        ResultSet resultSetDetails = psDetails.executeQuery();

	        // Loop through the result set and add each transaction detail to the list
	        while (resultSetDetails.next()) {
	            Transaction details = new Transaction();
	            details.setTransactionId(resultSetDetails.getString("t_id"));
	            details.setHeader(resultSetDetails.getString("header"));
	            details.setDate(resultSetDetails.getString("date"));
	            details.setCredit(resultSetDetails.getDouble("credit"));
	            details.setDebit(resultSetDetails.getDouble("debit"));


	            transactionDetailsList.add(details);  // Add the transaction details to the list
	        }

	        // Set the transaction details list in the transactionReport object
	        transactionReport.setTransactionDetailsList(transactionDetailsList);

	    } catch (SQLException e) {
	        e.printStackTrace();  // Log any exceptions that occur
	    }

	    // Return the Transaction object containing the aggregated report and details
	    return transactionReport;
	}
}