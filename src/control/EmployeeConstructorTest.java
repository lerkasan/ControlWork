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
	public void FirstNameExceptionTest() {
		new Employee(null, "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}

	@Test(expected = NullPointerException.class)
	public void LastNameExceptionTest() {
		new Employee("John", null, Genders.MALE, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void GenderExceptionTest() {
		new Employee("John", "Smith", null, LocalDate.of(1995, 8, 16), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void BirthdayNullExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, null, 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void BirthdayFutureExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(2025, 2, 12), 5, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = NullPointerException.class)
	public void HiredDateNullExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 5, null, "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void HiredDateFutureExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 5, LocalDate.of(2025, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void HiredDateIsBeforeBirthdayExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(2015, 2, 12), 5, LocalDate.of(2001, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void ExperienceNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), -15, LocalDate.of(2025, 2, 12), "Manager", 7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void SalaryNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 15, LocalDate.of(2025, 2, 12), "Manager", -7500.5, 15.3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void BonusNegativeExceptionTest() {
		new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 2, 12), 15, LocalDate.of(2025, 2, 12), "Manager", 7500.5, -15.3);
	}
}
