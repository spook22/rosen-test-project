package org.istabg.camel.examples;

import org.apache.camel.Exchange;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class BlueprintTest extends CamelBlueprintTestSupport {
	 
    private boolean debugBeforeMethodCalled;
    private boolean debugAfterMethodCalled;
 
    // override this method, and return the location of our Blueprint XML file to be used for testing
    @Override
    protected String getBlueprintDescriptor() {
        return "org/istabg/camel/examples/blueprint-camel-context.xml";
    }
    
    // here we have regular JUnit @Test method
    @Test
    public void testRoute() throws Exception {
 
        // set mock expectations
        getMockEndpoint("mock:a").expectedMessageCount(1);
 
        // send a message
        template.sendBody("direct:start", "World");
 
        // assert mocks
        assertMockEndpointsSatisfied();
 
        // assert on the debugBefore/debugAfter methods below being called as we've enabled the debugger
        assertTrue(debugBeforeMethodCalled);
        assertTrue(debugAfterMethodCalled);
    }
 
    @Override
    public boolean isUseDebugger() {
        // must enable debugger
        return true;
    }
 
    @Override
    protected void debugBefore(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label) {
        log.info("Before " + definition + " with body " + exchange.getIn().getBody());
        debugBeforeMethodCalled = true;
    }
 
    @Override
    protected void debugAfter(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label, long timeTaken) {
        log.info("After " + definition + " with body " + exchange.getIn().getBody());
        debugAfterMethodCalled = true;
    }
}