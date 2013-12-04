package rspasov.vacation.planner.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstName;

	private String lastName;

	private List<VacationDaysPerYear> totalVacationDaysPerYear;

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

	public int getAvailableVacationDays() {
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
