package dbs2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class ArffExport {
	
	private final String url 		= "jdbc:mysql://localhost:3306/";
	private final String db1 		= "bundesliga";
	private final String driver 	= "com.mysql.jdbc.Driver";
	private final String user 		= "root"; 
	private final String pass 		= "";
	
	private Statement db = null;
	
	private Stack<AutoCloseable> close = new Stack<AutoCloseable>();
	
	private Spiel[] spiele;
	
	public ArffExport() {
		if(connect()) {
			System.out.println("Verbunden.");
			
			if(fetchData())
				exportData();
			
			close();
		}
	}
	
	private boolean fetchData() {			
		if(!fetchSpiele())
			return false;
		
		displayVereine();
		
		return true;
	}
	
	private void displayVereine() {
		System.out.println("ID\t\tTore\tGegen\tNieder.\tHeim\tTorst.\tAusgang\tTag");
		for(Spiel v : spiele)
			System.out.println(v.id + ", " + v.verein + ":\t" + v.tore + "\t" + v.gegentore + "\t" + v.niederlagen + "\t" + strHeimvorteil(v.heimvorteil) + "\t" + v.steigung + "\t" + strAusgang(v.ausgang) + "\t" + strTag(v.tag));
	}

	private boolean fetchSpiele() {
		try {
			ResultSet rs = db.executeQuery("SELECT spiel, verein FROM arffexport");
			rs.last();
			spiele = new Spiel[rs.getRow()];
			rs.beforeFirst();
			
			while(rs.next())
				spiele[rs.getRow() - 1] = new Spiel(rs.getInt(1), rs.getInt(2));
			rs.close();
			
			rs = db.executeQuery("SELECT * FROM arffexport");
			rs.absolute(2);
			System.out.println(rs.getInt(8));
			int i;
			int[] tore;
			
			for(Spiel s : spiele) {
				rs.beforeFirst();
				while(rs.next()) {
					// Laufe vorwärts bis zu spiel s.id von verein s.verein
					if(rs.getInt(1) == s.id && rs.getInt(2) == s.verein) {
						i = 0;
						tore = new int[5];
						
						s.heimvorteil = rs.getInt(7);
						s.ausgang = rs.getInt(8);
						s.tag = rs.getInt(4);
						
						while(rs.previous()) {
							// Laufe rückwärts bis zum letzten spiel des vereins s.verein
							if(rs.getInt(2) == s.verein) {
								if(i < 5) {
									if(i < 3) {
										s.tore += rs.getInt(5);
										s.gegentore += rs.getInt(6);
									}
									if(rs.getInt(8) == 0) s.niederlagen += 1;
									// Tore für steigung sammeln
									tore[i] = rs.getInt(5);
								}
								else break;
								i++;
							}
						}
						
						// Steigung berechnen
						for(i = 0; i < 4; i++)
							s.steigung += tore[i] - tore[i + 1];
						
						break;
					}
				}
			}
			
			rs.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean exportData() {
		try {
			File arff = new File("D:/bundesliga.arff");
			PrintWriter w = new PrintWriter(arff);
			
			w.println("%1. Title: Bundesliga Spielprognose");
			w.println("\t@RELATION spiel");
			w.println();
			w.println("%Letzte drei Spiele");
			w.println("\t@ATTRIBUTE tore NUMERIC");
			w.println("\t@ATTRIBUTE gegentore NUMERIC");
			w.println("%Letzte fuenf Spiele");
			w.println("\t@ATTRIBUTE niederlagen NUMERIC");
			w.println("\t@ATTRIBUTE steigung NUMERIC");
			w.println("\t@ATTRIBUTE heimvorteil {heim,gast}");
			w.println("\t@ATTRIBUTE tag {montag,dienstag,mittwoch,donnerstag,freitag,samstag,sonntag}");
			w.println("\t@ATTRIBUTE ausgang {sieg,niederlage,unentschieden}");
			w.println();
			w.println("\t@DATA");
			
			for(Spiel s : spiele)
				w.println("\t" + s.tore + "," + s.gegentore + "," + s.niederlagen + "," + s.steigung + "," + strHeimvorteil(s.heimvorteil) + "," + strTag(s.tag) + "," + strAusgang(s.ausgang));

			w.close();
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String strTag(int day) {
		switch(day) {
			case 1: return "sonntag";
			case 2: return "montag";
			case 3: return "dienstag";
			case 4: return "mittwoch";
			case 5: return "donnerstag";
			case 6: return "freitag";
			case 7: return "samstag";
			default: return "";
		}
	}
	
	private String strAusgang(int ausgang) {
		switch(ausgang) {
			case 0: return "niederlage";
			case 1: return "unentschieden";
			case 2: return "sieg";
			default: return "";
		}
	}
	
	private String strHeimvorteil(int heimvorteil) {
		switch(heimvorteil) {
			case 0: return "gast";
			case 1: return "heim";
			default: return "";
		}
	}
	
	private boolean connect() {	
		Connection dbConn = getSqlConnection(url, driver, db1, user, pass);
		
		if(dbConn != null)
			db = getSqlStatement(dbConn);
		
		if(db != null)
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
	
	public void close() {
		for(AutoCloseable ac : close) {
			try {
				ac.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	
	class Spiel {

		public int id;
		public int verein;
		
		public int tore;
		public int gegentore;
		public int niederlagen;
		public int steigung;
		
		public int ausgang;
		public int tag;
		public int heimvorteil;
		
		public Spiel(int id, int verein) {
			this.id = id;
			this.verein = verein;
		}
	}
	
	public static void main(String[] args) {
		new ArffExport();
	}
	
}
