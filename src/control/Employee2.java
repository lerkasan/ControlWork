package control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Employee2 implements Comparable<Employee2> {
	private int id;
	private String firstName;
	private String lastName;
	private Genders gender;
	private LocalDate birthday;
	private int age;
	private int experience;
	private LocalDate hiredDate;
	private String position;
	private double salary;
	private double bonus;
	private double income;
	
	public Employee2() {
		id = -1;
	}

	public Employee2(String firstName, String lastName, Genders gender, LocalDate birthday, int experience,
			LocalDate hiredDate, String position, double salary, double bonus) {
		LocalDate now = LocalDate.now();
		this.firstName = Objects.requireNonNull(firstName, "First name must not be null");
		this.lastName = Objects.requireNonNull(lastName, "Last name must not be null");
		this.gender = Objects.requireNonNull(gender, "Gender must not be null");
		if (experience >= 0) {
			this.experience = experience;
		} else {
			throw new IllegalArgumentException("Experience can't be negative number.");
		}
		if ((birthday == null) || (hiredDate == null)) {
			throw new NullPointerException("Birthday and hiredDate dates can't be null.");
		}
		if (birthday.isAfter(now) || hiredDate.isAfter(now)) {
			throw new IllegalArgumentException("Given birthday or hiredDate argument is in the future.");
		}		
		if (birthday.until(hiredDate, ChronoUnit.YEARS) < 16) {
			throw new IllegalArgumentException("Employee can't be yonger than 16 years old.");
		}
		if (birthday.isBefore(hiredDate)) {
			this.birthday = birthday;
			this.hiredDate = hiredDate;
		} else {
			throw new IllegalArgumentException("Hired date can't be before Birth date.");
		}
		age = (int)this.getAge();
		this.position = Objects.requireNonNull(position, "Position must not be null");
		if (salary > 0) {
			this.salary = salary;
		} else {
			throw new IllegalArgumentException("Salary can't be negative number or zero.");
		}
		if (bonus >= 0) {
			this.bonus = bonus;
		} else {
			throw new IllegalArgumentException("Bonus can't be negative number.");
		}
		income = this.getIncome();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = Objects.requireNonNull(firstName, "First name must not be null");
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = Objects.requireNonNull(lastName, "Last name must not be null");
	}

	public Genders getGender() {
		return gender;
	}

	public void setGender(Genders gender) {
		this.gender = Objects.requireNonNull(gender, "Gender must not be null");
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		LocalDate now = LocalDate.now();
		if (birthday == null) {
			throw new NullPointerException("Birthday can't be null.");
		}
		if (birthday.isAfter(now)) {
			throw new IllegalArgumentException("Given birthday argument is in the future.");
		}
		if ((hiredDate != null) && (birthday.until(hiredDate, ChronoUnit.YEARS) < 16)) {
			throw new IllegalArgumentException("Employee can't be yonger than 16 years old.");
		}
		if (hiredDate == null) {
			this.birthday = birthday;
		} else if (birthday.isBefore(hiredDate)) {
				this.birthday = birthday;
		} else {
			throw new IllegalArgumentException("Birth date should be before Hired date.");
		}
	}
	
	public int getAge() {
		age = (int) birthday.until(LocalDate.now(), ChronoUnit.YEARS);
		return age;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public LocalDate getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(LocalDate hiredDate) {
		LocalDate now = LocalDate.now();
		if (hiredDate == null) {
			throw new NullPointerException("hiredDate can't be null.");
		}
		if (hiredDate.isAfter(now)) {
			throw new IllegalArgumentException("Given hiredDate argument is in the future.");
		}
		if ((birthday != null) && (birthday.until(hiredDate, ChronoUnit.YEARS) < 16)) {
			throw new IllegalArgumentException("Employee can't be yonger than 16 years old.");
		}
		if (birthday == null) {
			this.hiredDate = hiredDate;
		} else if (birthday.isBefore(hiredDate)) {
			this.hiredDate = hiredDate;
		} else {
			throw new IllegalArgumentException("hiredDate should be after Birth date.");
		}
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) { 
		this.position = Objects.requireNonNull(position, "Position must not be null");
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	public double getIncome() {
		double procbonus = 1 + bonus / 100;
		int temp = (int)Math.round(salary * procbonus * 100);
		income = (double)temp / 100;
		return income;
	}
	
	public static Map<Integer, Employee2> loadAverageMaleFromDB() {
		ResultSet rs;
		Connection con = Program.getConnection();
		Map<Integer, Employee2> employeeMap = new HashMap<>();
		try (Statement stm = con.createStatement()) {
			String sql = "select e.id, e.firstName, e.lastName, e.gender, e.birthDay, {fn timestampdiff(SQL_TSI_YEAR, e.birthDay, CURRENT_DATE)} as age, " + 
	                 "e.experience, e.hiredDate, p.position, e.salary, e.bonus, (e.salary + e.salary*e.bonus/100) as income " +
				     "from employee e left outer join positions p on e.position = p.id " +
				     "where (e.salary < 15000) and (e.gender = '0') " +
				     "order by e.experience";
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Employee2 emp = new Employee2();
				emp.setId(rs.getInt("id"));
				emp.setFirstName(rs.getString("firstName"));
				emp.setLastName(rs.getString("lastName"));
				emp.setGender(Genders.values()[rs.getInt("gender")]);
				emp.setBirthday(rs.getDate("birthday").toLocalDate());
				emp.age = rs.getInt("age");
				emp.setExperience(rs.getInt("experience"));
				emp.setHiredDate(rs.getDate("hiredDate").toLocalDate());
				emp.setPosition(rs.getString("position"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setBonus(rs.getDouble("bonus"));
				emp.income = rs.getDouble("income");
				employeeMap.put(emp.getId(), emp);
				}
			rs.close();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				e.getSQLState();
				e.getErrorCode();
				e.getMessage();
				e.getCause();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			e1.getSQLState();
			e1.getErrorCode();
			e1.getMessage();
			e1.getCause();
		}
		return employeeMap;
	}
	
	@Override
	public String toString() {
		if (id == -1) {
			return "";
		}
		Formatter aFormat = new Formatter();
		String result = aFormat.format("|   %1$6d   |   %2$25s   |   %3$25s   |   %4$5d   |   %5$5d   |   %6$25s   |   %7$12.2f   |%n", 
				id, firstName, lastName, age, experience, position, income).toString();
		aFormat.close();
		return result;
	}
	
	public static void saveIncomeReportToFile(Map<Integer, Employee2> employeeMap, String filePath) { 
		try (PrintWriter outputWriter = new PrintWriter(new FileWriter(filePath))) {
			outputWriter.println("|    id      |         First Name            |            Last Name          |    Age    |Experience |           Position            |      Income      |");
			for (Map.Entry<Integer, Employee2> entry : employeeMap.entrySet()) {
				outputWriter.print(entry.getValue());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int compareTo(Employee2 emp) {
		if (this.getLastName().compareTo(emp.getLastName()) > 0) {
			return 1;
		} else if (this.getLastName().compareTo(emp.getLastName()) < 0) {
			return -1;
		}
		return 0;
	}
	
	public static class EmployeeByExperienceComparatorForList implements Comparator<Employee2> {
		
		@Override
		public int compare(Employee2 emp1, Employee2 emp2) {
			if (emp1.getExperience() - emp2.getExperience() > 0) {
				return 1;
			} else if (emp1.getExperience() - emp2.getExperience() < 0) {
				return -1;
			}
			return 0;
		}
	}
	
	public static class EmployeeByExperienceComparatorForEntries implements Comparator<Map.Entry<Integer, Employee2>> {
		@Override
		public int compare(Map.Entry<Integer, Employee2> entry1, Map.Entry<Integer, Employee2> entry2) {
			if (entry1.getValue().getExperience() - entry2.getValue().getExperience() > 0) {
				return 1;
			} else if (entry1.getValue().getExperience() - entry2.getValue().getExperience() < 0) {
				return -1;
			}
			return 0;
		}
	}
	
	public static List<Employee2> sortListByExperience(List<Employee2> employeeList) {
		if (employeeList != null) {
			Collections.sort(employeeList, new EmployeeByExperienceComparatorForList());
		}
		return employeeList;
	}
	
	public static Map<Integer, Employee2> sortMapByExperience(Map<Integer, Employee2> unsortMap) {
		// Convert Map to List
		List<Map.Entry<Integer, Employee2>> mapEntriesList = new ArrayList<>(unsortMap.entrySet());
		// Sort list with comparator, to compare the Map values
		Collections.sort(mapEntriesList, new EmployeeByExperienceComparatorForEntries());
		// Convert sorted map back to a Map
		Map<Integer, Employee2> sortedMap = new LinkedHashMap<>();
		for (Iterator<Map.Entry<Integer, Employee2>> it = mapEntriesList.iterator(); it.hasNext();) {
			Map.Entry<Integer, Employee2> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	public static void printSalarySumReport() {
		ResultSet rs;
		double sumSalary;
		String position;
		String result;
		Connection con = Program.getConnection();
		try (Statement stm = con.createStatement()) {
			String sql = "select p.position, sum(e.salary) as sumSalary " +
					     "from employee e left outer join positions p on e.position = p.id " +
					     "group by p.position order by sumSalary ";
			rs = stm.executeQuery(sql);
			System.out.println("|           Position            |      Total salary sum      |");
			while (rs.next()) {
				Formatter aFormat = new Formatter();
				position = rs.getString("position");
				sumSalary = rs.getDouble("sumSalary");
				result = aFormat.format("|   %1$25s   |     %2$20.2f   |", position, sumSalary).toString();
				System.out.println(result);
				aFormat.close();
				}
			rs.close();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				e.getSQLState();
				e.getErrorCode();
				e.getMessage();
				e.getCause();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			e1.getSQLState();
			e1.getErrorCode();
			e1.getMessage();
			e1.getCause();
		}
	}
}