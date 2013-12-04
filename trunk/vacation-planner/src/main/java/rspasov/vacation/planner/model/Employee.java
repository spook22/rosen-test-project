package rspasov.vacation.planner.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Employee {

	private static Logger LOGGER = LoggerFactory.getLogger(Employee.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstName;

	private String lastName;

	@ElementCollection
	private List<VacationDaysPerYear> totalVacationDaysPerYear;

	@ElementCollection
	private List<VacationRequest> vacationRequests;

	protected Employee() {
	}

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<VacationDaysPerYear> getTotalVacationDaysPerYear() {
		return totalVacationDaysPerYear;
	}

	public List<VacationRequest> getVacationRequests() {
		return vacationRequests;
	}

	public int calculateAvailableVacationDays() {
		LOGGER.info("Calculating vacation days.");
		int result = 0;
		for (VacationDaysPerYear vacationDaysPerYear : totalVacationDaysPerYear) {
			result += vacationDaysPerYear.getDays();
		}
		for (VacationRequest vacationRequest : vacationRequests) {
			result -= vacationRequest.getWorkingDays();
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format("Employee[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}

}
