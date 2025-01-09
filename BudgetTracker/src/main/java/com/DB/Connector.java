package com.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	public static Connection con = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet res = null;
	
	private static String url = "jdbc:mysql://localhost:3306/budgettracker_db";
	private static String un = "root";
	private static String pwd = "root";
	
	
	public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,un,pwd);
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static void main(String[] args) {
//    	con = Connector.getCon();
//		
//		if (con != null) {
//            System.out.println("Connection successful!");
//        } else {
//            System.out.println("Failed to make connection.");
//        }
//	}
}
