package com.user.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.DB.Connector; // Import your database connection utility class

@WebServlet("/edit-transaction-servlet")
public class EditTransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditTransactionServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String transactionId = request.getParameter("id");
        String header = request.getParameter("header");
        String date = request.getParameter("date");
        String credit = request.getParameter("credit");
        String debit = request.getParameter("debit");

        // Database update logic
        try (Connection connection = Connector.getCon()) {
            String updateQuery = "UPDATE transactions SET header = ?, date = ?, credit = ?, debit = ? WHERE t_id = ?";
            PreparedStatement ps = connection.prepareStatement(updateQuery);

            ps.setString(1, header);
            ps.setString(2, date);
            ps.setString(3, credit);
            ps.setString(4, debit);
            ps.setString(5, transactionId);

            int rowsUpdated = ps.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                request.setAttribute("message", "Details updated successfully.");
            } else {
                request.setAttribute("message", "Failed to update details. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the transaction.");
        }

        // Forward to a JSP page to display the message
        request.getRequestDispatcher("mytransactions.jsp").forward(request, response);
    }
}
