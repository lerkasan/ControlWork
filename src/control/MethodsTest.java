package control;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.Test;

public class MethodsTest {

	@Test
	public void getAgeTest() {
		Employee emp = new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 0, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
		assertEquals(20, emp.getAge());
	}
	
	@Test
	public void getIncomeTest() {
		Employee emp = new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 0, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
		assertEquals(8648.08, emp.getIncome(), 0.00001);
	}
	
	@Test
	public void loadFromDBTest() throws SQLException {
		List<Employee> employeeList = Employee.loadAverageMaleFromDB();
		Employee emp = employeeList.get(0);
		assertEquals(2, emp.getId());
	}
	
	@Test
	public void sortByExperienceTest() throws SQLException {
		List<Employee> employeeList = Employee.loadAverageMaleFromDB();
		Employee.sortAverageMaleByExperience(employeeList);
		Employee emp = employeeList.get(0);
		assertEquals(4, emp.getId());
	}

}
