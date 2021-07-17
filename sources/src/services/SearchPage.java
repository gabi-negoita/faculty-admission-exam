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
import db.QueryDomeniu;
import db.QueryFacultate;
import db.QueryLog;
import db.QuerySpecializare;
import db.QueryUniversitate;
import db.QueryUser;

@WebServlet("/SearchPage")
public class SearchPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession();

	// Search candidat
	if (request.getParameter("searchforcandidat") != null && request.getParameter("liceucombobox") != null) {
	    List<String> nume_complet = QueryCandidat.getNumeComplet(request.getParameter("searchforcandidat"),
		    request.getParameter("liceucombobox"));
	    List<String> den_liceu = QueryCandidat.getLiceu(request.getParameter("searchforcandidat"),
		    request.getParameter("liceucombobox"));
	    List<String> medie_liceu = QueryCandidat.getMedieLiceu(request.getParameter("searchforcandidat"),
		    request.getParameter("liceucombobox"));
	    List<String> medie_bac = QueryCandidat.getMedieBac(request.getParameter("searchforcandidat"),
		    request.getParameter("liceucombobox"));

	    for (int i = 0; i < nume_complet.size(); i++) {
		out.print("<tr>");
		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + nume_complet.get(i) + "</td>");
		out.print("<td>" + den_liceu.get(i) + "</td>");
		out.print("<td>" + medie_liceu.get(i) + "</td>");
		out.print("<td>" + medie_bac.get(i) + "</td>");
		out.print("</tr>");
	    }

	    if (request.getParameter("searchforcandidat").equals("") == false
		    || request.getParameter("liceucombobox").equals("") == false) {
		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Candidat] => Parameters: [Nume]: \""
			+ request.getParameter("searchforcandidat") + "\"" + ", [Liceu]: \""
			+ request.getParameter("liceucombobox") + "\"";

		QueryLog.insert(cod_u, time, action, query);
	    }
	    return;
	}

	// Search universitate
	if (request.getParameter("searchforuniversitate") != null) {
	    List<String> den_u = QueryUniversitate.getDenumire(request.getParameter("searchforuniversitate"));
	    List<String> adresa_u = QueryUniversitate.getAdresa(request.getParameter("searchforuniversitate"));

	    for (int i = 0; i < den_u.size(); i++) {
		out.print("<tr>");
		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + den_u.get(i) + "</td>");
		out.print("<td>" + adresa_u.get(i) + "</td>");
		out.print("</tr>");
	    }

	    if (request.getParameter("searchforuniversitate").equals("") == false) {
		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Universitate] => Parameters: [Denumire]: \""
			+ request.getParameter("searchforuniversitate") + "\"";

		QueryLog.insert(cod_u, time, action, query);
	    }
	    return;
	}

	// Search facultate
	if (request.getParameter("searchforfacultate") != null) {
	    List<String> den_f = QueryFacultate.getDenumire(request.getParameter("searchforfacultate"));
	    List<String> den_u = QueryFacultate.getUniversitate(request.getParameter("searchforfacultate"));
	    List<String> adresa_f = QueryFacultate.getAdresa(request.getParameter("searchforfacultate"));

	    for (int i = 0; i < den_f.size(); i++) {
		out.print("<tr>");
		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + den_f.get(i) + "</td>");
		out.print("<td>" + den_u.get(i) + "</td>");
		out.print("<td>" + adresa_f.get(i) + "</td>");
		out.print("</tr>");
	    }

	    if (request.getParameter("searchforfacultate").equals("") == false) {
		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Facultate] => Parameters: [Denumire]: \""
			+ request.getParameter("searchforfacultate") + "\"";

		QueryLog.insert(cod_u, time, action, query);
	    }

	    return;
	}

	// Search domeniu
	if (request.getParameter("searchfordomeniu") != null) {
	    List<String> den_d = QueryDomeniu.getDenumire(request.getParameter("searchfordomeniu"));
	    List<String> den_f = QueryDomeniu.getFacultate(request.getParameter("searchfordomeniu"));

	    for (int i = 0; i < den_d.size(); i++) {
		out.print("<tr>");
		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + den_d.get(i) + "</td>");
		out.print("<td>" + den_f.get(i) + "</td>");
		out.print("</tr>");
	    }

	    if (request.getParameter("searchfordomeniu").equals("") == false) {
		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Domeniu] => Parameters: [Denumire]: \""
			+ request.getParameter("searchfordomeniu") + "\"";

		QueryLog.insert(cod_u, time, action, query);
	    }
	    return;
	}

	// Search specializare
	if (request.getParameter("searchforspecializare") != null) {
	    List<String> den_s = QuerySpecializare.getDenumire(request.getParameter("searchforspecializare"));
	    List<String> den_d = QuerySpecializare.getDomeniu(request.getParameter("searchforspecializare"));
	    List<String> durata_ani = QuerySpecializare.getDurata(request.getParameter("searchforspecializare"));
	    List<String> regula_admitere = QuerySpecializare
		    .getRegulaAdmitere(request.getParameter("searchforspecializare"));
	    List<String> locuri_buget = QuerySpecializare.getLocuri(request.getParameter("searchforspecializare"),
		    "buget");
	    List<String> locuri_taxa = QuerySpecializare.getLocuri(request.getParameter("searchforspecializare"),
		    "taxa");

	    for (int i = 0; i < den_s.size(); i++) {
		out.print("<tr>");
		out.print("<td><b>" + (i + 1) + "</b></td>");
		out.print("<td>" + den_s.get(i) + "</td>");
		out.print("<td>" + den_d.get(i) + "</td>");
		out.print("<td>" + durata_ani.get(i) + "</td>");
		out.print("<td>" + regula_admitere.get(i) + "</td>");
		out.print("<td>" + locuri_buget.get(i) + "</td>");
		out.print("<td>" + locuri_taxa.get(i) + "</td>");
		out.print("</tr>");
	    }

	    if (request.getParameter("searchforspecializare").equals("") == false) {
		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "SELECT";
		String query = "[Searched for Specializare] => Parameters: [Denumire]: \""
			+ request.getParameter("searchforspecializare") + "\"";

		QueryLog.insert(cod_u, time, action, query);
	    }
	    return;
	}

    }
}
