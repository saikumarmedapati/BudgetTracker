package com.user.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.DAO.TransactionDaoImpl;


@WebServlet("/delete-transaction-servlet")
public class DeleteTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DeleteTransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String transactionId = request.getParameter("id");

        TransactionDaoImpl transactionDAO = new TransactionDaoImpl();

        boolean isDeleted = transactionDAO.removeTransaction(transactionId);

        // Set response message based on the result
        if (isDeleted) {
            request.setAttribute("message", "Transaction deleted successfully.");
        } else {
            request.setAttribute("message", "Failed to delete the transaction. Please try again.");
        }

        request.getRequestDispatcher("mytransactions.jsp").forward(request, response); // Redirect to a transactions list page
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
