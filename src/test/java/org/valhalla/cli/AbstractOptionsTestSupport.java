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
import org.junit.Before;

/**
 * @author Claudio Corsi
 *
 */
public abstract class AbstractOptionsTestSupport {
	
	protected Options options;

	abstract Class<?>[] getClasses();
	
	@Before
	public void createOptions() throws OptionsException {
		options = new Options(getClasses());
	}

	protected String[] executeOptions(String args[], Object objects[], String expectedResults[]) throws OptionsException {
		String[] results = options.processArguements(args, objects);
		Assert.assertEquals("Number of returned arguments are not equal", expectedResults.length, results.length);
		Assert.assertArrayEquals("Return result values are not as expected", expectedResults, results);
		return results;
	}
}
