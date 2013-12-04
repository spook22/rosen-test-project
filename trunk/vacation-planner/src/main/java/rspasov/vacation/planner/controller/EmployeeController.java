package rspasov.vacation.planner.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rspasov.vacation.planner.model.Employee;
import rspasov.vacation.planner.repository.EmployeeRepository;

@Controller
public class EmployeeController {

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Resource
	private EmployeeRepository repository;

	@PostConstruct
	public void init() {
		LOGGER.info("Initialized EmployeeController.");

		// save a couple of employees
		repository.save(new Employee("Jack", "Bauer"));
		repository.save(new Employee("Chloe", "O'Brian"));
		repository.save(new Employee("Kim", "Bauer"));
		repository.save(new Employee("David", "Palmer"));
		repository.save(new Employee("Michelle", "Dessler"));

		// fetch all employees
		Iterable<Employee> employees = repository.findAll();
		LOGGER.info("Employee found with findAll():");
		LOGGER.info("-------------------------------");
		for (Employee employee : employees) {
			LOGGER.info(employee.toString());
		}

		// fetch an individual employee by ID
		Employee employee = repository.findOne(1L);
		LOGGER.info("Employee found with findOne(1L):");
		LOGGER.info("--------------------------------");
		LOGGER.info(employee.toString());

		// fetch employees by last name
		List<Employee> bauers = repository.findByLastName("Bauer");
		LOGGER.info("Employee found with findByLastName('Bauer'):");
		LOGGER.info("--------------------------------------------");
		for (Employee bauer : bauers) {
			LOGGER.info(bauer.toString());
		}
	}

	@RequestMapping(value = "/employee/{lastName}", method = RequestMethod.GET)
	public @ResponseBody
	List<Employee> greeting(@PathVariable String lastName) throws Exception {
		LOGGER.info("Processing request for parameters: " + lastName);
		return repository.findByLastName(lastName);
	}

	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception exc, HttpServletRequest request) {
		return "/ErrorView"; // TODO This has to be implemented.
	}
}