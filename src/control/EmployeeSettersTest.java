package control;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class EmployeeSettersTest {
	Employee emp;

	@Before
	public void setUp() throws Exception {
		emp = new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 0, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}

	@Test
	public void firstNameIsSet() {
		emp.setFirstName("Jim");
		assertEquals("Jim",emp.getFirstName());
	}
	
	@Test
	public void lastNameIsSet() {
		emp.setLastName("Thompson");
		assertEquals("Thompson",emp.getLastName());
	}
	
	@Test
	public void genderIsSet() {
		emp.setGender(Genders.FEMALE);
		assertEquals(Genders.FEMALE,emp.getGender());
	}
	
	@Test
	public void birthdayIsSet() {
		emp.setBirthday(LocalDate.of(1991, 8, 16));
		assertEquals(LocalDate.of(1991, 8, 16),emp.getBirthday());
	}
	
	@Test
	public void hiredDateIsSet() {
		emp.setHiredDate(LocalDate.of(2015, 8, 16));
		assertEquals(LocalDate.of(2015, 8, 16),emp.getHiredDate());
	}
	
	@Test
	public void positionIsSet() {
		emp.setPosition("Accountant");
		assertEquals("Accountant",emp.getPosition());
	}
	
	@Test
	public void salaryIsSet() {
		emp.setSalary(12345.67);
		assertEquals(12345.67,emp.getSalary(), 0.00001);
	}
	
	@Test
	public void bonusIsSet() {
		emp.setBonus(25.3);
		assertEquals(25.3,emp.getBonus(), 0.00001);
	}
	
	@Test(expected = NullPointerException.class)
	public void setFirstNameNullExceptionTest() {
		emp.setFirstName(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void setLastNameNullExceptionTest() {
		emp.setLastName(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void setGenderNullExceptionTest() {
		emp.setGender(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void setBirthdayNullExceptionTest() {
		emp.setBirthday(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setFutureBirthdayExceptionTest() {
		emp.setBirthday(LocalDate.of(2025, 4, 5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setExperienceNegativeExceptionTest() {
		emp.setExperience(-5);
	}
	
	@Test(expected = NullPointerException.class)
	public void setHiredDateNullExceptionTest() {
		emp.setHiredDate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setFutureHiredDateExceptionTest() {
		emp.setHiredDate(LocalDate.of(2025, 4, 5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setHiredDateBeforeBirthdayExceptionTest() {
		emp.setHiredDate(LocalDate.of(1975, 4, 5));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setHiredDateTooYoungEmployeeExceptionTest() {
		emp.setHiredDate(LocalDate.of(2005, 4, 5));
	}
	
	@Test(expected = NullPointerException.class)
	public void setPositionNullExceptionTest() {
		emp.setPosition(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setSalaryNegativeExceptionTest() {
		emp.setSalary(-5000.45);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setBonusNegativeExceptionTest() {
		emp.setBonus(-5);
	}
	
	

}
