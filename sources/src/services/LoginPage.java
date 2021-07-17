package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.QueryUser;

@WebServlet("/LoginPage")
public class LoginPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession session = request.getSession();

	// Redirecting to main_page if username and password are valid
	String username = request.getParameter("username");
	String password = request.getParameter("password");

	if (username != null && password != null) {
	    // Checking if passwords match
	    session.setAttribute("username", username);

	    String actualPassword = QueryUser.getPasswordByUsername(request.getParameter("username"));

	    if (actualPassword != null && password.equals(actualPassword) == true) {

		session.removeAttribute("loginErr");

		// Setting up session attributes and redirecting the user
		session.setAttribute("firstname", QueryUser.getFirstnameByUsername(username));
		session.setAttribute("lastname", QueryUser.getLastnameByUsername(username));
		session.setAttribute("role", QueryUser.getRoleByUsername(username));

		response.sendRedirect("main_page.jsp");
	    } else {
		session.setAttribute("loginErr", "Nume sau parola incorecte!");
		response.sendRedirect("login_page.jsp");
	    }
	}
    }

}
