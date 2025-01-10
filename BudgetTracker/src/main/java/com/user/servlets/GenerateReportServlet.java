package com.user.servlets;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.DAO.TransactionDaoImpl;
import com.user.entities.Transaction;

@WebServlet("/generate-report-servlet")
public class GenerateReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GenerateReportServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get input parameters
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            // Generate the report using the TransactionDaoImpl
            TransactionDaoImpl transactionDao = new TransactionDaoImpl();
            Transaction report = transactionDao.generateReport(startDateStr, endDateStr);

            if (report == null || (report.getTotalCredit() == 0.0 && report.getTotalDebit() == 0.0)) {
                request.setAttribute("error", "No transactions found for the selected date range.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("generatereport.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Set the transaction details and report as request attributes
            request.setAttribute("totalCredit", report.getTotalCredit());
            request.setAttribute("totalDebit", report.getTotalDebit());
            request.setAttribute("balance", report.getBalance());
            request.setAttribute("transactionDetailsList", report.getTransactionDetailsList());

            // Forward the request to the new reportview.jsp page
            RequestDispatcher dispatcher = request.getRequestDispatcher("reportview.jsp");
            dispatcher.forward(request, response);

        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Invalid date format. Please select valid dates.");
            request.getRequestDispatcher("generatereport.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while generating the report.");
            request.getRequestDispatcher("generatereport.jsp").forward(request, response);
        }
    }
}
