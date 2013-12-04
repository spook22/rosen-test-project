package rspasov.vacation.planner.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VacationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Date from;

	private Date to;

	private int workingDays;

	protected VacationRequest() {
	}

	public long getId() {
		return id;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public int getWorkingDays() {
		return workingDays;
	}

}
