package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryOptiuneCandidat {
    public static int getNextCod() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select nvl(max(COD_O), 0) + 1 as COD_O from OPTIUNE_CANDIDAT");

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_o") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static void insert(String den_s, String buget_taxa) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("insert into OPTIUNE_CANDIDAT VALUES (?, ?, ?, ?)");

	    int cod_o = getNextCod();
	    int cod_c = QueryCandidat.getNextCod() - 1;
	    int cod_s = QuerySpecializare.getCod(den_s);

	    statement.setInt(1, cod_o);
	    statement.setString(2, buget_taxa);
	    statement.setInt(3, cod_c);
	    statement.setInt(4, cod_s);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void deleteByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "delete from OPTIUNE_CANDIDAT where COD_C = (select COD_C from CANDIDAT where CNP = ?)");

	    statement.setString(1, cnp);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static boolean hasOption(int cod_c, int cod_s) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select count(*) as COUNT from OPTIUNE_CANDIDAT where COD_C = ? and COD_S = ?");

	    statement.setInt(1, cod_c);
	    statement.setInt(2, cod_s);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("count") : -1;

	    statement.close();
	    connection.close();

	    return results > 0;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return false;
    }

    public static List<Integer> getCodSpecializare(int cod_c) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select COD_S from OPTIUNE_CANDIDAT where COD_C = ? order by COD_O");

	    statement.setInt(1, cod_c);

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

    public static List<String> getFormaInvatamant(int cod_c) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select lower(BUGET_TAXA) as BUGET_TAXA from OPTIUNE_CANDIDAT where COD_C = ? order by COD_O");

	    statement.setInt(1, cod_c);

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
}
