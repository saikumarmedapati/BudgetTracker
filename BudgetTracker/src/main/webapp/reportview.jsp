<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.user.entities.Transaction" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5 border border-dark rounded shadow p-4">
        <h1 class="text-center text-success">Transaction Report</h1>

        <%
            // Retrieve the attributes set by the servlet
            Double totalCredit = (Double) request.getAttribute("totalCredit");
            Double totalDebit = (Double) request.getAttribute("totalDebit");
            Double balance = (Double) request.getAttribute("balance");
            List<Transaction> transactionDetailsList = (List<Transaction>) request.getAttribute("transactionDetailsList");

            // Display the summary
            if (totalCredit != null && totalDebit != null && balance != null) {
        %>
            <div class="card mt-3">
                <div class="card-body">
                    <h5>Total Credit: <%= totalCredit %></h5>
                    <h5>Total Debit: <%= totalDebit %></h5>
                    <h5>Balance: <%= balance %></h5>
                </div>
            </div>

            <!-- Transaction Details Table -->
            <div class="mt-4">
                <h3 class="text-primary">Transaction Details</h3>
                <table class="table table-striped border border-dark rounded shadow p-4">
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>Description</th>
                            <th>Date</th>
                            <th>Credit</th>
                            <th>Debit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Display transaction details if available
                            if (transactionDetailsList != null && !transactionDetailsList.isEmpty()) {
                                for (Transaction detail : transactionDetailsList) {
                        %>
                                    <tr>
                                        <td><%= detail.getTransactionId() %></td>
                                        <td><%= detail.getHeader() %></td>
                                        <td><%= detail.getDate() %></td>
                                        <td><%= detail.getCredit() %></td>
                                        <td><%= detail.getDebit() %></td>
                                    </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="5" class="text-center">No transactions found for the selected period.</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        <%
            } else {
        %>
            <div class="alert alert-danger mt-3">
                <strong>Error:</strong> No report data available.
            </div>
        <%
            }
        %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
