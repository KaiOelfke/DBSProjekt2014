package dbs2014;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class DataImport {
	
	private final String url 		= "jdbc:mysql://localhost:3306/";
	private final String db1 		= "bundesliga";
	private final String db2 		= "bundesliga_daten";
	private final String driver 	= "com.mysql.jdbc.Driver";
	private final String user 		= "root"; 
	private final String pass 		= "";
	
	private Statement db 	= null;
	private Statement data 	= null;
	
	private Stack<AutoCloseable> close = new Stack<AutoCloseable>();
	
	public DataImport() {
		if(connect()) {
			System.out.println("Verbunden.");
			
			if(importLiga())
				System.out.println("Ligadaten importiert.");
			
			if(importVerein())
				System.out.println("Vereinsdaten importiert.");
			
			if(importSpieler())
				System.out.println("Spielerdaten importiert.");
			
			if(importSpiel())
				System.out.println("Spieldaten importiert.");
			
			close();
			System.out.println("Verbindung geschlossen.");
		}
		
	}
	
	private boolean importSpiel() {
		try {
			ResultSet rs = data.executeQuery("SELECT spiel_id, datum, uhrzeit, heim, gast, tore_heim, tore_gast, spieltag FROM spiel");
			
			while(rs.next())
				db.executeUpdate("INSERT INTO spiel (id, datum, uhrzeit, gastgeber, gast, gastgeber_tore, gast_tore, spieltag) VALUES ('" + rs.getString(1) + "','" + rs.getString(2) + "','" +
						rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getString(5) + "','" + rs.getString(6) + "','" + rs.getString(7) + "','" + rs.getString(8) + "')");
			
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean importSpieler() {
		try {
			ResultSet rs = data.executeQuery("SELECT spieler_id, vereins_id, spieler_name, trikot_nr, land, tore FROM spieler");
			
			while(rs.next())
				db.executeUpdate("INSERT INTO spieler (id, verein, name, trikotnummer, heimatland, tore) VALUES ('" + rs.getString(1) + "','" + rs.getString(2) + "','" +
						rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getString(5) + "','" + rs.getString(6) + "')");
			
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean importVerein() {
		try {
			ResultSet rs = data.executeQuery("SELECT v_id, name, liga FROM verein");
			
			while(rs.next())
				db.executeUpdate("INSERT INTO verein (id, name, liga) VALUES ('" + rs.getString(1) + "','" + rs.getString(2) + "','" + rs.getString(3) + "')");
			
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean importLiga() {
		try {
			ResultSet rs = data.executeQuery("SELECT Liga_Nr, CONCAT(Liga_Nr, '. Bundesliga') FROM Liga");
			
			while(rs.next())
				db.executeUpdate("INSERT INTO liga (id, name) VALUES ('" + rs.getString(1) + "','" + rs.getString(2) + "')");
			
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean connect() {	
		Connection dbConn = getSqlConnection(url, driver, db1, user, pass);
		Connection dataConn = getSqlConnection(url, driver, db2, user, pass);
		
		if(dbConn != null && dataConn != null) {
			db = getSqlStatement(dbConn);
			data = getSqlStatement(dataConn);
		}
		
		if(db != null && data != null)
			return true;
		return false;
	}
	
	private Connection getSqlConnection(String url, String driver, String db, String user, String pass) {
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(url + db, user, pass);
			close.push(conn);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Statement getSqlStatement(Connection conn) {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			close.push(stmt);
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		for(AutoCloseable ac : close) {
			try {
				ac.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new DataImport();
	}
	
}
