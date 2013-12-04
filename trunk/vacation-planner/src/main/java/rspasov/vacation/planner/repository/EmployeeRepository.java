package rspasov.vacation.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rspasov.vacation.planner.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	List<Employee> findByLastName(String lastName);
}
