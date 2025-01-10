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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>

	<%
	TransactionDaoImpl transactionDao = new TransactionDaoImpl();
	Transaction totals = transactionDao.calculateTotals(); // Calculate totals using the method

	double totalCredit = totals.getTotalCredit();
	double totalDebit = totals.getTotalDebit();
	double balance = totals.getBalance();
	%>

	<div
		class="container col-md-4 mt-5 border border-success rounded shadow p-4">
		<h1>Budget Tracker</h1>
		<br>

		<button class="add-transaction-button"
			onclick="document.getElementById('transactionForm').style.display='block';">
			+ Add Transaction</button>

		<form id="transactionForm" action="add-transaction-servlet"
			method="post" style="display: none;">
			<div class="mb-3">
				<input type="hidden" name="transactionId"
					value="<%=java.util.UUID.randomUUID().toString()%>"> <label
					for="header" class="form-label">Transaction Header:</label> <input
					type="text" id="header" name="header" class="form-control" required>
			</div>

			<div class="mb-3">
				<label for="date" class="form-label">Transaction Date:</label> <input
					type="date" id="date" name="date" class="form-control" required>
			</div>

			<div class="mb-3">
				<label class="form-label">Transaction Type:</label>
				<div class="form-check form-check-inline">
					<input type="radio" name="type" value="income"
						class="form-check-input" required> <label
						class="form-check-label">Income</label>
				</div>
				<div class="form-check form-check-inline">
					<input type="radio" name="type" value="expense"
						class="form-check-input" required> <label
						class="form-check-label">Expense</label>
				</div>
			</div>

			<div class="mb-3">
				<label for="amount" class="form-label">Amount:</label> <input
					type="number" id="amount" name="amount" class="form-control"
					required>
			</div>

			<button type="submit" class="btn btn-primary">Add
				Transaction</button>
		</form>
	</div>

	<div class="d-flex justify-content-center align-items-center"
		style="min-height: 10vh;">
		<button type="button" class="btn btn-primary shadow"
			style="padding: 6px 12px; font-size: 14px; height: 40px;"
			data-target="mytransactions.jsp"
			onclick="window.location.href='mytransactions.jsp'">My
			Transactions</button>
	</div>





	<script src="script.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
