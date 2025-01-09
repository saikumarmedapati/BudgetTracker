package com.user.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private String transactionId;
	private String header;
	private String date;
	private double credit;
	private double debit;
	
	private double totalCredit;
    private double totalDebit;
    private double balance;

	// Constructors, Getters, and Setters
	

	public String getTransactionId() {
		return transactionId;
	}

	public Transaction(String transactionId, String header, String date, double credit, double debit,
			double totalCredit, double totalDebit, double balance) {
		super();
		this.transactionId = transactionId;
		this.header = header;
		this.date = date;
		this.credit = credit;
		this.debit = debit;
		this.totalCredit = totalCredit;
		this.totalDebit = totalDebit;
		this.balance = balance;
	}

	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
	}

	public double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(double totalDebit) {
		this.totalDebit = totalDebit;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
