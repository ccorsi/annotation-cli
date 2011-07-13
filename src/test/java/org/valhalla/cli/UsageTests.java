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

	@Override
	Class<?>[] getClasses() {
		return new Class<?>[] { OptionImplOne.class, OptionImplTwo.class };
	}

	@Test
	public void testUsageInfo() throws OptionsException {
		String expectedUsageMessage = "usage: java org.valhalla.cli.MainClass simple command line string"
				+ lineSep
				+ "  -A	This is an A option"
				+ lineSep
				+ "  --bOption[=value| value]	This is the bOption option"
				+ lineSep;
		String usageMessage = this.options.usage(MainClass.class,
				"simple command line string");
		Assert.assertEquals("Usage string is incorrect", expectedUsageMessage,
				usageMessage);
	}
}
