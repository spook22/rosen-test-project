package com.rspasov.spring.tests.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class TestApplication {

    private static Logger logger = LoggerFactory.getLogger(TestApplication.class);

    public static void main(String[] args) {
        logger.info("Starting test application...");
        SpringApplication.run(TestApplication.class, args);
    }

    @Component
    public class GreetingHandler {
        public Mono<ServerResponse> hello(ServerRequest request) {
            return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                    .body(BodyInserters.fromObject("Hello, Spring!"));
        }
    }

    @Configuration
    public class GreetingRouter {
        @Bean
        public RouterFunction<ServerResponse> route(
                TestApplication.GreetingHandler greetingHandler) {
            return RouterFunctions.route(RequestPredicates
                    .GET("/hello")
                    .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                    greetingHandler::hello);
        }
    }

}
