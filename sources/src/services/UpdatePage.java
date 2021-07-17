package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.QueryCandidat;
import db.QueryExamen;
import db.QueryLog;
import db.QueryOptiuneCandidat;
import db.QueryRezultat;
import db.QueryUser;

@WebServlet("/UpdatePage")
public class UpdatePage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession();

	// Filling candidati table
	if (request.getParameter("numecandidat") != null) {

	    List<String> numeComplet = QueryCandidat.getNumeCompletSorted(request.getParameter("numecandidat"));
	    List<String> cnp = QueryCandidat.getCNPSorted(request.getParameter("numecandidat"));

	    for (int i = 0; i < numeComplet.size(); i++) {
		out.print("<tr>");

		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + numeComplet.get(i) + "</td>");
		out.print("<td>" + cnp.get(i) + "</td>");

		out.print("</tr>");
	    }

	    if (request.getParameter("numecandidat").equals("") == false) {
		/* Logging the operation */
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Candidat or CNP] => Parameters: [Keyword]: \""
			+ request.getParameter("numecandidat") + "\"";
		QueryLog.insert(cod_u, time, action, query);
	    }
	    return;
	}

	// Filling nume field
	if (request.getParameter("cnp_pentru_nume") != null) {
	    out.print(QueryCandidat.getNumeByCNP(request.getParameter("cnp_pentru_nume")));
	    return;
	}

	// Filling initiala tata field
	if (request.getParameter("cnp_pentru_inittata") != null) {
	    out.print(QueryCandidat.getInitialaTataByCNP(request.getParameter("cnp_pentru_inittata")));
	    return;
	}

	// Filling prenume field
	if (request.getParameter("cnp_pentru_prenume") != null) {
	    out.print(QueryCandidat.getPrenumeByCNP(request.getParameter("cnp_pentru_prenume")));
	    return;
	}

	// Filling cnp field
	if (request.getParameter("cnp_pentru_cnp") != null) {
	    out.print(request.getParameter("cnp_pentru_cnp"));
	    return;
	}

	// Filling data nastere field
	if (request.getParameter("cnp_pentru_datanastere") != null) {
	    out.print(QueryCandidat.getDataNastereByCNP(request.getParameter("cnp_pentru_datanastere")));
	    return;
	}

	// Filling liceu absolvit field
	if (request.getParameter("cnp_pentru_liceuabsolvit") != null) {
	    out.print(QueryCandidat.getLiceuByCNP(request.getParameter("cnp_pentru_liceuabsolvit")));
	    return;
	}

	// Filling medie liceu field
	if (request.getParameter("cnp_pentru_medieliceu") != null) {
	    out.print(QueryCandidat.getMedieLiceuByCNP(request.getParameter("cnp_pentru_medieliceu")));
	    return;
	}

	// Filling medie bac field
	if (request.getParameter("cnp_pentru_mediebac") != null) {
	    out.print(QueryCandidat.getMedieBacByCNP(request.getParameter("cnp_pentru_mediebac")));
	    return;
	}

	// Filling examene table
	if (request.getParameter("cnp_pentru_examstable") != null) {
	    List<String> den_e = QueryExamen.getDenumireByCNP(request.getParameter("cnp_pentru_examstable"));
	    List<String> locatie = QueryExamen.getLocatieByCNP(request.getParameter("cnp_pentru_examstable"));
	    List<String> data = QueryExamen.getDataByCNP(request.getParameter("cnp_pentru_examstable"));

	    for (int i = 0; i < den_e.size(); i++) {
		double nota = QueryRezultat.getNotaByDenumireExamenAndCNP(den_e.get(i),
			request.getParameter("cnp_pentru_examstable"));

		out.print("<tr>");

		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + den_e.get(i) + "</td>");
		out.print("<td>" + locatie.get(i) + "</td>");
		out.print("<td>" + data.get(i) + "</td>");
		out.print(
			"<td><input type='number' id='nota' name='nota' class='form-control' min='0' max='10' step='0.01' value=\""
				+ nota + "\"></td>");

		out.print("</tr>");
	    }
	    return;
	}

	// Deleting candidat
	if (request.getParameter("cnp_pentru_delete") != null) {
	    String cnp = request.getParameter("cnp_pentru_delete");
	    QueryRezultat.deleteByCNP(cnp);
	    QueryOptiuneCandidat.deleteByCNP(cnp);
	    QueryCandidat.deleteByCNP(cnp);

	    /* Logging the operation */
	    int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
	    String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
	    String action = "DELETE";
	    String query = "[Deleted Candidat] => Parameters: [CNP]: \"" + cnp + "\"";
	    QueryLog.insert(cod_u, time, action, query);

	    return;
	}

	// Updating candidat info
	if (request.getParameter("formsubmit") != null) {
	    boolean ready = true;

	    if (request.getParameter("nume") == null || request.getParameter("inittata") == null
		    || request.getParameter("prenume") == null || request.getParameter("cnp") == null
		    || request.getParameter("datanastere") == null || request.getParameter("liceuabsolvit") == null
		    || request.getParameter("medieliceu") == null || request.getParameter("mediebac") == null
		    || request.getParameter("numeexamen") == null || request.getParameter("notaexamen") == null) {

		ready = false;
	    }

	    if (ready) {
		// Getting and processing the data
		String nume = request.getParameter("nume").substring(0, 1).toUpperCase()
			+ request.getParameter("nume").substring(1).toLowerCase();
		String init_tata = request.getParameter("inittata").toUpperCase();
		String prenume = request.getParameter("prenume").substring(0, 1).toUpperCase()
			+ request.getParameter("prenume").substring(1).toLowerCase();
		String cnp = request.getParameter("cnp");
		String data_n = request.getParameter("datanastere");
		String den_liceu = request.getParameter("liceuabsolvit");
		double medie_liceu = Double.parseDouble(request.getParameter("medieliceu"));
		double medie_bac = Double.parseDouble(request.getParameter("mediebac"));
		// Updating candidat
		QueryCandidat.update(nume, init_tata, prenume, cnp, data_n, den_liceu, medie_liceu, medie_bac);

		// Updating nota candidat
		if (request.getParameter("numeexamen").length() > 0
			&& request.getParameter("notaexamen").length() > 0) {
		    String[] den_e = request.getParameter("numeexamen").toString()
			    .substring(2, request.getParameter("numeexamen").toString().length() - 2).split("\",\"");
		    String[] nota = request.getParameter("notaexamen").toString()
			    .substring(2, request.getParameter("notaexamen").toString().length() - 2).split("\",\"");

		    // Update note by den_e and cnp
		    for (int i = 0; i < den_e.length; i++) {
			QueryRezultat.update(Double.parseDouble(nota[i]), den_e[i], cnp);
		    }
		}

		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "UPDATE";
		String query = "[Updated Candidat] => Parameters: [CNP]: \"" + cnp + "\"";
		QueryLog.insert(cod_u, time, action, query);

	    } else {
		out.print("error");
	    }
	    return;
	}
    }
}
