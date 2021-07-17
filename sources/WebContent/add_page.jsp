<%@page import="db.QueryCandidat"%>
<%@page import="db.QueryUniversitate"%>
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
        <title>Adauga | ADMITERE LA FACULTATE</title>
        <jsp:include page="common_head.jsp" />
  		<script src="scripts/add_page.js"></script>
  		<link rel="stylesheet" href="css/add_page.css">
  		<!-- ALERT -->
		<div class="alert alert-danger alert-dismissible fade show" id="myAlert" role="alert" style="z-index: 1; position: fixed; width:100%; display: none">
			<button type="button" id="close" class="close" aria-label="Close">
	  			<span aria-hidden="true">&times;</span>
			</button>
			<span id="alerttest">defaf</span>
		</div>
    </head>
    </head>
    <body>
        <jsp:include page="common_body.jsp" />
        
        <form action="AddPage" method="POST" id="addForm">
        	<fieldset class="datecandidatfieldset">
                    <legend class="datacandidatlegend">Date candidat</legend>
                    <div class="datecandidat">
                        <div class="numecandidat">
                        	<!-- NUME -->
                            <div class="nume">
                                <label for="nume">Nume</label>
                                <input type="text" id="nume" name="nume" placeholder="Popescu" class="form-control" minlength="3" maxlength="20" onkeypress="return numeFieldKeyPressed()" required>
                            </div>
                            <!-- INITIALA TATA -->
                            <div class="inittata">
                                <label for="inittata">Initiala tata</label>
                                <input type="text" id="inittata" name="inittata" placeholder="M" class="form-control" minlength="1" maxlength="1" onkeypress="return initTataFieldKeyPressed()" required>
                            </div>
                            <!-- PRENUME -->
	                        <div class="prenume">
	                            <label for="prenume" >Prenume</label>
	                            <input type="text" id="prenume" name="prenume" placeholder="Ion" class="form-control" minlength="3" maxlength="20" onkeypress="return prenumeFieldKeyPressed()" required>
	                        </div>
                        </div>
                        <div class="cnpsidatanastere">
	                        <!-- CNP -->
	                        <div class="cnp">
	                            <label for="cnp" >Cod Numeric Personal (CNP)</label>
	                            <input type="text" id="cnp" name="cnp" placeholder="1990101123456" class="form-control" minlength="13" maxlength="13" onkeypress="return cnpFieldKeyPressed()" required>
	                        </div>
	                        <!-- DATA NASTERE -->
	                        <div class="datanastere">
	                            <label for="datanastere">Data nastere</label>
	                            <input type="date" id="datanastere" name="datanastere" class="form-control" min="1900-01-01" max="<%=DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now().minus(18, ChronoUnit.YEARS)) %>" required>
	                        </div>
                        </div>
                        <div class="liceusimedii">
	                        <!-- LICEU ABSOLVIT -->
	                        <div class="liceu">
	                            <label for="liceuabsolvit">Liceu absolvit</label>
	                            <input type="text" id="liceuabsolvit" name="liceuabsolvit" class="form-control" list="licee" placeholder="Selectati liceul absolvit" minlength="10" maxlength="35" onkeypress="return liceuAbsolvitFieldKeyPressed()" required>
	                            <datalist id="licee">
	                                <%
	                                    List<String> licee = QueryCandidat.getLiceu();
	                               	
	                            		for (String liceu : licee) {
	                                		out.print("<option>" + liceu + "</option>");
	                            		}
									%>
	                            </datalist>
	                        </div>
                            <!-- MEDIE LICEU -->
                            <div class="medieliceu">
                                <label for="medieliceu" >Medie liceu</label>
                                <input type="number" id="medieliceu" name="medieliceu" class="form-control" min="5" max="10" step="0.01" value="5" required>
                            </div>
                            <!-- MEDIE BACALAUREAT -->
                            <div class="mediebac">
                                <label for="mediebac" >Medie bacalaureat</label>
                                <input type="number" id="mediebac" name="mediebac" class="form-control" min="5" max="10" step="0.01" value="5" required>
                            </div>
                        </div>
                    </div>
                </fieldset>
            <div class="optionssection">
                <fieldset class="optiunicandidatfieldset">
                    <legend class="optiunicandidatlegend">Optiuni candidat</legend>
                    <!-- OPTIUNE CANDIDAT -->
                    <div class="optiunicandidat">
                        <!-- UNIVERSITATE -->
                        <div class="universitate">
                            <label for="universitatecombobox">Universitate</label>
                            <select id="universitatecombobox" name="universitatecombobox" class="form-control">
                                <option selected>Selectati o universitate</option>
                             <%
                             	List<String> universitati = QueryUniversitate.getDenumire();
                            	
                             	for (String universitate : universitati) {
                             		out.print("<option>" + universitate + "</option>");
                             	}
                             %>
                            </select>
                        </div>
                        <!-- FACULTATE -->
                        <div class="facultate">
                            <label for="facultatecombobox">Facultate</label>
                            <select id="facultatecombobox" name="facultatecombobox" class="form-control">
                                <option selected>Selectati o facultate</option>
                            </select>
                        </div>
                        <!-- DOMENIU -->
                        <div class="domeniu">
                            <label for="domeniucombobox">Domeniu</label>
                            <select id="domeniucombobox" name="domeniucombobox" class="form-control">
                                <option selected>Selectati un domeniu</option>
                            </select>
                        </div>
                        <!-- SPECIALIZARE -->
                        <div class="specializare">
                            <label for="specializarecombobox">Specializare</label>
                            <select id="specializarecombobox" name="specializarecombobox" class="form-control">
                                <option selected>Selectati o specializare</option>
                            </select>
                        </div>
                        <!-- FORMA DE INVATAMANT -->
                        <div class="bugettaxa">
                            <label for="bugettaxacombobox">Forma de invatamant</label>
                            <select id="bugettaxacombobox" name="bugettaxacombobox" class="form-control">
                                <option selected>Selectati o forma de invatamant</option>
                            </select>
                        </div>
                        <!-- ADD BUTTON -->
                        <div class="addoption">
                            <button type="button" id="addoption" name="addoption" class="btn btn-success">ADAUGA</button>
                        </div>
                    </div>
                </fieldset>
                <!-- OPTIONS TABLE -->
                <div class="optionstable">
                    <table class="table table-striped table-sm" name="optionstable" id="optionstable">
                        <!-- TABLE HEADER -->
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">UNIVERSITATE</th>
                                <th scope="col">FACULTATE</th>
                                <th scope="col">DOMENIU</th>
                                <th scope="col">SPECIALIZARE</th>
                                <th scope="col">FORMA DE INVATAMANT</th>
                                <th scope="col">ACTIUNE</th>
                            </tr>
                        </thead>
                        <!-- TABLE BODY -->
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="submitbutton">
                <button type="submit" id="submit" name="submit" class="btn btn-primary">INSCRIE</button>
            </div>
        </form>
    </body>
</html>