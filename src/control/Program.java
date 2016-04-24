package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class Program {
	public static final String NEGATIVE_EXPERIENCE = "Experience can't be negative number.";
	public static final String NULL_DATE = "Birthday and hiredDate dates can't be null";
	public static final String FUTURE_DATE = "Given birthday or hiredDate argument is in the future.";
	public static final String TOO_YOUNG_EMPLOYEE = "Employee can't be yonger than 16 years old.";
	public static final String HIRED_DATE_BEFORE_BITHDAY = "Hired date can't be before Birth date.";
	public static final String NEGATIVE_SALARY = "Salary can't be negative number or zero.";
	public static final String NEGATIVE_BONUS = "Bonus can't be negative number.";
	protected static Logger logger = Logger.getAnonymousLogger();
	private static String connString;
	
	static {
		Properties props = new Properties();
		try (InputStreamReader in = new InputStreamReader(new FileInputStream("appProperties.txt"), "UTF-8")) {
			props.load(in);
			connString = props.getProperty("DBConnectionString");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getConnString() {
		return connString;
	}

	private Program() {
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(getConnString());
		return conn;
	}

	public static void main(String[] args) throws SQLException {
		/*List<Employee> employeeList = Employee.loadAverageMaleFromDB();
		Employee.sortAverageMaleByExperience(employeeList);
		Employee.saveIncomeReportToFile(employeeList, "report.txt");
		Employee.printSalarySumReport();*/
		
		Map<Integer, Employee2> employeeMap = Employee2.loadAverageMaleFromDB();
		Map<Integer,Employee2> sortedResultMap = Employee2.sortMapByExperience(employeeMap);
		Employee2.saveIncomeReportToFile(sortedResultMap, "report2.txt");
	}
}
