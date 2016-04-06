package control;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeSettersTest {
	Employee emp;

	@Before
	public void setUp() throws Exception {
		emp = new Employee("John", "Smith", Genders.MALE, LocalDate.of(1995, 8, 16), 0, LocalDate.of(2015, 2, 12), "Manager", 7500.5, 15.3);
	}

	@Test
	public void FirstNameIsSet() {
		emp.setFirstName("Jim");
		assertEquals("Jim",emp.getFirstName());
	}
	
	@Test
	public void LastNameIsSet() {
		emp.setLastName("Thompson");
		assertEquals("Thompson",emp.getLastName());
	}

}
