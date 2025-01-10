<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.DAO.TransactionDaoImpl" %> <!-- Replace with your actual package -->
<%@ page import="com.user.entities.Transaction" %> <!-- Replace with your actual package -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Transaction</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body {
        background-color: #f8f9fa;
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    .btn-primary {
        background-color: #007bff;
        border-color: #007bff;
    }
    .form-group label {
        font-weight: bold;
    }
</style>
</head>
<body>
<%
    // Fetch transaction by ID
    String transactionId = request.getParameter("id");
    Transaction transaction = null;

    if (transactionId != null && !transactionId.isEmpty()) {
        TransactionDaoImpl transactionDAO = new TransactionDaoImpl();
        transaction = transactionDAO.getTransactionById(transactionId);
    }
%>
    <div class="container col-md-4 border border-dark rounded shadow p-4">
        <h2 class="text-center mb-4 text-success">Edit Transaction</h2>
        <form action="edit-transaction-servlet" method="post"> <!-- Set the action to your update handler -->
            <!-- Hidden ID -->
            <input type="hidden" name="id" value="<%= transaction != null ? transaction.getTransactionId() : "" %>">
            
            <!-- Transaction Number -->
            <div class="mb-3">
                <label for="txnNumber" class="form-label fw-bold">#Txn id</label>
                <input name="id" type="text" class="form-control" id="txnNumber" 
                       value="<%= transaction != null ? transaction.getTransactionId() : "" %>" readonly>
            </div>
            
            <!-- Header -->
            <div class="mb-3">
                <label for="header" class="form-label fw-bold">Header</label>
                <input name="header" type="text" class="form-control" id="header" 
                       value="<%= transaction != null ? transaction.getHeader() : "" %>">
            </div>
            
            <!-- Date -->
            <div class="mb-3">
                <label for="date" class="form-label fw-bold">Date</label>
                <input name="date" type="date" class="form-control" id="date" 
                       value="<%= transaction != null ? transaction.getDate() : "" %>">
            </div>
            
            <!-- Credit and Debit -->
            <div class="mb-3">
                <label for="credit" class="form-label fw-bold">Credit</label>
                <input name="credit" type="number" class="form-control" id="credit" 
                       value="<%= transaction != null ? transaction.getCredit() : "" %>">
            </div>
            <div class="mb-3">
                <label for="debit" class="form-label fw-bold">Debit</label>
                <input name="debit" type="number" class="form-control" id="debit" 
                       value="<%= transaction != null ? transaction.getDebit() : "" %>">
            </div>
            
            <!-- Submit Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Update</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
