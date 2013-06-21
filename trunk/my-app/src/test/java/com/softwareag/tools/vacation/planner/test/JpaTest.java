package com.softwareag.tools.vacation.planner.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.softwareag.tools.vacation.planner.model.Person;

public class JpaTest {

	private static final Logger LOGGER = Logger.getLogger(JpaTest.class);

	private static final String PERSISTENCE_UNIT_NAME = "vacation.planner";

	private static void info(Object message) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}

	private EntityManagerFactory factory;

	private EntityManager manager;

	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		manager = factory.createEntityManager();
	}

	@After
	public void tearDown() {
		manager.close();
		factory.close();
	}

	private void showPeople() {
		// Read the existing entries and write to console
		Query query = manager.createQuery("select p from Person p");
		@SuppressWarnings("unchecked")
		List<Person> people = query.getResultList();
		for (Person person : people) {
			info(person);
		}
		info("Size: " + people.size());
	}

	@Test
	public void test() {
		showPeople();
		try {
			// Create new Person
			manager.getTransaction().begin();
			Person newPerson = new Person();
			newPerson.setName("Rosen Spasov");
			manager.persist(newPerson);
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			manager.getTransaction().rollback();
		}
		showPeople();
	}

}
