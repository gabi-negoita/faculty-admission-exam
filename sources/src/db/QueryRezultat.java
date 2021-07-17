package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QueryRezultat {
    public static void insert(int cod_e, double nota) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("insert into REZULTAT VALUES (?, ?, ?)");

	    int cod_c = QueryCandidat.getNextCod() - 1;

	    statement.setInt(1, cod_c);
	    statement.setInt(2, cod_e);
	    statement.setDouble(3, nota);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static double getNotaByDenumireExamenAndCNP(String den_e, String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select NOTA from REZULTAT r, CANDIDAT c, EXAMEN e where c.COD_C = r.COD_C and r.COD_E = e.COD_E and DEN_E = ? and CNP = ?");

	    statement.setString(1, den_e);
	    statement.setString(2, cnp);

	    ResultSet result_set = statement.executeQuery();
	    double results = result_set.next() ? result_set.getDouble("nota") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static void deleteByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("delete from REZULTAT where COD_C = (select COD_C from CANDIDAT where CNP = ?)");

	    statement.setString(1, cnp);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void update(double nota, String den_e, String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    " update REZULTAT set NOTA = ? where COD_C = (select COD_C from CANDIDAT where CNP = ?) and COD_E = (select COD_E from examen where DEN_E = ?);");

	    statement.setDouble(1, nota);
	    statement.setString(2, cnp);
	    statement.setString(3, den_e);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static float getNota(Integer cod_c, Integer cod_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select NOTA from REZULTAT where COD_C = ? and COD_E = (select e.COD_E from EXAMEN e, REGULA_ADMITERE r, SPECIALIZARE s where r.COD_E = e.COD_E and r.COD_R = s.COD_R and COD_S = ?)");

	    statement.setInt(1, cod_c);
	    statement.setInt(2, cod_s);

	    ResultSet result_set = statement.executeQuery();
	    float results = result_set.next() ? result_set.getFloat("nota") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }
}
