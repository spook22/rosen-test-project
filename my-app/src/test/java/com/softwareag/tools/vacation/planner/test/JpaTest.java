package com.softwareag.tools.vacation.planner.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.softwareag.tools.vacation.planner.model.Person;

public class JpaTest {

	private static final Logger LOGGER = Logger.getLogger(JpaTest.class);

	private static final String PERSISTENCE_UNIT_NAME = "vacation.planner";

	private void info(Object message) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(message);
		}
	}

	private void showPeople(EntityManager em) {
		// Read the existing entries and write to console
		Query query = em.createQuery("select p from Person p");
		@SuppressWarnings("unchecked")
		List<Person> people = query.getResultList();
		for (Person person : people) {
			info(person);
		}
		info("Size: " + people.size());
	}

	@Test
	public void test() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		try {
			EntityManager em = factory.createEntityManager();
			showPeople(em);
			try {
				// Create new Person
				em.getTransaction().begin();
				Person newPerson = new Person();
				newPerson.setName("Rosen Spasov");
				em.persist(newPerson);
				em.getTransaction().commit();
			} catch (RuntimeException e) {
				em.getTransaction().rollback();
			}
			showPeople(em);
		} finally {
			factory.close();
		}
	}

}
