package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.QueryUser;

@WebServlet("/RegisterPage")
public class RegisterPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	HttpSession session = request.getSession();

	if (request.getParameter("submit") != null) {
	    if (request.getParameter("firstname") != null && request.getParameter("lastname") != null
		    && request.getParameter("username") != null && request.getParameter("password") != null
		    && request.getParameter("confirmpassword") != null) {

		session.setAttribute("firstname", request.getParameter("firstname"));
		session.setAttribute("lastname", request.getParameter("lastname"));
		session.setAttribute("username", request.getParameter("username"));

		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmpassword");

		// Checking if passwords match
		if (password.equals(confirmPassword)) {
		    // Checking for username duplicates
		    if (QueryUser.hasDuplicates(session.getAttribute("username").toString()) == false) {
			// Inserting user
			QueryUser.insert(session.getAttribute("firstname").toString().toUpperCase(),
				session.getAttribute("lastname").toString().toUpperCase(),
				session.getAttribute("username").toString(), password, "CANDIDAT");

			session.removeAttribute("registerErr");
			session.removeAttribute("firstname");
			session.removeAttribute("lastname");
			session.removeAttribute("username");

			session.setAttribute("registerMsg", "V-ati inregistrat cu succes!");

			response.sendRedirect("register_page.jsp");
		    } else {
			session.setAttribute("registerErr", "");
			session.setAttribute("registerMsg", "Utilizatorul exista deja.");
			response.sendRedirect("register_page.jsp");
		    }

		} else {
		    session.setAttribute("registerErr", "");
		    session.setAttribute("registerMsg", "Parolele introduse nu se potrivesc.");
		    response.sendRedirect("register_page.jsp");
		}
	    }
	}
    }

}
