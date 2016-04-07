package control;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class EmployeeConstructorTest {

	@Test
	public void generalTest() { 
		Employee emp = new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 0, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
		assertEquals("John", emp.getFirstName());
		assertEquals("Smith", emp.getLastName());
		assertEquals(Genders.MALE, emp.getGender());
		assertEquals(LocalDate.of(1995, 8, 16), emp.getBirthday());
		assertEquals(0, emp.getExperience());
		assertEquals(LocalDate.of(2015, 2, 12), emp.getHiredDate());
		assertEquals("Manager", emp.getPosition());
		assertEquals(7500.5, emp.getSalary(), 0.00001);
		assertEquals(15.3, emp.getBonus(), 0.00001);
	}
	
	@Test(expected = NullPointerException.class)
	public void firstNameExceptionTest() {
		new Employee(null, "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}

	@Test(expected = NullPointerException.class)
	public void lastNameExceptionTest() {
		new Employee("John", null, Genders.MALE, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void genderExceptionTest() {
		new Employee("John", "Smith", null, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void birthdayNullExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, null, 5, LocalDate.of(1995, 8, 16), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void birthdayFutureExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(2025, 2, 12), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void hiredDateNullExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 5, null, "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hiredDateFutureExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 5, LocalDate.of(2025, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void hiredDateIsBeforeBirthdayExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(2015, 2, 12), 5, LocalDate.of(1985, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void employeeYoungerTooYoungExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(2000, 2, 12), 5, LocalDate.of(2015, 3, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void experienceNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), -15, LocalDate.of(2025, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void salaryNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 15, LocalDate.of(2025, 2, 12), "Manager", -7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void bonusNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 15, LocalDate.of(2025, 2, 12), "Manager", 7500.5, -15.3);
	}
}
