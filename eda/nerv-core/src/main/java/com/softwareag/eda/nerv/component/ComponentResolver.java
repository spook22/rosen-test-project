package com.softwareag.eda.nerv.component;

import org.apache.camel.Component;

public interface ComponentResolver {

	Component resolve(String name);

}
