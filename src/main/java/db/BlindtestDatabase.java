package db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BlindtestDatabase {

    static final String DB_NAME = "watanabot.db";


    public void init() throws IOException {
	File db = new File(DB_NAME);
	if (db.exists()) {
	    System.out.println("Database already exists");
	    return;
	}
	
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
