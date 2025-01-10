package com.user.servlets;

import java.io.IOException;

import com.DAO.TransactionDaoImpl;
import com.user.entities.Transaction;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class AddTransactionServlet
 */

@WebServlet("/add-transaction-servlet")
public class AddTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTransactionServlet() {
        super();
    }

	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String header = request.getParameter("header");
        String dateStr = request.getParameter("date");
        String type = request.getParameter("type");

        // Initialize credit/debit as doubles
        double credit = 0.0;
        double debit = 0.0;

        // Retrieve the transactionId and parse it as an integer
        String transactionIdStr = request.getParameter("transactionId");

        // Retrieve the amount and assign to credit or debit based on the type
        String amount = request.getParameter("amount");
        if (amount != null && !amount.trim().isEmpty()) {
            try {
                double parsedAmount = Double.parseDouble(amount); // Parse amount to double
                if ("income".equalsIgnoreCase(type)) {
                    credit = parsedAmount;
                } else if ("expense".equalsIgnoreCase(type)) {
                    debit = parsedAmount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount format: " + amount);
            }
        }
        
//        Date date = null;
//        if (dateStr != null && !dateStr.trim().isEmpty()) {
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                date = sdf.parse(dateStr); // Parse the string into a Date object
//            } catch (Exception e) {
//                e.printStackTrace();
//                // Handle invalid date format gracefully, for example by setting date to null
//            }
//        }

        // Print debug statements for input data
//        System.out.println("Transaction Data Received:");
//        System.out.println("Transaction ID: " + transactionIdStr);
//        System.out.println("Header: " + header);
//        System.out.println("Date: " + dateStr);
//        System.out.println("Type: " + type);
//        System.out.println("Credit: " + credit);
//        System.out.println("Debit: " + debit);

        // Create a transaction object
        Transaction t = new Transaction();
        t.setTransactionId(transactionIdStr);
        t.setHeader(header);
        t.setDate(dateStr); // Set the validated date
        t.setCredit(credit);
        t.setDebit(debit);

        // Save transaction to the database
        TransactionDaoImpl transactionDao = new TransactionDaoImpl();
        boolean isSaved = transactionDao.addDetails(t);
//        System.out.println("Transaction Saved: " + isSaved);
        
        response.sendRedirect("index.jsp");
    }
}