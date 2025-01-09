<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.user.entities.Transaction"%>
<%@ page import="com.DAO.TransactionDaoImpl"%>
<!DOCTYPE html>
<html>
<head>
<title>Budget Tracker</title>
<link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>

	<%
	TransactionDaoImpl transactionDao = new TransactionDaoImpl();
	Transaction totals = transactionDao.calculateTotals(); // Calculate totals using the method

	double totalCredit = totals.getTotalCredit();
	double totalDebit = totals.getTotalDebit();
	double balance = totals.getBalance();
	%>

	<div class="container">
		<h1>Budget Tracker</h1>

		<button class="add-transaction-button"
			onclick="document.getElementById('transactionForm').style.display='block';">
			+ Add Transaction</button>

		<form id="transactionForm" action="add-transaction-servlet"
			method="post" style="display: none;">
			<input type="hidden" name="transactionId"
				value="<%=java.util.UUID.randomUUID().toString()%>"> <label
				for="header">Transaction Header:</label> <input type="text"
				id="header" name="header" required> <label for="date">Transaction
				Date:</label> <input type="date" id="date" name="date" required>
			<div class="radio-group">
				<label>Transaction Type:</label> <label><input type="radio"
					name="type" value="income" required> Income</label> <label><input
					type="radio" name="type" value="expense" required> Expense</label>
			</div>

			<label for="amount">Amount:</label> <input type="number" id="amount"
				name="amount" required>

			<button type="submit" class="btn btn-primary">Add
				Transaction</button>
		</form>

		<div id="transactionListContainer">
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
			<div id="noTransactionsBox" class="no-transactions-box">
				<div class="no-transactions" id="noTransactionsMessage">No
					transactions added</div>
			</div>
			<%
			} else {
			%>
			<table id="transactionTable">
				<thead>
					<tr>
						<th>Transaction ID</th>
						<th>Header</th>
						<th>Date</th>
						<th>Credit</th>
						<th>Debit</th>
						<!--  <th>Actions</th>-->
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
						<td><%=credit%></td>
						<td><%=debit%></td>
						<!--  <td class="actions">
							<button
								onclick="editTransaction('<%=transaction.getTransactionId()%>')">Edit</button>
							<button class="remove"
								onclick="removeTransaction('<%=transaction.getTransactionId()%>')">Remove</button>
						</td>-->
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
		<h3>Account Statement</h3>
		<div>
			<p>
				Total Credit: <span id="totalCredit"><%=String.format("%.2f", totalCredit)%></span>
			</p>
			<p>
				Total Debit: <span id="totalDebit"><%=String.format("%.2f", totalDebit)%></span>
			</p>
			<p>
				Balance: <span id="balance"><%=String.format("%.2f", balance)%></span>
			</p>
		</div>
	</div>

	<script src="script.js"></script>

</body>
</html>
