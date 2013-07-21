package com.softwareag.eda.nerv;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.softwareag.eda.nerv.consumer.FilteredConsumer;
import com.softwareag.eda.nerv.subscribe.subscription.DefaultSubscription;
import com.softwareag.eda.nerv.subscribe.subscription.Subscription;
import com.softwareag.eda.nerv.task.ContinuousPublishTask;

public class NERVContinuousEmittingUnitTest extends AbstractNERVUnitTest {

	private static final Logger logger = Logger.getLogger(NERVContinuousEmittingUnitTest.class);

	private final String message = "myContMsg";

	private final ContinuousPublishTask publisher = new ContinuousPublishTask(type, message, 500);

	private final FilteredConsumer consumer = new FilteredConsumer(message);

	private Thread runner;

	private Subscription subscription;

	@Before
	public void before() throws Exception {
		subscription = new DefaultSubscription(type, consumer)
		NERV.instance().subscribe(subscription);
		runner = new Thread(publisher);
		runner.start();
	}

	@After
	public void after() throws Exception {
		try {
			publisher.stop();
			runner.join();
			assertEquals(publisher.getPublishedMessages(), consumer.getEvents().size());
			logger.debug(String.format("Published and consumed asynchronously %s messages.", publisher.getPublishedMessages()));
		} finally {
			NERV.instance().unsubscribe(subscription);
		}
	}

	@Test
	public void testPubSub10Msgs() throws Exception {
		pubSubUsingFilter(10);
	}

	@Test
	public void testPublish100Msgs() throws Exception {
		for (int count = 0; count < 100; count++) {
			NERV.instance().publish("testPublish", message);
		}
	}

}
