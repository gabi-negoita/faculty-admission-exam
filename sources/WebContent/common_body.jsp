<%
	String current_path = request.getRequestURI();
	String current_page = current_path.substring(current_path.lastIndexOf("/") + 1, current_path.length() - 4);
%>

<!-- IMAGINE LOGO + NUME -->
<div class="header">
	<a href="main_page.jsp">
	    <div class="logocontainer">
			<img src="resource/university.svg" alt="UGAL" class="logo">
	    </div>
    </a>
    <form action="" method="get">
        <div class="user">
            <div class="btn-group">
                <button type="button" class="btn btn-outline-info" style="pointer-events: none; color: black; border: 1px solid #C3D6FB">
                	<b>
                		<%=session.getAttribute("lastname").toString().charAt(0) + ". " + session.getAttribute("firstname").toString().charAt(0) + session.getAttribute("firstname").toString().substring(1).toLowerCase() %>
                	</b>
                </button>
                <button type="button" class="btn btn-outline-info" style="pointer-events: none; color: gray; border: 1px solid #C3D6FB">
                	<%=session.getAttribute("role") %>
                </button>
                <button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                </button>
                <div class="dropdown-menu" style="margin-right: 5px; padding-left: 0px; padding-right: 0px; min-width: 0px;">
                    <div class="logoutbutton">
                        <button class="dropdown-item" type="submit" name="logout">Deconectati-va</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<form action="" method="get">
	<div class="tab">
	    <!-- BUTON VIZUALIZEAZA -->
	    <button class="<%=current_page.equals("search_page") ? "btn btn-info" : "btn btn-outline-info" %>" type="submit" name="to_search_page">
	    	<img src="resource/search.svg" title="Cauta (search)">
			<p>Cauta</p>
	    </button>
	    <%
	        if (session.getAttribute("role").equals("CANDIDAT") == false) 
	        {
	    %>
	    <!-- BUTON ADAUGA -->
	    <button class="<%=current_page.equals("add_page") ? "btn btn-info" : "btn btn-outline-info" %>" type="submit" name="to_add_page">
	    	<img src="resource/add.svg" title="Adauga (add)">
			<p>Adauga</p>
		</button>
	    <!-- BUTON ACTUALIZEAZA -->
	    <button class="<%=current_page.equals("update_page") ? "btn btn-info" : "btn btn-outline-info" %>" type="submit" name="to_update_page">
	    	<img src="resource/update.svg" title="Actualizeaza (update)">
			<p>Actualizeaza</p>
		</button>
	    <!-- BUTON REZULTATE -->
	    <button class="<%=current_page.equals("results_page") ? "btn btn-info" : "btn btn-outline-info" %>" type="submit" name="to_results_page">
	    	<img src="resource/results.svg" title="Rezultate (Results)">
			<p>Rezultate</p>
		</button>
	    <%
	        }
	    %>
	    <%
	        if (session.getAttribute("role").equals("ADMINISTRATOR"))
	        {
	    %>
	    <!-- BUTON LOG-uri -->
	    <button class="<%=current_page.equals("logs_page") ? "btn btn-info" : "btn btn-outline-info" %>" type="submit" name="to_logs_page">
	    	<img src="resource/logs.svg" title="Log-uri (logs)">
			<p>Log-uri</p>
		</button>
	    <%
	        }
	    %>
	</div>
</form>