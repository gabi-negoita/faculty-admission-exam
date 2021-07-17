package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryCandidat {
    public static List<String> getLiceu() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select distinct DEN_LICEU from CANDIDAT order by DEN_LICEU");

	    ResultSet result_set = statement.executeQuery();
	    List<String> names = new ArrayList<String>();

	    while (result_set.next()) {
		names.add(result_set.getString("den_liceu"));
	    }

	    statement.close();
	    connection.close();

	    return names;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getNumeCompletSorted(String keyword) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select concat(NUME, ' ', INIT_TATA, '. ', PRENUME) as NUME from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ? or CNP like ?) order by NUME");

	    statement.setString(1, "%" + keyword + "%");
	    statement.setString(2, "%" + keyword + "%");
	    statement.setString(3, "%" + keyword + "%");
	    statement.setString(4, "%" + keyword + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("nume"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getCNPSorted(String keyword) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select concat(NUME, ' ', INIT_TATA, '. ', PRENUME) as NUME, CNP from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ? or CNP like ?) order by NUME");

	    statement.setString(1, "%" + keyword + "%");
	    statement.setString(2, "%" + keyword + "%");
	    statement.setString(3, "%" + keyword + "%");
	    statement.setString(4, "%" + keyword + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("cnp"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getNumeComplet(String name, String den_liceu) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select concat(NUME, ' ', INIT_TATA, '. ', PRENUME) as NUME from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ?) and DEN_LICEU like ?");

	    statement.setString(1, "%" + name + "%");
	    statement.setString(2, "%" + name + "%");
	    statement.setString(3, "%" + name + "%");
	    statement.setString(4, "%" + den_liceu + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("nume"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getNumeComplet(Integer cod_c) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select concat(NUME, ' ', INIT_TATA, '. ', PRENUME) as NUME from CANDIDAT where COD_C = ?");

	    statement.setInt(1, cod_c);

	    ResultSet result_set = statement.executeQuery();

	    String results = result_set.next() ? result_set.getString("nume") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getLiceu(String name, String den_liceu) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select DEN_LICEU from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ?) and DEN_LICEU like ?");

	    statement.setString(1, "%" + name + "%");
	    statement.setString(2, "%" + name + "%");
	    statement.setString(3, "%" + name + "%");
	    statement.setString(4, "%" + den_liceu + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("den_liceu"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getMedieLiceu(String name, String den_liceu) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select MEDIE_LICEU from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ?) and DEN_LICEU like ?");

	    statement.setString(1, "%" + name + "%");
	    statement.setString(2, "%" + name + "%");
	    statement.setString(3, "%" + name + "%");
	    statement.setString(4, "%" + den_liceu + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("medie_liceu"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static List<String> getMedieBac(String name, String den_liceu) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select MEDIE_BAC from CANDIDAT where (NUME like ? or INIT_TATA like ? or PRENUME like ?) and DEN_LICEU like ?");

	    statement.setString(1, "%" + name + "%");
	    statement.setString(2, "%" + name + "%");
	    statement.setString(3, "%" + name + "%");
	    statement.setString(4, "%" + den_liceu + "%");

	    ResultSet result_set = statement.executeQuery();
	    List<String> results = new ArrayList<String>();

	    while (result_set.next()) {
		results.add(result_set.getString("medie_bac"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static boolean hasDuplicatesCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select count(CNP) as CNP from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cnp") : -1;

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
		    .prepareStatement("select nvl(max(COD_C), 0) + 1 as COD_C from CANDIDAT");

	    ResultSet result_set = statement.executeQuery();
	    int results = result_set.next() ? result_set.getInt("cod_c") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static String getNumeByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select NUME from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("nume") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getInitialaTataByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select INIT_TATA from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("init_tata") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getPrenumeByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select PRENUME from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("prenume") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getDataNastereByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DATA_N from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("data_n") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static String getLiceuByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select DEN_LICEU from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    String results = result_set.next() ? result_set.getString("den_liceu") : null;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static double getMedieLiceuByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select MEDIE_LICEU from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    double results = result_set.next() ? result_set.getDouble("medie_liceu") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static double getMedieBacByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select MEDIE_BAC from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    ResultSet result_set = statement.executeQuery();
	    double results = result_set.next() ? result_set.getDouble("medie_bac") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static void insert(String nume, String init_tata, String prenume, String cnp, String data_n,
	    String den_liceu, double medie_liceu, double medie_bac) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("insert into CANDIDAT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

	    int cod_c = getNextCod();

	    statement.setInt(1, cod_c);
	    statement.setString(2, nume);
	    statement.setString(3, init_tata);
	    statement.setString(4, prenume);
	    statement.setString(5, cnp);
	    statement.setString(6, data_n);
	    statement.setString(7, den_liceu);
	    statement.setDouble(8, medie_liceu);
	    statement.setDouble(9, medie_bac);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static List<String> getOptiuneSpecializare() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "select distinct DEN_S as DEN_S from SPECIALIZARE s, OPTIUNE_CANDIDAT o where s.COD_S = o.COD_S and COD_C = ?");

	    int cod_c = QueryCandidat.getNextCod() - 1;

	    statement.setInt(1, cod_c);

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

    public static void deleteByCNP(String cnp) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("delete from CANDIDAT where CNP = ?");

	    statement.setString(1, cnp);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void update(String nume, String init_tata, String prenume, String cnp, String data_n,
	    String den_liceu, double medie_liceu, double medie_bac) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement(
		    "update CANDIDAT set nume = ?, init_tata = ?, prenume = ?, data_n = ?, den_liceu = ?, medie_liceu = ?, medie_bac = ? where CNP = ?");

	    statement.setString(1, nume);
	    statement.setString(2, init_tata);
	    statement.setString(3, prenume);
	    statement.setString(4, data_n);
	    statement.setString(5, den_liceu);
	    statement.setDouble(6, medie_liceu);
	    statement.setDouble(7, medie_bac);
	    statement.setString(8, cnp);

	    statement.execute();

	    statement.close();
	    connection.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static List<Integer> getCod() {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select COD_C from CANDIDAT");

	    ResultSet result_set = statement.executeQuery();
	    List<Integer> results = new ArrayList<Integer>();

	    while (result_set.next()) {
		results.add(result_set.getInt("cod_c"));
	    }

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return null;
    }

    public static float getMedieLiceu(Integer cod_c) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection
		    .prepareStatement("select MEDIE_LICEU from CANDIDAT where COD_C = ?");

	    statement.setInt(1, cod_c);

	    ResultSet result_set = statement.executeQuery();
	    float results = result_set.next() ? result_set.getFloat("medie_liceu") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }

    public static float getMedieBac(Integer cod_c) {
	try {
	    Connection connection = DatabaseConnection.initializeDatabase();
	    PreparedStatement statement = connection.prepareStatement("select MEDIE_BAC from CANDIDAT where COD_C = ?");

	    statement.setInt(1, cod_c);

	    ResultSet result_set = statement.executeQuery();
	    float results = result_set.next() ? result_set.getFloat("medie_bac") : -1;

	    statement.close();
	    connection.close();

	    return results;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return -2;
    }
}
