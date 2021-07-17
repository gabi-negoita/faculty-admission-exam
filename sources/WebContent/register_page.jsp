<%@page import="db.QueryUser"%>

<html>
    <head>     
		<jsp:include page="common_head.jsp" />
        <link rel="stylesheet" href="css/register_page.css">
        <title>Inregistrare | ADMITERE LA FACULTATE</title>
        
        <!-- ALERT -->
		<div class="alert alert-<%=session.getAttribute("registerErr") != null ? "danger" : "success"%> alert-dismissible fade show" role="alert" style="z-index: 1; <%=session.getAttribute("registerMsg") == null ? "display: none" : ""%>">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	  			<span aria-hidden="true">&times;</span>
			</button>
			<%=session.getAttribute("registerErr") != null ? "<strong>Eroare!</strong>" : ""%> <%=session.getAttribute("registerMsg") != null ? session.getAttribute("registerMsg") : ""%>
		</div>
    </head>
    </head>
    <body>
    	<div class="frame">
	        <div class="logocontainer">
	            <img src="resource/university.svg" alt="UNIVERSITY LOGO">            
	        </div>
	        <form action="RegisterPage" method="POST">
	            <div class="container">
	                <div class="lastnamecontainer">
	                    <input type="text" name="lastname" placeholder="Nume" class="form-control <%=session.getAttribute("registerErr") != null ? "is-invalid" : "" %>" value="<%=session.getAttribute("lastname") != null ? session.getAttribute("lastname") : "" %>" required>
	                </div>
	                <div class="firstnamecontainer">
	                    <input type="text" name="firstname" placeholder="Prenume" class="form-control <%=session.getAttribute("registerErr") != null ? "is-invalid" : "" %>" value="<%=session.getAttribute("firstname") != null ? session.getAttribute("firstname") : "" %>" required>
	                </div>
	                <div class="usernamecontainer">
	                    <input type="text" name="username" placeholder="Utilizator" class="form-control <%=session.getAttribute("registerErr") != null ? "is-invalid" : "" %>" value="<%=session.getAttribute("username") != null ? session.getAttribute("username") : "" %>" required>
	                </div>
	                <div class="passwordcontainer">
	                    <input type="password" name="password" placeholder="Parola" class="form-control  <%=session.getAttribute("registerErr") != null ? "is-invalid" : "" %>" required>
	                </div>
	                <div class="confirmpasswordcontainer">
	                    <input type="password" name="confirmpassword" placeholder="Confirmati parola" class="form-control  <%=session.getAttribute("registerErr") != null ? "is-invalid" : "" %>" required>
	                </div>
	                <div class="button">
	                    <button type="submit" name="submit" class="btn btn-primary">INREGISTREAZA-TE</button>
	                </div>
	                <div class="register">
						Ai deja cont? <a href="login_page.jsp">Conecteaza-te</a>!
	                </div>
	            </div>
	        </form>
        </div>
    </body>
</html>