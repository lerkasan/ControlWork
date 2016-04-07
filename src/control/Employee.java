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
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

enum Genders {MALE, FEMALE};

public class Employee implements Comparable<Employee> {
	private int id;
	private String firstName;
	private String lastName;
	private Genders gender;
	private LocalDate birthday;
	private int experience;
	private LocalDate hiredDate;
	private String position;
	private double salary;
	private double bonus;
	
	public Employee() {
		id = -1;
	}

	public Employee(String firstName, String lastName, Genders gender, LocalDate birthday, int experience,
			LocalDate hiredDate, String position, double salary, double bonus) {
		RuntimeException e;
		LocalDate now = LocalDate.now();
		this.firstName = Objects.requireNonNull(firstName, "First name must not be null");
		this.lastName = Objects.requireNonNull(lastName, "Last name must not be null");
		this.gender = Objects.requireNonNull(gender, "Gender must not be null");
		if (experience >= 0) {
			this.experience = experience;
		} else {
			e = new IllegalArgumentException(Program.NEGATIVEEXPERIENCE);
			Program.logger.log(Level.SEVERE, Program.NEGATIVEEXPERIENCE, e);
			throw e;
		}
		if ((birthday == null) || (hiredDate == null)) {
			e = new NullPointerException(Program.NULLDATE);
			Program.logger.log(Level.SEVERE, Program.NULLDATE, e);
			throw e;
		}
		if (birthday.isAfter(now) || hiredDate.isAfter(now)) {
			e = new IllegalArgumentException(Program.FUTUREDATE);
			Program.logger.log(Level.SEVERE, Program.FUTUREDATE, e);
			throw e;
		}		
		if (birthday.until(hiredDate, ChronoUnit.YEARS) < 16) {
			e = new IllegalArgumentException(Program.TOOYOUNGEMPLOYEE);
			Program.logger.log(Level.SEVERE, Program.TOOYOUNGEMPLOYEE, e);
			throw e;
		}
		if (birthday.isBefore(hiredDate)) {
			this.birthday = birthday;
			this.hiredDate = hiredDate;
		} else {
			e = new IllegalArgumentException(Program.HIREDDATEBEFOREBITHDAY);
			Program.logger.log(Level.SEVERE, Program.HIREDDATEBEFOREBITHDAY, e);
			throw e;
		}
		this.position = Objects.requireNonNull(position, "Position must not be null");
		if (salary > 0) {
			this.salary = salary;
		} else {
			e = new IllegalArgumentException(Program.NEGATIVESALARY);
			Program.logger.log(Level.SEVERE, Program.NEGATIVESALARY, e);
			throw e;
		}
		if (bonus >= 0) {
			this.bonus = bonus;
		} else {
			e = new IllegalArgumentException(Program.NEGATIVEBONUS);
			Program.logger.log(Level.SEVERE, Program.NEGATIVEBONUS, e);
			throw e;
		}
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
		RuntimeException e;
		LocalDate now = LocalDate.now();
		if (birthday == null) {
			e = new NullPointerException(Program.NULLDATE);
			Program.logger.log(Level.SEVERE, Program.NULLDATE, e);
			throw e;
		}
		if (birthday.isAfter(now)) {
			e = new IllegalArgumentException(Program.FUTUREDATE);
			Program.logger.log(Level.SEVERE, Program.FUTUREDATE, e);
			throw e;
		}
		if ((hiredDate != null) && (birthday.until(hiredDate, ChronoUnit.YEARS) < 16)) {
			e = new IllegalArgumentException(Program.TOOYOUNGEMPLOYEE);
			Program.logger.log(Level.SEVERE, Program.TOOYOUNGEMPLOYEE, e);
			throw e;
		}
		if (hiredDate == null) {
			this.birthday = birthday;
		} else if (birthday.isBefore(hiredDate)) {
				this.birthday = birthday;
		} else {
			e = new IllegalArgumentException(Program.HIREDDATEBEFOREBITHDAY);
			Program.logger.log(Level.SEVERE, Program.HIREDDATEBEFOREBITHDAY, e);
			throw e;
		}
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		if (experience >= 0) {
			this.experience = experience;
		} else {
			RuntimeException e = new IllegalArgumentException(Program.NEGATIVEEXPERIENCE);
			Program.logger.log(Level.SEVERE, Program.NEGATIVEEXPERIENCE, e);
			throw e;
		}
	}

	public LocalDate getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(LocalDate hiredDate) {
		RuntimeException e;
		LocalDate now = LocalDate.now();
		if (hiredDate == null) {
			e = new NullPointerException(Program.NULLDATE);
			Program.logger.log(Level.SEVERE, Program.NULLDATE, e);
			throw e;
		}
		if (hiredDate.isAfter(now)) {
			e = new IllegalArgumentException(Program.FUTUREDATE);
			Program.logger.log(Level.SEVERE, Program.FUTUREDATE, e);
			throw e;
		}
		if ((birthday != null) && (birthday.until(hiredDate, ChronoUnit.YEARS) < 16)) {
			e = new IllegalArgumentException(Program.TOOYOUNGEMPLOYEE);
			Program.logger.log(Level.SEVERE, Program.TOOYOUNGEMPLOYEE, e);
			throw e;
		}
		if (birthday == null) {
			this.hiredDate = hiredDate;
		} else if (birthday.isBefore(hiredDate)) {
			this.hiredDate = hiredDate;
		} else {
			e = new IllegalArgumentException(Program.HIREDDATEBEFOREBITHDAY);
			Program.logger.log(Level.SEVERE, Program.HIREDDATEBEFOREBITHDAY, e);
			throw e;
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
		if (salary > 0) {
			this.salary = salary;
		} else {
			RuntimeException e = new IllegalArgumentException(Program.NEGATIVESALARY);
			Program.logger.log(Level.SEVERE, Program.NEGATIVESALARY, e);
			throw e;
		}
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		if (bonus >= 0) {
			this.bonus = bonus;
		} else {
			RuntimeException e = new IllegalArgumentException(Program.NEGATIVEBONUS);
			Program.logger.log(Level.SEVERE, Program.NEGATIVEBONUS, e);
			throw e;
		}
	}
	
	public long getAge() {
		return birthday.until(LocalDate.now(), ChronoUnit.YEARS);
	}
	
	public double getIncome() {
		double procbonus = 1 + bonus / 100;
		int temp = (int)Math.round(salary * procbonus * 100);
		return (double)temp / 100;
	}
	
	public static List<Employee> loadAverageMaleFromDB() {
		ResultSet rs;
		Connection con = Program.getConnection();
		List<Employee> employeeList = new ArrayList<>();
		try (Statement stm = con.createStatement()) {
			String sql = "select e.id, e.firstName, e.lastName, e.gender, e.birthDay, e.experience, e.hiredDate, p.position, e.salary, e.bonus " +
					     "from employee e left outer join positions p on e.position = p.id " +
					     "where (e.salary < 15000) and (e.gender = '0')" ;
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setFirstName(rs.getString("firstName"));
				emp.setLastName(rs.getString("lastName"));
				emp.setGender(Genders.values()[rs.getInt("gender")]);
				emp.setBirthday(rs.getDate("birthday").toLocalDate());
				emp.setExperience(rs.getInt("experience"));
				emp.setHiredDate(rs.getDate("hiredDate").toLocalDate());
				emp.setPosition(rs.getString("position"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setBonus(rs.getDouble("bonus"));
				employeeList.add(emp);
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
		return employeeList;
	}
	
	public static List<Employee> sortAverageMaleByExperience(List<Employee> employeeList) {
		if (employeeList != null) {
			Collections.sort(employeeList, new EmployeeByExperienceComparator());
		}
		return employeeList;
	}
	
	@Override
	public String toString() {
		if (id == -1) {
			return "";
		}
		Formatter aFormat = new Formatter();
		String result = aFormat.format("|   %1$6d   |   %2$25s   |   %3$25s   |   %4$5d   |   %5$5d   |   %6$25s   |   %7$12.2f   |%n", 
				id, firstName, lastName, this.getAge(), experience, position, this.getIncome()).toString();
		aFormat.close();
		return result;
	}
	
	public static void saveIncomeReportToFile(List<Employee> employeeList, String filePath) { 
		try (PrintWriter outputWriter = new PrintWriter(new FileWriter(filePath))) {
			outputWriter.println("|    id      |         First Name            |            Last Name          |    Age    |Experience |           Position            |      Income      |");
			for (Employee emp : employeeList) {
				outputWriter.print(emp);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int compareTo(Employee emp) {
		if (this.getLastName().compareTo(emp.getLastName()) > 0) {
			return 1;
		} else if (this.getLastName().compareTo(emp.getLastName()) < 0) {
			return -1;
		}
		return 0;
	}
	
	public static class EmployeeByExperienceComparator implements Comparator<Employee> {
		
		@Override
		public int compare(Employee emp1, Employee emp2) {
			if (emp1.getExperience() - emp2.getExperience() > 0) {
				return 1;
			} else if (emp1.getExperience() - emp2.getExperience() < 0) {
				return -1;
			}
			return 0;
		}
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
