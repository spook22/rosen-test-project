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

import rspasov.vacation.planner.model.Customer;
import rspasov.vacation.planner.repository.CustomerRepository;

@Controller
public class CustomerController {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Resource
	private CustomerRepository repository;

	@PostConstruct
	public void init() {
		LOGGER.info("Initialized CustomerController.");

		// save a couple of customers
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));
		repository.save(new Customer("David", "Palmer"));
		repository.save(new Customer("Michelle", "Dessler"));

		// fetch all customers
		Iterable<Customer> customers = repository.findAll();
		LOGGER.info("Customers found with findAll():");
		LOGGER.info("-------------------------------");
		for (Customer customer : customers) {
			LOGGER.info(customer.toString());
		}

		// fetch an individual customer by ID
		Customer customer = repository.findOne(1L);
		LOGGER.info("Customer found with findOne(1L):");
		LOGGER.info("--------------------------------");
		LOGGER.info(customer.toString());

		// fetch customers by last name
		List<Customer> bauers = repository.findByLastName("Bauer");
		LOGGER.info("Customer found with findByLastName('Bauer'):");
		LOGGER.info("--------------------------------------------");
		for (Customer bauer : bauers) {
			LOGGER.info(bauer.toString());
		}
	}

	@RequestMapping(value = "/customer/{lastName}", method = RequestMethod.GET)
	public @ResponseBody
	List<Customer> greeting(@PathVariable String lastName) throws Exception {
		LOGGER.info("Processing request for parameters: " + lastName);
		return repository.findByLastName(lastName);
	}

	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception exc, HttpServletRequest request) {
		return "/ErrorView"; // TODO This has to be implemented.
	}
}