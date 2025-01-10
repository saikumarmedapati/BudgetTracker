<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.user.entities.Transaction"%>
<%@ page import="com.DAO.TransactionDaoImpl"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Transactions</title>
    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom Styles -->
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="bg-light">

    <%
    TransactionDaoImpl transactionDao = new TransactionDaoImpl();
    Transaction totals = transactionDao.calculateTotals();

    double totalCredit = totals.getTotalCredit();
    double totalDebit = totals.getTotalDebit();
    double balance = totals.getBalance();
    %>

    <div class="container mt-5 col-md-8 border border-success rounded shadow p-4">
        <div class="card">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h3 class="mb-0">My Transactions</h3>
                <a href="generatereport.jsp" class="btn btn-light btn-sm">Generate Report</a>
            </div>
            <div class="card-body">
                <%
                TransactionDaoImpl transactionDao1 = new TransactionDaoImpl();
                List<Transaction> transactions = null;
                try {
                    transactions = transactionDao1.getAllTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                    transactions = new ArrayList<>();
                }

                if (transactions == null || transactions.isEmpty()) {
                %>
                <div class="alert alert-warning text-center" role="alert">
                    No transactions added yet.
                </div>
                <%
                } else {
                %>
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>Transaction ID</th>
                            <th>Header</th>
                            <th>Date</th>
                            <th>Credit</th>
                            <th>Debit</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        for (Transaction transaction : transactions) {
                            String transactionId = transaction.getTransactionId() != null ? transaction.getTransactionId() : "N/A";
                            String header = transaction.getHeader() != null ? transaction.getHeader() : "No Header";
                            String date = transaction.getDate() != null ? transaction.getDate() : "No Date";
                            double credit = transaction.getCredit() != 0.0 ? transaction.getCredit() : 0.0;
                            double debit = transaction.getDebit() != 0.0 ? transaction.getDebit() : 0.0;
                        %>
                        <tr>
                            <td><%=transactionId%></td>
                            <td><%=header%></td>
                            <td><%=date%></td>
                            <td><%=String.format("%.2f", credit)%></td>
                            <td><%=String.format("%.2f", debit)%></td>
                            <td class="text-center">
                                <a href="editdetails.jsp?id=<%=transactionId%>" class="btn btn-sm btn-primary">Edit</a>
                                <a href="delete-transaction-servlet?id=<%=transactionId%>" class="btn btn-sm btn-danger">Remove</a>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
                <%
                }
                %>
            </div>
        </div>

        <div class="card mt-4">
            <div class="card-header bg-secondary text-white">
                <h3 class="mb-0">Account Statement</h3>
            </div>
            <div class="card-body">
                <p class="mb-1">
                    <strong>Total Credit:</strong> <span id="totalCredit" class="text-success"><%=String.format("%.2f", totalCredit)%></span>
                </p>
                <p class="mb-1">
                    <strong>Total Debit:</strong> <span id="totalDebit" class="text-danger"><%=String.format("%.2f", totalDebit)%></span>
                </p>
                <p class="mb-1">
                    <strong>Balance:</strong> <span id="balance" class="<%=balance >= 0 ? "text-success" : "text-danger"%>"><%=String.format("%.2f", balance)%></span>
                </p>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
