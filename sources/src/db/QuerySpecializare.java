package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuerySpecializare {
    public static List<String> getDenumire(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select DEN_S from SPECIALIZARE where DEN_S like ?");

	    statement.setString(1, "%" + den_s + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_s"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getDenumire(Integer cod_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DEN_S from SPECIALIZARE where COD_S = ?");

	    statement.setInt(1, cod_s);

	    ResultSet result_set = statement.executeQuery();

	    String results = result_set.next() ? result_set.getString("den_s") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getDenumireSorted() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DEN_S from SPECIALIZARE order by DEN_S");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_s"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getDomeniu(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DEN_D from SPECIALIZARE s, DOMENIU d where s.COD_D = d.COD_D and DEN_S like ?");

	    statement.setString(1, "%" + den_s + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_d"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getDurata(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select DURATA_ANI from SPECIALIZARE where DEN_S like ?");

	    statement.setString(1, "%" + den_s + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("durata_ani"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getRegulaAdmitere(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DESCRIERE from SPECIALIZARE s, REGULA_ADMITERE r where s.COD_R = r.COD_R and DEN_S like ?");

	    statement.setString(1, "%" + den_s + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("descriere"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getLocuri(String den_s, String buget_taxa) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select NR_LOCURI from SPECIALIZARE s, LOC l where s.COD_S = l.COD_S and BUGET_TAXA = ? and DEN_S like ?");

	    statement.setString(1, buget_taxa);
	    statement.setString(2, "%" + den_s + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("nr_locuri"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<Integer> getCod() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select COD_S from SPECIALIZARE");

	    ResultSet result_set = statement.executeQuery();
	    List<Integer> results = new ArrayList<Integer>();

	    while (result_set.next()) {
		results.add(result_set.getInt("cod_s"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static int getCod(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select COD_S from SPECIALIZARE where DEN_S = ?");

	    statement.setString(1, den_s);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_s") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static int getCodExamen(String den_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select e.COD_E as COD_E from SPECIALIZARE s, REGULA_ADMITERE r, EXAMEN e where s.COD_R = r.COD_R and r.COD_E = e.COD_E and DEN_S = ?");

	    statement.setString(1, den_s);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_e") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static int getCodRegulaAdmitere(Integer cod_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select r.COD_R from SPECIALIZARE s, REGULA_ADMITERE r where s.COD_R = r.COD_R and COD_S = ?");

	    statement.setInt(1, cod_s);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_r") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

}
