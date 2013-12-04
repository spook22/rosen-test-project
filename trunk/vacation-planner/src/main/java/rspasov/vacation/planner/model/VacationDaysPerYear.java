package rspasov.vacation.planner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VacationDaysPerYear {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int year;
	
	private int days;

	protected VacationDaysPerYear() {
	}

	public long getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public int getDays() {
		return days;
	}

}
