package rspasov.reactive;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class ReactorTest {

	@Test
	public void test() {
		Mono.just("Test").subscribe(System.out::println);
	}

}
