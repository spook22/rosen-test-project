package com.softwareag.camel.um;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;

import com.pcbsys.nirvana.client.nChannel;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nChannelNotFoundException;
import com.pcbsys.nirvana.client.nSession;

@UriEndpoint(scheme = "um", consumerClass = UmConsumer.class)
public class UmEndpoint extends DefaultEndpoint {

	protected static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	protected static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	protected final boolean topic;

	public UmEndpoint(String endpointUri, UmComponent component) {
		super(endpointUri, component);
		if (getEndpointUri().indexOf("://queue:") > -1) {
			topic = false;
		} else if (getEndpointUri().indexOf("://topic:") > -1) {
			topic = true;
		} else {
			throw new IllegalArgumentException("Endpoint URI unsupported: " + endpointUri);
		}
	}

	@Override
	public UmComponent getComponent() {
		return (UmComponent) super.getComponent();
	}

	@Override
	public Producer createProducer() throws Exception {
		return new UmProducer(this);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		UmConsumer consumer = new UmConsumer(this, processor);
		configureConsumer(consumer);
		return consumer;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	protected String getDestinationName() {
		String answer = getEndpointUri().substring(getEndpointUri().lastIndexOf(":") + 1);
		if (answer.indexOf("?") > -1) {
			answer = answer.substring(0, answer.lastIndexOf("?"));
		}
		return answer;
	}

	protected nChannel getChannel() throws Exception {
		nSession session = getComponent().createSession();
		nChannelAttributes channelAttributes = new nChannelAttributes(getDestinationName());
		try {
			return session.findChannel(channelAttributes);
		} catch (nChannelNotFoundException e) {
			channelAttributes.setTTL(10000);
			channelAttributes.setType(nChannelAttributes.MIXED_TYPE);
			return session.createChannel(channelAttributes);
		}
	}

}
