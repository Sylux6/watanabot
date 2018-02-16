package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BlindtestDatabase {

    static final String DB_NAME = "watanabot.db";


    public void init() throws IOException {
	String url = "jdbc:sqlite:" + DB_NAME;

	try (Connection conn = DriverManager.getConnection(url)) {
	    if (conn != null) {
		DatabaseMetaData meta = conn.getMetaData();
		System.out.println("The driver name is " + meta.getDriverName());
		System.out.println("A new database has been created.");
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }
}
