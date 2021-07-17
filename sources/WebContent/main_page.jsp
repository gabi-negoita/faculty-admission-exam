<%@page import="db.QueryUser"%>

<%
	/* Redirect to login_page if user tries to enter the page without logging in */
	if (session.getAttribute("username") == null ||
		session.getAttribute("firstname") == null ||
		session.getAttribute("lastname") == null ||
		session.getAttribute("role") == null) {
	    
	    response.sendRedirect("login_page.jsp");
	    return;
	}
	
	/* Logging out */
	if (request.getParameter("logout") != null) {
	    session.invalidate();
	    
	    response.sendRedirect("login_page.jsp");
	    return;
	}
	
	/* Redirecting to specific page based on the button pressed */
	if (request.getParameter("to_search_page") != null) {
	
		response.sendRedirect("search_page.jsp");
		return;
	}
	
	if (request.getParameter("to_add_page") != null) {
	
		response.sendRedirect("add_page.jsp");
		return;
	}
	
	if (request.getParameter("to_update_page") != null) {
	
		response.sendRedirect("update_page.jsp");
		return;
	}
	
	if (request.getParameter("to_results_page") != null) {
	
		response.sendRedirect("results_page.jsp");
		return;
	}
	
	if (request.getParameter("to_logs_page") != null) {
	
		response.sendRedirect("logs_page.jsp");
		return;
	}
%>

<html>
    <head>
        <title>Acasa | ADMITERE LA FACULTATE</title>
		<jsp:include page="common_head.jsp" />
		<link rel="stylesheet" href="css/main_page.css">
    </head>
    
    <body>
       	<jsp:include page="common_body.jsp" />
       	
       	<!-- Actual page body -->
        <div class="main_page">
        	Buna ziua, 
        	<b><%=QueryUser.getLastnameByUsername(session.getAttribute("username").toString()) + " " + QueryUser.getFirstnameByUsername(session.getAttribute("username").toString()) %></b>!
        </div>
    </body>
</html> 
