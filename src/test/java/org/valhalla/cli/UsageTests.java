/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.valhalla.cli;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Claudio Corsi
 * 
 */
public class UsageTests extends AbstractOptionsTestSupport {

	private static String lineSep = System.getProperty("line.separator");

	// Set this to an empty class list to avoid a NPE during the start of the tests.
	private Class<?>[] optionClasses = new Class<?>[0];

	@Override
	Class<?>[] getClasses() {
		return optionClasses;
	}

	@Test
	public void testUsageInfo() throws OptionsException {
		setUpAndCreateOptions(new Class<?>[] { OptionImplOne.class, OptionImplTwo.class });
		String expectedUsageMessage = "usage: java org.valhalla.cli.MainClass simple command line string"
				+ lineSep
				+ "  -A    This is an A option"
				+ lineSep
				+ "  --bOption[=value| value]    This is the bOption option"
				+ lineSep;
		String usageMessage = this.options.usage(MainClass.class,
				"simple command line string");
//		System.out.println(usageMessage);
		Assert.assertEquals("Usage string is incorrect", expectedUsageMessage,
				usageMessage);
	}

	@Test
	public void testMultipleLineUsage() throws OptionsException {
		setUpAndCreateOptions(new Class<?>[] { OptionImplOne.class,
				OptionImplTwo.class, OptionImplThree.class });
		String expectedUsageMessage = "usage: java org.valhalla.cli.MainClass simple command line string"
				+ lineSep
				+ "  -A    This is an A option"
				+ lineSep
				+ "  --bOption[=value| value]    This is the bOption option"
				+ lineSep
				+ "  -B    This is option B that will contain a description that will span multiple lines to test that the usage mechanism"
				+ lineSep 
				+ "    will properly display this information on the screen"
				+ lineSep;
		String usageMessage = this.options.usage(MainClass.class,
				"simple command line string");
//		System.out.println("########################");
//		System.out.println(usageMessage);
//		System.out.println("########################");
//		System.out.println(expectedUsageMessage);
//		System.out.println("########################");
//		System.out.println("lengths [" + usageMessage.length() + " , " + expectedUsageMessage.length() + "]");
		Assert.assertEquals("Usage string is incorrect", expectedUsageMessage,
				usageMessage);
	}
	
	private void setUpAndCreateOptions(Class<?>[] classes) throws OptionsException {
		optionClasses = classes;
		createOptions();
	}
}
