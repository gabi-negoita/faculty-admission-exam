<%@page import="db.QueryCandidat"%>
<%@page import="java.util.List"%>

<%
	// Redirect to login_page if user tries to enter the page without logging in
	if (session.getAttribute("username") == null ||
		session.getAttribute("firstname") == null ||
		session.getAttribute("lastname") == null ||
		session.getAttribute("role") == null) {
	    
	    response.sendRedirect("login_page.jsp");
	    return;
	}
	
	// Logging out
	if (request.getParameter("logout") != null) {
	    session.invalidate();
	    
	    response.sendRedirect("login_page.jsp");
	    return;
	}
	
	// Redirecting to specific page based on the button pressed
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
        <title>Cauta | ADMITERE LA FACULTATE</title>
        <jsp:include page="common_head.jsp" />
        <script src="scripts/search_page.js"></script>
        <link rel="stylesheet" href="css/search_page.css">
    </head>
    <body>
        <jsp:include page="common_body.jsp" />
        
        <form action="SearchPage" method="GET">
            <div class="searchfilter">
                <div class="searchfield">
                    <!-- FIELD CAUTARE -->
                    <input class="form-control" type="text" name="searchfield" id="searchfield" placeholder="Cauta dupa nume..." required>
                </div>
                <!-- COMBOBOX -->
                <div class="combobox">
                    <select name="combobox" class="form-control" id="combobox">
                        <option>Candidat</option>
                        <option>Universitate</option>
                        <option>Facultate</option>
                        <option>Domeniu</option>
                        <option>Specializare</option>
                    </select>
                </div>
                <!-- LICEU ABSOLVIT -->
                <div class="liceucombobox">
                    <select name="liceucombobox" id="liceucombobox" class="form-control">
                        <option value="">Selectati un liceu</option>
                        <%
                            List<String> licee = QueryCandidat.getLiceu();
                              	for (String liceu : licee) {
                              	    out.print("<option>" + liceu + "</option>");
                              	}
                            %>
                    </select>
                </div>
            </div>
        </form>
        <!-- RESULTS TABLE -->
        <div class="resultstable">
            <table class="table table-striped table-sm table-hover" name="resultstable" id="resultstable">
                <!-- TABLE HEADER -->
                <thead class="thead-dark">
                </thead>
                <!-- TABLE BODY -->
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="resultslabel" id="resultslabel" name="resultslabel">Rezultate gasite: </i><b>0</b></div>
    </body>
</html>