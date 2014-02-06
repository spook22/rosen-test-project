package com.softwareag.um;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.softwareag.um.jms.UmJmsPerfTest;
import com.softwareag.um.nativeapi.UmNativeApiPerfTest;

@RunWith(Suite.class)
@SuiteClasses({ UmJmsPerfTest.class, UmNativeApiPerfTest.class })
public class UmTestSuite {

}
