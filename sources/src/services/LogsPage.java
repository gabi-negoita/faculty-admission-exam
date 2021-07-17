package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.QueryLog;
import db.QueryUser;

@WebServlet("/LogsPage")
public class LogsPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PrintWriter out = response.getWriter();

	if (request.getParameter("filllogs") != null && request.getParameter("searchfield") != null
		&& request.getParameter("rolcombobox") != null && request.getParameter("actiunecombobox") != null
		&& request.getParameter("date") != null) {

	    List<Integer> cod_u = QueryLog.getCodUser(request.getParameter("date"),
		    request.getParameter("actiunecombobox"));
	    List<String> time = QueryLog.getTime(request.getParameter("date"), request.getParameter("actiunecombobox"));
	    List<String> action = QueryLog.getAction(request.getParameter("date"),
		    request.getParameter("actiunecombobox"));
	    List<String> query = QueryLog.getQuery(request.getParameter("date"),
		    request.getParameter("actiunecombobox"));

	    int index = 0;
	    for (int i = 0; i < cod_u.size(); i++) {
		String username = QueryUser.getUsernameByCod(cod_u.get(i));
		String role = QueryUser.getRoleByUsername(username);

		if (username.contains(request.getParameter("searchfield")) == false) {
		    continue;
		}

		if (role.contains(request.getParameter("rolcombobox")) == false) {
		    continue;
		}

		out.print("<tr>");

		out.print("<td><b>" + (index + 1) + "</b></td>");
		out.print("<td>" + username + "</td>");
		out.print("<td>" + role + "</td>");
		out.print("<td>" + time.get(i) + "</td>");
		out.print("<td>" + action.get(i) + "</td>");
		out.print("<td>" + query.get(i) + "</td>");

		out.print("</tr>");
		index++;
	    }
	    return;
	}
    }

}
