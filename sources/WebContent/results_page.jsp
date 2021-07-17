<%@page import="java.util.List"%>
<%@page import="db.QuerySpecializare"%>
	
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
        <title>Rezultate | ADMITERE LA FACULTATE</title>
		<jsp:include page="common_head.jsp" />
    </head>
    
    <body>
       	<jsp:include page="common_body.jsp" />
  		<script src="scripts/results_page.js"></script>
  		<link rel="stylesheet" href="css/results_page.css">
       	<!-- Actual page body -->
        <form action="" method="get">
	        <div class="results">
	        	<div class="controlsandresults">
					<div class="controls">
			            <!-- COMBOBOX -->
			            <div class="combobox">
			                <select name="combobox" class="form-control" id="combobox">
			                	<option>Selectati o specializare</option>
			                	<%
			                		List<String> den_s = QuerySpecializare.getDenumireSorted();
			                		for (int i = 0; i < den_s.size(); i++) {
			                			out.print("<option>" + den_s.get(i) + "</option>");
			                		}
			                	%>
			                </select>
			            </div>
			            <div class="exportbuttons">
			                <div class="exportpdf">
			                    <div class="btn-group">
			                        <button type="button" class="btn btn-outline-danger" style="pointer-events: none; color: black;"><b>PDF</b></button>
			                        <button class="btn btn-outline-danger" name="pdfexport" id="pdfexport"">
			                            <img src="resource/download.svg" title="Descarca fisier PDF (Download PDF file)">
			                        </button>
			                    </div>
			                </div>
			                <div class="exportxls">
			                    <div class="btn-group">
			                        <button type="button" class="btn btn-outline-success" style="pointer-events: none; color: black; overflow: hidden;"><b>XLS</b></button>
			                        <button class="btn btn-outline-success" name="xlsexport" id="xlsexport"">
										<img src="resource/download.svg" title="Descarca fisier XLS (Download XLS file)">
			                        </button>
			                    </div>
			                </div>
			            </div>
			        </div>
			        <!-- RESULTS TABLE -->
		            <div class="resultstable">
		            	<table class="table table-striped table-sm table-hover" id="resultstable">
		                    <!-- TABLE HEADER -->
		                    <thead class="thead-dark">
		                        <tr>
		                            <th scope="col">#</th>
		                            <th scope="col">NUME</th>
		                            <th scope="col">MEDIE CONCURS</th>
		                            <th scope="col">SPECIALIZARE</th>
		                            <th scope="col">FORMA DE FINANTARE</th>
		                            <th scope="col">REZULTAT</th>
		                        </tr>
		                    </thead>
		                    <!-- TABLE BODY -->
		                    <tbody>
		                    </tbody>
		                </table>
		            </div>
			     </div>
	             <!-- RESULTS PIE CHART -->
	             <div class="piechart" name="piechart" id="piechart"></div>
	        </div>
        </form>     
    </body>
</html> 
