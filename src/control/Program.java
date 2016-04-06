package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Program {

	private Program() {
	}

	public static Connection getConnection() {
		Connection conn = null;
		Properties props = new Properties();
		try (InputStreamReader in = new InputStreamReader(new FileInputStream("appProperties.txt"), "UTF-8")) {
			props.load(in);
			String connString = props.getProperty("DBConnectionString");
			conn = DriverManager.getConnection(connString);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			e.getSQLState();
			e.getErrorCode();
			e.getMessage();
			e.getCause();
		}
		return conn;
	}

	public static void main(String[] args) {

	}
}
