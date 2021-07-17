package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryLog {

    public static List<Integer> getCodUser(String time, String action) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select COD_U from LOG where TIME like ? and ACTION like ? order by TIME desc");

	    statement.setString(1, "%" + time + "%");
	    statement.setString(2, "%" + action + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<Integer> results = new ArrayList<Integer>();

	    while (result_set.next()) {
		results.add(result_set.getInt("cod_u"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getTime(String time, String action) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select TIME from LOG where TIME like ? and ACTION like ? order by TIME desc");

	    statement.setString(1, "%" + time + "%");
	    statement.setString(2, "%" + action + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> names = new ArrayList<String>();

	    while (result_set.next()) {
		names.add(result_set.getString("time"));
	    }

	    statement.close();
	    connection.close();

	    return names;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getAction(String time, String action) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select ACTION from LOG where TIME like ? and ACTION like ? order by TIME desc");

	    statement.setString(1, "%" + time + "%");
	    statement.setString(2, "%" + action + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> names = new ArrayList<String>();

	    while (result_set.next()) {
		names.add(result_set.getString("action"));
	    }

	    statement.close();
	    connection.close();

	    return names;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getQuery(String time, String action) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select QUERY from LOG where TIME like ? and ACTION like ? order by TIME desc");

	    statement.setString(1, "%" + time + "%");
	    statement.setString(2, "%" + action + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> names = new ArrayList<String>();

	    while (result_set.next()) {
		names.add(result_set.getString("query"));
	    }

	    statement.close();
	    connection.close();

	    return names;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static void insert(int cod_u, String time, String action, String query) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("insert into LOG VALUES (?, ?, ?, ?)");

	    statement.setInt(1, cod_u);
	    statement.setString(2, time);
	    statement.setString(3, action);
	    statement.setString(4, query);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static String getMinTime() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select nvl(min(TIME), now()) as TIME from LOG");

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("time") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getMaxTime() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select nvl(max(TIME), now()) as TIME from LOG;");

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("time") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

}
