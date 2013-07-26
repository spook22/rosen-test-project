package com.softwareag.eda.nerv.jms;

import javax.jms.ConnectionFactory;

public interface ConnectionFactoryProvider {

	ConnectionFactory connectionFactory();

}
