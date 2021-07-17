package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDomeniu {

    public static List<String> getDenumire(String den_d) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DEN_D from DOMENIU where DEN_D like ?");

	    statement.setString(1, "%" + den_d + "%");

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

    public static List<String> getFacultate(String den_d) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DEN_F from DOMENIU d, FACULTATE f where d.COD_F = f.COD_F and DEN_D like ?");

	    statement.setString(1, "%" + den_d + "%");

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

    public static List<String> getSpecializare(String den_d) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DEN_S from DOMENIU d, SPECIALIZARE s where d.COD_D = s.COD_D and DEN_D like ?");

	    statement.setString(1, "%" + den_d + "%");

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

}
