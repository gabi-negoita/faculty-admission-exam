package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryUniversitate {

    public static List<String> getDenumire() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DEN_U from UNIVERSITATE order by DEN_U");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_u"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getDenumire(String den_u) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select DEN_U from UNIVERSITATE where DEN_U like ?");

	    statement.setString(1, "%" + den_u + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_u"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getAdresa(String den_u) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select ADRESA_U from UNIVERSITATE where DEN_U like ?");

	    statement.setString(1, "%" + den_u + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("adresa_u"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getFacultate(String den_u) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DEN_F from UNIVERSITATE u, FACULTATE f where u.COD_U = f.COD_U and DEN_U like ?");

	    statement.setString(1, "%" + den_u + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_f"));
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
