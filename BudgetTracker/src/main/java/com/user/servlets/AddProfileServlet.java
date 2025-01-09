package com.user.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DB.Connector;

/**
 * Servlet implementation class AddProfileServlet
 */
@WebServlet("/add-profile-servlet")
public class AddProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String fullName = request.getParameter("fullName");
        String mobile = request.getParameter("mobile");

        try (Connection connection = Connector.getCon()) {
            String sql = "INSERT INTO UserProfile (accountNumber, fullName, mobile) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ps.setString(2, fullName);
            ps.setString(3, mobile);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to index.jsp after saving the details
        response.sendRedirect("index.jsp");
    }

}
