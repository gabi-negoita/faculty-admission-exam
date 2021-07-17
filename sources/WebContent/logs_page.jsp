<%@page import="db.QueryLog"%>

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
        <title>Log-uri | ADMITERE LA FACULTATE</title>
		<jsp:include page="common_head.jsp" />
    </head>
    
    <body>
       	<jsp:include page="common_body.jsp" />
       	<script src="scripts/logs_page.js"></script>
  		<link rel="stylesheet" href="css/logs_page.css">
       	<!-- Actual page body -->
       	<div class="searchfilter">
                <div class="searchfield">
                    <!-- FIELD CAUTARE -->
                    <input class="form-control" type="text" name="searchfield" id="searchfield" placeholder="Cauta dupa nume utilizator...">
                </div>
                <!-- ROL COMBOBOX -->
                <div class="rolcombobox">
                    <select name="rolcombobox" class="form-control" id="rolcombobox">
                        <option>Selectati un rol</option>
                        <option>ADMINISTRATOR</option>
                        <option>MANAGER</option>
                        <option>CANDIDAT</option>
                    </select>
                </div>
                <!-- ACTIUNE COMBOBOX -->
                <div class="actiunecombobox">
                    <select name="actiunecombobox" class="form-control" id="actiunecombobox">
                        <option>Selectati o actiune</option>
                        <option>SELECT</option>
                        <option>INSERT</option>
                        <option>UPDATE</option>
                        <option>DELETE</option>
                    </select>
                </div>
                <!-- DATA -->
                <div class="data">
                    <input type="date" id="data" name="data" class="form-control" min="<%=QueryLog.getMinTime().substring(0, 10) %>" max="<%=QueryLog.getMaxTime().substring(0, 10) %>" required>
                </div>
		</div>
		<div class="logstable">
	        <table class="table table-striped table-sm table-hover" id="logstable">
	            <!-- TABLE HEADER -->
	            <thead class="thead-dark">
	                <tr>
	                    <th scope="col">#</th>
	                    <th scope="col">UTILIZATOR</th>
	                    <th scope="col">ROL</th>
	                    <th scope="col">DATA SI ORA</th>
	                    <th scope="col">ACTIUNE</th>
	                    <th scope="col">QUERY</th>
	                </tr>
	            </thead>
	            <!-- TABLE BODY -->
	            <tbody>
	            </tbody>
	        </table>
	    </div>
	    <div class="resultslabel" id="resultslabel" name="resultslabel">Rezultate gasite: </i><b>0</b></div>
    </body>
</html> 
