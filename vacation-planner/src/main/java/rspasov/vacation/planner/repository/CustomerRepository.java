package rspasov.vacation.planner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rspasov.vacation.planner.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
