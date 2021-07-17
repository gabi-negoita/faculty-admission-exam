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
import db.QueryLoc;
import db.QueryLog;
import db.QueryOptiuneCandidat;
import db.QueryRezultat;
import db.QuerySpecializare;
import db.QueryUniversitate;
import db.QueryUser;

@WebServlet("/AddPage")
public class AddPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession();

	// Filling facultate combobox
	if (request.getParameter("selectedfacultate") != null) {
	    out.print("<option>Selectati o facultate</option>");

	    if (request.getParameter("selectedfacultate").equals("Selectati o universitate") == false) {
		List<String> facultati = QueryUniversitate.getFacultate(request.getParameter("selectedfacultate"));
		out.print("test: " + facultati.size());

		for (String facultate : facultati) {
		    out.print("<option");

		    if (facultate.equals("")) {
			out.print(" selected");
		    }

		    out.print(">");
		    out.print(facultate);
		    out.print("</option>");
		}
	    }
	    return;
	}

	// Filling domeniu combobox
	if (request.getParameter("selecteddomeniu") != null) {
	    out.print("<option>Selectati un domeniu</option>");

	    if (request.getParameter("selecteddomeniu").equals("Selectati o facultate") == false) {
		List<String> domenii = QueryFacultate.getDomeniu(request.getParameter("selecteddomeniu"));

		for (String domeniu : domenii) {
		    out.print("<option");

		    if (domeniu.equals("")) {
			out.print(" selected");
		    }

		    out.print(">");
		    out.print(domeniu);
		    out.print("</option>");
		}
	    }
	    return;
	}

	// Filling specializare combobox
	if (request.getParameter("selectedspecializare") != null) {
	    out.print("<option>Selectati o specializare</option>");

	    if (request.getParameter("selectedspecializare").equals("Selectati un domeniu") == false) {
		List<String> specializari = QueryDomeniu.getSpecializare(request.getParameter("selectedspecializare"));

		for (String specializare : specializari) {
		    out.print("<option");

		    if (specializare.equals("")) {
			out.print(" selected");
		    }

		    out.print(">");
		    out.print(specializare);
		    out.print("</option>");
		}
	    }
	    return;
	}

	// Filling forma invatamant combobox
	if (request.getParameter("selectedbugettaxa") != null) {
	    out.print("<option>Selectati o forma de invatamant</option>");

	    if (request.getParameter("selectedbugettaxa").equals("Selectati o specializare") == false) {
		List<String> formeInvatamant = QueryLoc.getFormaInvatamant();

		for (String forma : formeInvatamant) {
		    out.print("<option");

		    if (forma.equals("")) {
			out.print(" selected");
		    }

		    out.print(">");
		    out.print(forma.substring(0, 1).toUpperCase() + forma.substring(1));
		    out.print("</option>");
		}
	    }
	    return;
	}

	// Submitting the form
	if (request.getParameter("formsubmit") != null) {
	    // Checking for CNP duplicates
	    boolean isDuplicated = true;

	    if (request.getParameter("checkcnpforduplicates") != null) {
		isDuplicated = QueryCandidat.hasDuplicatesCNP(request.getParameter("checkcnpforduplicates"));
	    }

	    if (isDuplicated) {
		out.print("duplicated");
		return;
	    }

	    boolean ready = true;
	    if (request.getParameter("nume") == null || request.getParameter("inittata") == null
		    || request.getParameter("prenume") == null || request.getParameter("cnp") == null
		    || request.getParameter("datanastere") == null || request.getParameter("liceuabsolvit") == null
		    || request.getParameter("medieliceu") == null || request.getParameter("mediebac") == null
		    || request.getParameter("specializare") == null || request.getParameter("bugettaxa") == null) {

		ready = false;
	    }

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

	    // Processing the JSON string as a string
	    // Turning it into an array of strings
	    String[] den_s = request.getParameter("specializare").toString()
		    .substring(2, request.getParameter("specializare").toString().length() - 2).split("\",\"");
	    String[] buget_taxa = request.getParameter("bugettaxa").toString()
		    .substring(2, request.getParameter("bugettaxa").toString().length() - 2).split("\",\"");

	    if (ready) {
		// Inserting candidate
		QueryCandidat.insert(nume, init_tata, prenume, cnp, data_n, den_liceu, medie_liceu, medie_bac);

		// Inserting candidate options
		for (int i = 0; i < den_s.length; i++) {
		    QueryOptiuneCandidat.insert(den_s[i], buget_taxa[i]);
		}

		// Inserting candidate results
		List<String> specializari = QueryCandidat.getOptiuneSpecializare();
		for (String specializare : specializari) {
		    int cod_e = QuerySpecializare.getCodExamen(specializare);
		    if (cod_e > 0) {
			QueryRezultat.insert(cod_e, 0.0);
		    }
		}

		// Logging the operation
		int cod_u = QueryUser.getCodByUsername(session.getAttribute("username").toString());
		String time = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s").format(LocalDateTime.now());
		String action = "INSERT";
		String query = "[Inserted Candidat] => Parameters: [CNP]: \"" + cnp + "\"";
		QueryLog.insert(cod_u, time, action, query);

	    } else {
		out.print("error");
	    }
	    return;
	}
    }

}
