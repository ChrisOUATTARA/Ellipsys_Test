package org.example;
import java.sql.*;

public class MyApplication {
	/*public static void connect() {
		Connection conn = null;
		try {
			// db parameters
			String url = "jdbc:sqlite:C:/Users/root/Downloads/ellipsys_test_db/ellipsys_test_db.db3";
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}*/
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		MyApplication app = new MyApplication();

		//app.createNewTable("id");
		//app.insert("id", "test");
		app.selectAll();
	}
	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Users/root/Downloads/ellipsys_test_db/ellipsys_test_db.db3";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void selectAll(){
		//String sql = "SELECT table_name FROM information_schema.tables WHERE table_type='BASE TABLE'";
		String sql = "SELECT *  FROM oa_trf_src";
		//String sql = "SELECT *  FROM oa_trf_src_id_lkp";
		try (Connection conn = this.connect();
			 Statement stmt  = conn.createStatement();
			 ResultSet rs    = stmt.executeQuery(sql)){

			// loop through the result set
			while (rs.next()) {
				System.out.println(
						//rs.getString("id")+"\t - " +rs.getString("champ"));
				System.out.println(rs.getString("id") +  "\t - " +
						rs.getString("trf") + "\t - " +
						rs.getString("tgtTb")+ "\t - " +
						rs.getString("tgtLab")+ "\t - " +
						rs.getString("srcTb")+ "\t - " +
						rs.getString("srcLab")+ "\t - " +
						rs.getString("impact"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createNewTable(String colName) {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Users/root/Downloads/ellipsys_test_db/ellipsys_test_db.db3";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS oa_trf_src_"+colName+"_lkp (\n"
				+ "	id integer PRIMARY KEY,\n"
				+ "	champ text NOT NULL\n"
				+ ");";

		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insert(String colName, String champ) {
		String sql = "INSERT INTO oa_trf_src_"+colName+"_lkp(champ) VALUES(?)";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, champ);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}


