package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import modules.blindtest.Category;
import utils.BotUtils;
import utils.DBUtils;

public class BlindtestDatabase {
    
    static public void init() {
	try {
	    
	    DBUtils.execute("CREATE TABLE blindtest_collection ("
	        	+ "ID integer PRIMARY KEY AUTOINCREMENT,"
	        	+ "URL text NOT NULL,"
	        	+ "TITLE text NOT NULL,"
	        	+ "ROMANIZED_TITLE text,"
	        	+ "ARTIST text NOT NULL,"
	        	+ "YEAR integer NOT NULL,"
	        	+ "SOURCE text NOT NULL,"
	        	+ "CATEGORY text NOT NULL,"
	        	+ "TAGS text"
	        	+ ");");

	    DBUtils.execute("CREATE TABLE blindtest_leaderboard ("
		    + "ID_MEMBER integer NOT NULL,"
		    + "ID_GUILD integer NOT NULL,"
		    + "SCORE integer NOT NULL,"
		    + "CONSTRAINT blindtest_leaderboard_pk PRIMARY KEY (id_member,id_guild)"
		    + ");");
	} catch (SQLException e) {
	    BotUtils.logger.error("Blindtest database creation", e);
	}
	    
	BotUtils.logger.info("Blindtest database created");
	    populate();
    }
   
    static public void populate() {
	try {
	    addSong("https://www.youtube.com/watch?v=eieGBVOcYvI", "WATER BLUE NEW WORLD", "WATER BLUE NEW WORLD", "Aqours", 2017, "Love Live! Sunshine!!", Category.INSERT_SONG, "idol love_live");
	    addSong("https://www.youtube.com/watch?v=Rj1IMG4QtTM", "勇気はどこに?君の胸に!",  "Yuuki wa Doko ni? Kimi no Mune ni!", "Aqours", 2017, "Love Live! Sunshine!!", Category.ENDING, "idol love_live");
	    addSong("https://www.youtube.com/watch?v=XX1grXH07AU", "ぼなぺてぃーと♡S", "Bon Appétit♡S", "Blend A", 2017, "Blend S", Category.OPENING, "");
	    addSong("https://www.youtube.com/watch?v=fFuj4lcJ96o", "oldToday", "oldToday", "Sawano Hiroyuki", 2017, "Re:Creators", Category.INSERT_SONG, "");
	} catch (SQLException e) {
	    BotUtils.logger.error("Blindtest database population", e);
	}
	BotUtils.logger.info("Blindtest database populated");
    }
    
    static public void addSong(String url, String title, String romanized_title, String artist, int year, String source, Category category, String tags) throws SQLException {
	DBUtils.execute("INSERT INTO blindtest_collection (URL, TITLE, ROMANIZED_TITLE, ARTIST, YEAR, SOURCE, CATEGORY, TAGS) VALUES ("
		+ "\"" + url + "\","
		+ "\"" + title.replace("\"", "\\\"") + "\","
		+ "\"" + romanized_title.replace("\"", "\\\"") + "\","
		+ "\"" + artist.replace("\"", "\\\"") + "\","
		+ year + ","
		+ "\"" + source.replace("\"", "\\\"") + "\","
		+ "\"" + category.toString() + "\","
		+ "\"" + tags.replace("\"", "\\\"") + "\")");
	DBUtils.collection_size++;
    }
    
    static public void addPlayer(Long id, Long guild, int score) throws SQLException {
	DBUtils.execute("INSERT INTO blondtest_leaderboard (ID_MEMBER, ID_GUILD, SCORE) VALUES (" + id + ", "+ guild+", "+score+")");
	BotUtils.logger.info("Player "+id+" from guild "+guild+" added to leaderboard");
    }
    
    static public void updateScore(long id, long guild, int diff) throws SQLException {
	// Determining whether olayer exists or not
	ResultSet rs = DBUtils.query("SELECT * FROM blindtest_leaderboard WHERE ID_MEMBER="+id+" AND ID_GUILD="+guild);
	if (!rs.next()) {
	    addPlayer(id, guild, Math.max(0, diff));
	}
	else {
	    DBUtils.execute("UPDATE blindtest_leaderboard SET score="+Math.max(0, (rs.getInt("SCORE")+diff))+"WHERE ID_MEMBER="+id+" AND ID_GUILD="+guild);
	}
	BotUtils.logger.info("Score updated for user "+id+" from guild "+guild+" to "+rs.getInt("score"));
    }
    
    static public ResultSet selectRandom(int n) throws SQLException {
	return DBUtils.query("SELECT * FROM blindtest_collection" + 
		"ORDER BY RANDOM() LIMIT " + n);
    }
    
    static public void databaseSize() throws SQLException {
	ResultSet rs = DBUtils.query("SELECT COUNT(*) AS total FROM blindtest_collection");
	DBUtils.collection_size = rs.getInt("total");
    }
}
