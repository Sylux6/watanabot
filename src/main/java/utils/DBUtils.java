package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.BlindtestDatabase;

public class DBUtils {

    static private final String DB_NAME = "watanabot.db";
    static private String url = "jdbc:sqlite:" + DB_NAME;
    static private Connection conn;
    static public int collection_size = 0;
    static private DatabaseMetaData meta;
    
    static public void initDB() {
	boolean exist = false;
	File db = new File(DB_NAME);

	if (db.exists()) {
	    exist = true;
	}
	
	try {
	    conn = DriverManager.getConnection(url);
	    meta = conn.getMetaData();
	} catch (SQLException e) {
	    BotUtils.logger.error("Database connection", e);
	}	

	BotUtils.logger.info("Connected to "+DB_NAME);
	if (!exist) BlindtestDatabase.init();
    }
    
    static public int execute(String sql) throws SQLException {
	Statement stmt = conn.createStatement();
	return stmt.executeUpdate(sql);
    }
    
    static public ResultSet query(String sql) throws SQLException {
	Statement stmt = conn.prepareStatement(sql);
	return stmt.executeQuery(sql);
    }
    
    static public void update() {
	try {
	    conn.close();
	    conn = DriverManager.getConnection(url);
	    meta = conn.getMetaData();
	} catch (SQLException e) {
	    BotUtils.logger.error("Database connection", e);
	}
    }
}
