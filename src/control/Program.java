package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class Program {
	public static final String NEGATIVEEXPERIENCE = "Experience can't be negative number.";
	public static final String NULLDATE = "Birthday and hiredDate dates can't be null";
	public static final String FUTUREDATE = "Given birthday or hiredDate argument is in the future.";
	public static final String TOOYOUNGEMPLOYEE = "Employee can't be yonger than 16 years old.";
	public static final String HIREDDATEBEFOREBITHDAY = "Hired date can't be before Birth date.";
	public static final String NEGATIVESALARY = "Salary can't be negative number or zero.";
	public static final String NEGATIVEBONUS = "Bonus can't be negative number.";
	protected static Logger logger = Logger.getAnonymousLogger();

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
		List<Employee> employeeList = Employee.loadAverageMaleFromDB();
		Employee.sortAverageMaleByExperience(employeeList);
		Employee.saveIncomeReportToFile(employeeList, "report.txt");
		Employee.printSalarySumReport();
		
		Map<Integer, Employee2> employeeMap = Employee2.loadAverageMaleFromDB();
		Employee2.sortMapByExperience(employeeMap);
		Employee2.saveIncomeReportToFile(employeeMap, "report2.txt");
	}
}
