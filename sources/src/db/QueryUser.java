package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryUser {

    public static String getUsernameByCod(int cod_u) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select USERNAME from USER where COD_U = ?");

	    statement.setInt(1, cod_u);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("username") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getPasswordByUsername(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select PASSWORD from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("password") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getFirstnameByUsername(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select FIRSTNAME from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("firstname") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getLastnameByUsername(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select LASTNAME from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("lastname") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getRoleByUsername(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select ROLE from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("role") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static int getCodByUsername(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select COD_U from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_u") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static boolean hasDuplicates(String username) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select count(USERNAME) as USERNAME from USER where USERNAME = ?");

	    statement.setString(1, username);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("username") : -1;

	    statement.close();
	    connection.close();

	    return results != 0;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return true;
    }

    public static int getNextCod() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select nvl(max(COD_U), 0) + 1 as COD_U from USER");

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_u") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static void insert(String firstname, String lastname, String username, String password, String role) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("insert into USER VALUES (?, ?, ?, ?, ?, ?)");

	    int cod_u = getNextCod();

	    statement.setInt(1, cod_u);
	    statement.setString(2, firstname);
	    statement.setString(3, lastname);
	    statement.setString(4, username);
	    statement.setString(5, password);
	    statement.setString(6, role);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
