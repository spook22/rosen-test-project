package com.rspasov.spring.tests.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestWebApplication {

    private static Logger logger = LoggerFactory.getLogger(TestWebApplication.class);

    @LocalServerPort
    private int localServerPort;

    @Test
    public void testLocalServer() {
        logger.info("Local server port: {}", localServerPort);
    }

}

