package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
	String databaseDriver = "com.mysql.cj.jdbc.Driver";
	String databaseURL = "jdbc:mysql://localhost:3306/";

	String databaseName = "isi";
	String databaseUsername = "root";
	String databasePassword = "root";

	Class.forName(databaseDriver);

	return DriverManager.getConnection(databaseURL + databaseName, databaseUsername, databasePassword);
    }
}
