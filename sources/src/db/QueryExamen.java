package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryExamen {
    public static List<String> getDenumireByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select distinct den_e from EXAMEN e, REGULA_ADMITERE r, SPECIALIZARE s, OPTIUNE_CANDIDAT o, CANDIDAT c where e.COD_E = r.COD_E and r.COD_R = s.COD_R and s.COD_S = o.COD_S and o.COD_C = c.COD_C and CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_e"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getLocatieByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select distinct locatie from EXAMEN e, REGULA_ADMITERE r, SPECIALIZARE s, OPTIUNE_CANDIDAT o, CANDIDAT c where e.COD_E = r.COD_E and r.COD_R = s.COD_R and s.COD_S = o.COD_S and o.COD_C = c.COD_C and CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("locatie"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getDataByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select distinct data from EXAMEN e, REGULA_ADMITERE r, SPECIALIZARE s, OPTIUNE_CANDIDAT o, CANDIDAT c where e.COD_E = r.COD_E and r.COD_R = s.COD_R and s.COD_S = o.COD_S and o.COD_C = c.COD_C and CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("data"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }
}
