<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.user.entities.Transaction"%>
<%@ page import="com.DAO.TransactionDaoImpl"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Manager</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mb-4">Transaction Manager</h2>
        <form id="transactionForm" class="mb-4">
            <h3>Add Transaction</h3>
            <div class="form-group">
                <label for="txnNumber">Txn#:</label>
                <input type="text" class="form-control" id="txnNumber" placeholder="Enter Transaction Number">
            </div>
            <div class="form-group">
                <label for="txnDate">Txn Date:</label>
                <input type="date" class="form-control" id="txnDate">
            </div>
            <div class="form-group">
                <label for="header">Header:</label>
                <input type="text" class="form-control" id="header" placeholder="Enter Transaction Header">
            </div>
            <div class="form-group">
                <label for="credit">Credit:</label>
                <input type="number" class="form-control" id="credit" placeholder="Enter Credit Amount">
            </div>
            <div class="form-group">
                <label for="debit">Debit:</label>
                <input type="number" class="form-control" id="debit" placeholder="Enter Debit Amount">
            </div>
            <button type="button" class="btn btn-primary" onclick="addTransaction()">Add Transaction</button>
        </form>

        <h3>Transaction List</h3>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Txn#</th>
                    <th>Date</th>
                    <th>Header</th>
                    <th>Credit</th>
                    <th>Debit</th>
                </tr>
            </thead>
            <tbody id="transactionTableBody">
                <!-- Transactions will be dynamically added here -->
            </tbody>
        </table>

        <h3>Account Statement</h3>
        <div>
            <p>Total Credit: <span id="totalCredit">0</span></p>
            <p>Total Debit: <span id="totalDebit">0</span></p>
            <p>Balance: <span id="balance">0</span></p>
        </div>
    </div>
    
</body>
</html>
