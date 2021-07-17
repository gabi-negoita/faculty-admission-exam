<%@page import="db.QueryCandidat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.util.List"%>

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
        <title>Actualizeaza | ADMITERE LA FACULTATE</title>
		<jsp:include page="common_head.jsp" />
  		<script src="scripts/update_page.js"></script>
  		<link rel="stylesheet" href="css/update_page.css">
  		
  		<!-- ALERT -->
		<div class="alert alert-danger alert-dismissible fade show" id="myAlert" role="alert" style="z-index: 1; position: fixed; width:100%; display: none">
			<button type="button" id="close" class="close" data-dismiss="alert" aria-label="Close">
	  			<span aria-hidden="true">&times;</span>
			</button>
			<span id="alerttest">defaf</span>
		</div>
    </head>
    
    <body>
       	<jsp:include page="common_body.jsp" />
       	
       	<!-- Actual page body -->
        <form action="UpdatePage" method="GET">
	        <div class="tableandinfo">
	        	<div class="searchandtable">
		            <div class="searchcontainer">
		            	<input class="form-control" type="text" name="searchfield" id="searchfield" placeholder="Cauta dupa nume sau cnp...">
		            </div>
		            <!-- CANDIDATI TABLE -->
		            <div class="candidatitable">
		                <table class="table table-striped table-sm table-hover" id="candidatitable">
		                    <!-- TABLE HEADER -->
		                    <thead class="thead-dark">
		                        <tr>
		                            <th scope="col">#</th>
		                            <th scope="col">NUME</th>
		                            <th scope="col">CNP</th>
		                        </tr>
		                    </thead>
		                    <!-- TABLE BODY -->
		                    <tbody></tbody>
		                </table>
		            </div>
	            </div>
	            <!-- UPDATE DATA -->
	            <div class="updatedata">
	                <div class="dateandexams">
	                    <!-- DATE CANDIDAT -->
	                    <fieldset class="datecandidatfieldset">
	                    <legend class="datacandidatlegend">Date candidat</legend>
	                    <div class="datecandidat">
	                        <div class="numecomplet">
	                            <!-- NUME -->
	                            <div class="nume">
	                                <label for="nume">Nume</label>
	                                <input type="text" id="nume" name="nume" placeholder="Popescu" class="form-control" minlength="3" maxlength="20"  onkeypress="return numeFieldKeyPressed()" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                            <!-- INITIALA TATA -->
	                            <div class="inittata">
	                                <label for="inittata">Initiala tata</label>
	                                <input type="text" id="inittata" name="inittata" placeholder="M" class="form-control" minlength="1" maxlength="1" onkeypress="return initTataFieldKeyPressed()" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                            <!-- PRENUME -->
	                            <div class="prenume">
	                                <label for="prenume" >Prenume</label>
	                                <input type="text" id="prenume" name="prenume" placeholder="Ion" class="form-control" minlength="3" maxlength="20" onkeypress="return prenumeFieldKeyPressed()" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                        </div>
	                        <div class="cnpsidatanastere">
	                            <!-- CNP -->
	                            <div class="cnp">
	                                <label for="cnp" >Cod Numeric Personal (CNP)</label>
	                                <input type="text" id="cnp" name="cnp" placeholder="1990101123456" class="form-control" minlength="13" maxlength="13" onkeypress="return cnpFieldKeyPressed()" required disabled>
	                            </div>
	                            <!-- DATA NASTERE -->
	                            <div class="datanastere">
	                                <label for="datanastere">Data nastere</label>
	                                <input type="date" id="datanastere" name="datanastere" class="form-control" min="1900-01-01" max="<%=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now().minus(18, ChronoUnit.YEARS)) %>" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                        </div>
	                        <div class="liceusimedie">
	                            <!-- LICEU ABSOLVIT -->
	                            <div class="liceu">
	                                <label for="liceuabsolvit">Liceu absolvit</label>
	                                <input type="text" id="liceuabsolvit" name="liceuabsolvit" class="form-control" minlength="10" maxlength="35" list="licee" placeholder="Selectati liceul absolvit" onkeypress="return liceuAbsolvitFieldKeyPressed()" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                                <datalist id="licee">
	                                    <%
	                                    	List<String> den_liceu = QueryCandidat.getLiceu();
	                                    	for (String liceu : den_liceu) {
	                                    	    out.print("<option>" + liceu + "</option>");
	                                    	}
	                                    %>
	                                </datalist>
	                            </div>
	                            <!-- MEDIE LICEU -->
	                            <div class="medieliceu">
	                                <label for="medieliceu" >Medie liceu</label>
	                                <input type="number" id="medieliceu" name="medieliceu" class="form-control" min="5" max="10" step="0.01" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                            <!-- MEDIE BACALAUREAT -->
	                            <div class="mediebac">
	                                <label for="mediebac" >Medie bacalaureat</label>
	                                <input type="number" id="mediebac" name="mediebac" class="form-control" min="5" max="10" step="0.01" required <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>
	                            </div>
	                        </div>
	                    </div>
	                    </fieldset>
	                    <!-- EXAMS TABLE -->
	                    <div class="exams">
	                        <div class="examstable">
	                            <table class="table table-striped table-sm table-hover" id="examstable">
	                                <!-- TABLE HEADER -->
	                                <thead class="thead-dark">
	                                    <tr>
	                                        <th scope="col">#</th>
	                                        <th scope="col">EXAMEN</th>
	                                        <th scope="col">LOCATIE</th>
	                                        <th scope="col">DATA</th>
	                                        <th scope="col">NOTA</th>
	                                    </tr>
	                                </thead>
	                                <!-- TABLE BODY -->
	                                <tbody>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
			            <div class="deleteandsubmit">
				            <div class="submitbutton">
				                <button type="submit" id="update" name="update" class="btn btn-primary">ACTUALIZEAZA</button>
				            </div>
				            <div class="deletebutton">
				                <button type="button" id="delete" name="delete" class="btn btn-danger" <%=session.getAttribute("role").equals("ADMINISTRATOR") == false ? "disabled" : "" %>>STERGE</button>
				            </div>
				        </div>
                    </div>
                </div>
            </div>          
	    </form>
    </body>
</html> 
