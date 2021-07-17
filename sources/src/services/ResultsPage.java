package services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import db.QueryCandidat;
import db.QueryLoc;
import db.QueryOptiuneCandidat;
import db.QueryRezultat;
import db.QuerySpecializare;

@WebServlet("/ResultsPage")
public class ResultsPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	PrintWriter out = response.getWriter();

	// Filling table results
	// Drawing pie chart
	// Executing algorithm: repartizare candidati
	if (request.getParameter("fillresults") != null && request.getParameter("specializarecombobox") != null) {

	    HashMap<Integer, List<Triplet<Integer, Float, String>>> specializari = new HashMap<>();
	    List<Integer> codCandidat = QueryCandidat.getCod();
	    List<Integer> codSpecializare = QuerySpecializare.getCod();

	    // Adding cod_s as key
	    for (Integer cod_s : codSpecializare) {
		specializari.put(cod_s, null);
	    }

	    // For every cod_s and for every cod_c in cod_s, add if cod_c has cod_s as
	    // option
	    for (Map.Entry<Integer, List<Triplet<Integer, Float, String>>> item : specializari.entrySet()) {
		List<Triplet<Integer, Float, String>> candidati = new ArrayList<>();
		for (Integer cod_c : codCandidat) {
		    if (QueryOptiuneCandidat.hasOption(cod_c, item.getKey())) {
			// Computing medie concurs candidat
			float medieConcurs = -1;
			int cod_r = QuerySpecializare.getCodRegulaAdmitere(item.getKey());

			// ADMIS => Candidat ADMIS LA la specializarea respectiva
			// ACCEPTAT => Candidat ACCEPTAT in lista cu medii de concurs (NU este admis
			// inca)
			// RESPINS => Candidat RESPINS (cauza: nota prea mica, locuri insuficiente)
			// ADMIS RESPINS => Candidat RESPINS deoarece a fost deja admis la alta
			// specializare

			// Medie: 50% medie bacalaureat + 50% medie liceu => cod_r : [1, 3, 5]
			if (cod_r == 1 || cod_r == 3 || cod_r == 5) {
			    float medieLiceu = QueryCandidat.getMedieLiceu(cod_c);
			    float medieBac = QueryCandidat.getMedieBac(cod_c);

			    medieConcurs = (medieBac + medieLiceu) / 2;
			    if (medieLiceu < 5 || medieBac < 5 || medieConcurs < 5) {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "RESPINS"));
			    } else {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "ACCEPTAT"));
			    }
			}
			// Medie: 50% medie bacalaureat + 50% nota examen/proba practica/interviu =>
			// cod_r : [2, 4, 6, 7]
			else if (cod_r == 2 || cod_r == 4 || cod_r == 6 || cod_r == 7) {
			    float medieBac = QueryCandidat.getMedieBac(cod_c);
			    float medieExamen = QueryRezultat.getNota(cod_c, item.getKey());

			    medieConcurs = (medieBac + medieExamen) / 2;
			    if (medieBac < 5 || medieExamen < 5 || medieConcurs < 5) {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "RESPINS"));
			    } else {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "ACCEPTAT"));
			    }
			}
			// Medie: 100% nota examen/proba practica => cod_r : [8, 9, 10, 11, 12, 13]
			else if (cod_r >= 8 && cod_r <= 13) {
			    medieConcurs = QueryRezultat.getNota(cod_c, item.getKey());
			    if (medieConcurs < 5) {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "RESPINS"));
			    } else {
				candidati.add(new Triplet<>(cod_c, medieConcurs, "ACCEPTAT"));
			    }
			}
		    }
		}
		// Sorting in descending order (bubblesort)
		if (candidati.size() >= 2) {
		    for (int i = 0; i < candidati.size(); i++) {
			for (int j = i + 1; j < candidati.size(); j++) {
			    if (candidati.get(i).getValue1() < candidati.get(j).getValue1()) {
				Collections.swap(candidati, i, j);
			    }
			}
		    }
		}
		item.setValue(candidati);
	    }

	    HashMap<Integer, Pair<Integer, Integer>> specs = new HashMap<>();
	    for (Integer cod_s : codSpecializare) {
		specs.put(cod_s, new Pair<Integer, Integer>(QueryLoc.getNumarLocuri(cod_s, "buget"),
			QueryLoc.getNumarLocuri(cod_s, "taxa")));
	    }

	    for (Integer cod_c : codCandidat) {
		List<Integer> codSpecializareOptiuni = QueryOptiuneCandidat.getCodSpecializare(cod_c);
		List<String> formaInvatamantOptiuni = QueryOptiuneCandidat.getFormaInvatamant(cod_c);
		boolean admis = false;
		int codsAdmis = 0;

		for (int i = 0; i < codSpecializareOptiuni.size(); i++) {
		    List<Triplet<Integer, Float, String>> candidati = specializari.get(codSpecializareOptiuni.get(i));

		    for (int j = 0; j < candidati.size(); j++) {
			if (candidati.get(j).getValue0() == cod_c) {
			    // Checking for edge cases
			    if (candidati.get(j).getValue2() == "RESPINS") {
				break;
			    }

			    if ((formaInvatamantOptiuni.get(i) == "buget"
				    && specs.get(codSpecializareOptiuni.get(i)).getValue0() == 0)
				    || (formaInvatamantOptiuni.get(i) == "taxa"
					    && specs.get(codSpecializareOptiuni.get(i)).getValue1() == 0)) {
				candidati.set(j, candidati.get(j).setAt2("RESPINS"));
				break;
			    }

			    if (admis == false) {
				candidati.set(j, candidati.get(j).setAt2("ADMIS"));

				// Scad loc
				if (formaInvatamantOptiuni.get(i) == "buget") {
				    specs.get(codSpecializareOptiuni.get(i))
					    .setAt0(specs.get(codSpecializareOptiuni.get(i)).getValue0() - 1);
				} else {
				    specs.get(codSpecializareOptiuni.get(i))
					    .setAt1(specs.get(codSpecializareOptiuni.get(i)).getValue1() - 1);
				}

				admis = true;
				codsAdmis = codSpecializareOptiuni.get(i);
			    } else if (codsAdmis != codSpecializareOptiuni.get(i)) {
				candidati.set(j, candidati.get(j).setAt2("ACCEPTAT RESPINS"));
			    }
			}
		    }
		    specializari.put(codSpecializareOptiuni.get(i), candidati);
		}
	    }

	    // Ajax response
	    int i = 0, admisi = 0, respinsi = 0;

	    for (Map.Entry<Integer, List<Triplet<Integer, Float, String>>> item : specializari.entrySet()) {
		String den_s = QuerySpecializare.getDenumire(item.getKey());

		if (request.getParameter("specializarecombobox").equals("") == false
			&& request.getParameter("specializarecombobox").equals(den_s) == false) {
		    continue;
		}

		for (Triplet<Integer, Float, String> candidat : item.getValue()) {
		    // item.getKey() -> cod_s
		    // candidat.getValue0() -> cod_c
		    // candidat.getValue1() -> medie
		    // candidat.getValue2() -> status
		    if (candidat.getValue2().equals("ADMIS")) {
			admisi++;
		    } else if (candidat.getValue2().equals("RESPINS")) {
			respinsi++;
		    }

		    out.print("<tr>");

		    out.print("<td><b>" + (i + 1) + "</b></td>");
		    out.print("<td>" + QueryCandidat.getNumeComplet(candidat.getValue0()) + "</td>");
		    out.print("<td>" + (int) (candidat.getValue1() * 100) / 100.0 + "</td>");
		    out.print("<td>" + den_s + "</td>");
		    out.print(
			    "<td>" + QueryOptiuneCandidat.getFormaInvatamant(candidat.getValue0()).get(0).toUpperCase()
				    + "</td>");
		    out.print("<td>" + candidat.getValue2() + "</td>");

		    out.print("</tr>");

		    i++;
		}
	    }
	    out.print("#" + admisi + "#" + respinsi);

	    return;
	}

	// PDF export
	if (request.getParameter("pdfexport") != null && request.getParameter("numecomplet") != null
		&& request.getParameter("medieconcurs") != null && request.getParameter("specializari") != null
		&& request.getParameter("bugettaxa") != null && request.getParameter("rezultat") != null) {

	    String[] columns = { "#", "Nume", "Medie", "Specializare", "Buget/Taxa", "Rezultat" };
	    String[] numeComplet = request.getParameter("numecomplet").toString()
		    .substring(2, request.getParameter("numecomplet").toString().length() - 2).split("\",\"");
	    String[] medie = request.getParameter("medieconcurs").toString()
		    .substring(2, request.getParameter("medieconcurs").toString().length() - 2).split("\",\"");
	    String[] specializari = request.getParameter("specializari").toString()
		    .substring(2, request.getParameter("specializari").toString().length() - 2).split("\",\"");
	    String[] bugetTaxa = request.getParameter("bugettaxa").toString()
		    .substring(2, request.getParameter("bugettaxa").toString().length() - 2).split("\",\"");
	    String[] rezultat = request.getParameter("rezultat").toString()
		    .substring(2, request.getParameter("rezultat").toString().length() - 2).split("\",\"");

	    int max;
	    int[] spaces = { 4, 0, 0, 0, 0, 14 };

	    max = columns[1].length();
	    for (int i = 0; i < numeComplet.length; i++) {
		max = numeComplet[i].length() > max ? numeComplet[i].length() : max;
	    }
	    spaces[1] = max + 1;

	    max = columns[2].length();
	    for (int i = 0; i < medie.length; i++) {
		max = medie[i].length() > max ? medie[i].length() : max;
	    }
	    spaces[2] = max + 1;

	    max = columns[3].length();
	    for (int i = 0; i < specializari.length; i++) {
		max = specializari[i].length() > max ? specializari[i].length() : max;
	    }
	    spaces[3] = max + 1;

	    max = columns[4].length();
	    for (int i = 0; i < bugetTaxa.length; i++) {
		max = bugetTaxa[i].length() > max ? bugetTaxa[i].length() : max;
	    }
	    spaces[4] = max + 1;

	    String[] delimiter = { "", "", "", "", "", "" };
	    for (int i = 0; i < spaces.length; i++) {
		for (int j = 0; j < spaces[i]; j++) {
		    delimiter[i] = delimiter[i] + " ";
		}
	    }

	    int maxRowChar = spaces[0] + spaces[1] + spaces[2] + spaces[3] + spaces[4] + spaces[5];

	    PDDocument document = new PDDocument();
	    PDPage blankPage = new PDPage();

	    // Title
	    PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
	    contentStream.setFont(PDType1Font.COURIER_BOLD, 18);
	    contentStream.setLeading(14.5f);
	    contentStream.setCharacterSpacing(-0.5f);

	    String title = "Rezultatele candidatilor admisi/respinsi";
	    contentStream.beginText();
	    contentStream.newLineAtOffset(306 - title.length() * 5.15f, blankPage.getMediaBox().getHeight() - 64);
	    contentStream.showText(title);
	    contentStream.endText();

	    // Body
	    contentStream.beginText();
	    contentStream.newLineAtOffset(306 - maxRowChar * 2.75f, blankPage.getMediaBox().getHeight() - 128);
	    contentStream.setFont(PDType1Font.COURIER, 10);

	    // Printing column names
	    for (int i = 0; i < columns.length; i++) {
		contentStream.showText(columns[i] + delimiter[i].substring(columns[i].length()));
	    }
	    contentStream.newLine();
	    contentStream.newLine();

	    // Printing rows
	    for (int i = 0; i < numeComplet.length; i++) {
		String index = "" + (i + 1);
		contentStream.showText(index + delimiter[0].substring(index.length()) + numeComplet[i]
			+ delimiter[1].substring(numeComplet[i].length()) + medie[i]
			+ delimiter[2].substring(medie[i].length()) + specializari[i]
			+ delimiter[3].substring(specializari[i].length()) + bugetTaxa[i]
			+ delimiter[4].substring(bugetTaxa[i].length()) + rezultat[i]);

		contentStream.newLine();
	    }
	    contentStream.endText();
	    contentStream.close();

	    document.addPage(blankPage);

	    document.save("C:\\Windows\\Temp\\Results.pdf");

	    document.close();
	    return;
	}

	// XLS export
	if (request.getParameter("xlsexport") != null && request.getParameter("numecomplet") != null
		&& request.getParameter("medieconcurs") != null && request.getParameter("specializari") != null
		&& request.getParameter("bugettaxa") != null && request.getParameter("rezultat") != null) {

	    String[] columns = { "#", "Nume", "Medie", "Specializare", "Buget/Taxa", "Rezultat" };
	    String[] numeComplet = request.getParameter("numecomplet").toString()
		    .substring(2, request.getParameter("numecomplet").toString().length() - 2).split("\",\"");
	    String[] medie = request.getParameter("medieconcurs").toString()
		    .substring(2, request.getParameter("medieconcurs").toString().length() - 2).split("\",\"");
	    String[] specializari = request.getParameter("specializari").toString()
		    .substring(2, request.getParameter("specializari").toString().length() - 2).split("\",\"");
	    String[] bugetTaxa = request.getParameter("bugettaxa").toString()
		    .substring(2, request.getParameter("bugettaxa").toString().length() - 2).split("\",\"");
	    String[] rezultat = request.getParameter("rezultat").toString()
		    .substring(2, request.getParameter("rezultat").toString().length() - 2).split("\",\"");

	    try (XSSFWorkbook wb = new XSSFWorkbook()) {
		XSSFSheet sheet = wb.createSheet();

		// Set the values for the table
		XSSFRow row;
		XSSFCell cell;

		// Header
		row = sheet.createRow(0);
		for (int i = 0; i < columns.length; i++) {
		    cell = row.createCell(i);
		    cell.setCellValue(columns[i]);
		}

		// Body
		for (int i = 0; i < numeComplet.length; i++) {
		    row = sheet.createRow(i + 1);

		    cell = row.createCell(0);
		    cell.setCellValue(i + 1);
		    cell = row.createCell(1);
		    cell.setCellValue(numeComplet[i]);
		    cell = row.createCell(2);
		    cell.setCellValue(medie[i]);
		    cell = row.createCell(3);
		    cell.setCellValue(specializari[i]);
		    cell = row.createCell(4);
		    cell.setCellValue(bugetTaxa[i]);
		    cell = row.createCell(5);
		    cell.setCellValue(rezultat[i]);
		}

		// Save
		try (FileOutputStream fileOut = new FileOutputStream("C:\\Windows\\Temp\\Results.xlsx")) {
		    wb.write(fileOut);
		}
		return;
	    }
	}
    }
}
