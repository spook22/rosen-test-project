package com.softwareag.eda.nerv.direct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softwareag.eda.nerv.NERV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/beans.xml" })
public class DirectTest {

	@Test
	public void test() {
		NERV.instance().getDefaultConnection().publish("test", "test");
	}

}
