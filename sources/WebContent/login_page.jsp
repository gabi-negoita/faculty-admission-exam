<html>
	<head>     
		<jsp:include page="common_head.jsp" />
        <link rel="stylesheet" href="css/login_page.css">
        <title>Login | ADMITERE LA FACULTATE</title>
        
		<!-- ALERT -->
		<div class="alert alert-danger alert-dismissible fade show" role="alert" style="<%=session.getAttribute("loginErr") == null ? "display: none" : ""%>">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	  			<span aria-hidden="true">&times;</span>
			</button>
			<strong>Eroare!</strong> <%=session.getAttribute("loginErr") != null ? session.getAttribute("loginErr") : ""%>
		</div>
    </head>
    
    <body>
    	<div class="frame">
	        <div class="logocontainer">
	            <img src="resource/university.svg" alt="UNIVERSITY LOGO">            
	        </div>
	        <form action="LoginPage" method="POST">
	            <div class="container">
	                <div class="usernamecontainer">
	                    <input type="text" name="username" placeholder="Utilizator" class="form-control <%=session.getAttribute("loginErr") != null ? "is-invalid" : "" %>" value="<%=session.getAttribute("username") != null ? session.getAttribute("username") : "" %>" required>
	                </div>
	                <div class="passwordcontainer">
	                    <input type="password" name="password" placeholder="Parola" class="form-control <%=session.getAttribute("loginErr") != null ? "is-invalid" : "" %>" required>
	                </div>
	                <div class="button">
	                    <button type="submit" name="submit" class="btn btn-primary">CONECTEAZA-TE</button>
	                </div>
	                <div class="register">
						Nu esti membru? <a href="register_page.jsp">Inregistreaza-te</a>!
	                </div>
	            </div>
	        </form>
        </div>
    </body>
</html>