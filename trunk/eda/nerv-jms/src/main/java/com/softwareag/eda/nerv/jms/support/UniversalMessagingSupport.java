package com.softwareag.eda.nerv.jms.support;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcbsys.nirvana.client.nBaseClientException;
import com.pcbsys.nirvana.client.nChannelAlreadyExistsException;
import com.pcbsys.nirvana.client.nChannelAttributes;
import com.pcbsys.nirvana.client.nSecurityException;
import com.pcbsys.nirvana.client.nSessionAttributes;
import com.pcbsys.nirvana.nAdminAPI.nACL;
import com.pcbsys.nirvana.nAdminAPI.nBaseAdminException;
import com.pcbsys.nirvana.nAdminAPI.nChannelACLEntry;
import com.pcbsys.nirvana.nAdminAPI.nLeafNode;
import com.pcbsys.nirvana.nAdminAPI.nRealmNode;
import com.pcbsys.nirvana.nJMS.ConnectionFactoryImpl;
import com.pcbsys.nirvana.nJMS.DestinationImpl;
import com.pcbsys.nirvana.nJMS.TopicImpl;
import com.softwareag.eda.nerv.NERVException;

public class UniversalMessagingSupport extends AbstractDestinationSupport {

	private static final Logger logger = LoggerFactory.getLogger(UniversalMessagingSupport.class);

	private static final String UM_SUBJECT_EVERYONE = "*@*";

	private static final String UM_JNDI_CONTEXT_CHANNEL_NAME = "/naming/defaultContext";

	public UniversalMessagingSupport(String providerUrl) {
		super(providerUrl);
	}

	@Override
	public void bindTopic(String jndiEntryName) throws NERVException {
		try {
			TopicImpl topic = new TopicImpl(jndiEntryNameToTopicName(jndiEntryName));
			bind(jndiEntryName, topic);
		} catch (Exception e) {
			throw new NERVException("Unable to bind topic " + jndiEntryName + " at '" + providerUrl + "'.", e);
		}
	}

	private void createTopic(final String jndiEntryName, boolean createJndiChannel) {
		final String topicName = jndiEntryNameToTopicName(jndiEntryName);
		nRealmNode node = null;
		try {
			nSessionAttributes sessionAttributes = new nSessionAttributes(providerUrl);
			node = new nRealmNode(sessionAttributes);
			node.waitForEntireNameSpace();

			nACL channelAcl = nLeafNode.createACL();
			nChannelACLEntry everyone = new nChannelACLEntry(UM_SUBJECT_EVERYONE);
			everyone.setFullPrivileges(true);
			channelAcl.add(everyone);

			nChannelAttributes channelAttributes = null;
			if (createJndiChannel) {
				channelAttributes = new nChannelAttributes(topicName, 0, 0, nChannelAttributes.PERSISTENT_TYPE);
			} else {
				channelAttributes = new nChannelAttributes();
				channelAttributes.setName(topicName);
				channelAttributes.setType(nChannelAttributes.MIXED_TYPE);
				channelAttributes.useJMSEngine(true);
				channelAttributes.setClusterWide(true);
			}

			try {
				node.createChannel(channelAttributes, 0, channelAcl);
			} catch (nSecurityException e) {
				if (logger.isDebugEnabled()) {
					logger.debug("Cannot create channel.", e);
				}
				// com.pcbsys.nirvana.client.nSecurityException: Clustering is
				// not configured on this realm and will not create cluster wide
				// channels.
				channelAttributes.setClusterWide(false);
				node.createChannel(channelAttributes, 0, channelAcl);
			}
			if (logger.isInfoEnabled()) {
				logger.info("Channel with name '" + topicName + "' successfully created on UM server at '"
						+ providerUrl + "'.");
			}
		} catch (nChannelAlreadyExistsException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Topic '" + topicName + "' already exists on UM server at '" + providerUrl + "'.", e);
			}
		} catch (nBaseAdminException e) {
			throw new NERVException("Unable to create topic " + topicName + " on UM server at '" + providerUrl + "'.",
					e);
		} catch (nBaseClientException e) {
			throw new NERVException("Unable to create topic " + topicName + " on UM server at '" + providerUrl + "'.",
					e);
		} finally {
			if (node != null) {
				node.close();
			}
		}
	}

	private String jndiEntryNameToTopicName(String jndiEntryName) {
		return jndiEntryName;
	}

	@Override
	protected ConnectionFactory createConnectionFactory() {
		ConnectionFactoryImpl connFactory = new ConnectionFactoryImpl(new DestinationImpl(providerUrl));
		connFactory.setEnableSharedDurable(true);
		return connFactory;
	}

	@Override
	protected void initializeProviderSpecifics() {
		createTopic(UM_JNDI_CONTEXT_CHANNEL_NAME, true);
	}

	@Override
	public void createTopic(final String jndiEntryName) {
		createTopic(jndiEntryName, false);
	}
}