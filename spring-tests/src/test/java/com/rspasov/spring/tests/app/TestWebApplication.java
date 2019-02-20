package com.rspasov.spring.tests.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestWebApplication.LocalTestConfiguration.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class TestWebApplication {

    private static Logger logger = LoggerFactory.getLogger(TestWebApplication.class);

    @LocalServerPort
    private int localServerPort;

    @Test
    public void testLocalServer() {
        logger.info("Local server port: {}", localServerPort);
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    public static class LocalTestConfiguration {

        @Bean
        public TestController aboutController() {
            return new TestController();
        }
    }

    @RestController
    public static class TestController {

        @GetMapping(path = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
        public Mono<String> get() {
            return Mono.just("Test successful.");
        }

        public TestController() {
            logger.info("Test controller loaded");
        }

    }

}

