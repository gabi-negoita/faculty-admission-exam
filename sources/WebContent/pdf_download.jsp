<%
	String filename = "Results.pdf";
	String filepath = "C:\\Windows\\Temp\\";
	response.setContentType("application/attachment");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	
	java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + filename);
	
	int i;
	while ((i = fileInputStream.read()) != -1) {
		out.write(i);
	}
	fileInputStream.close();
%>