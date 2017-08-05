package rspasov.junit;

import java.util.concurrent.TimeUnit;

import org.junit.ClassRule;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

@RunWith(ClasspathSuite.class)
@ClassnameFilters({ "rspasov.junit.*" })
public class TestSuite {

	@ClassRule
	public static Timeout suiteTimeout = new Timeout(6, TimeUnit.SECONDS);

}
