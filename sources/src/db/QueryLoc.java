package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryLoc {

    public static List<String> getFormaInvatamant() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select distinct BUGET_TAXA from LOC order by BUGET_TAXA");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("buget_taxa"));
	    }
	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static Integer getNumarLocuri(Integer cod_s, String buget_taxa) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select NR_LOCURI from LOC where BUGET_TAXA = ? and COD_S = ?");

	    statement.setString(1, buget_taxa);
	    statement.setInt(2, cod_s);

	    ResultSet result_set = statement.executeQuery();

	    Integer results = result_set.next() ? result_set.getInt("nr_locuri") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }
}
