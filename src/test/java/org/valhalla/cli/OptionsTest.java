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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.valhalla.cli.Options;
import org.valhalla.cli.OptionsException;
import org.valhalla.cli.annotations.Option;

/**
 * @author Claudio Corsi
 * 
 */
public class OptionsTest {

	/**
	 * Test method for {@link
	 * org.apache.commons.cli.Options#Options(java.lang.Class<?>[])}.
	 * 
	 * @throws OptionsException
	 */
	@Test
	public void testOptionsClassReturnedArguments() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		String[] expected = new String[] { "foo", "bar" };
		String args[] = options.processArguements(expected,
				new Object[] { new Simple() });
		assertArrayEquals("Return remaining arguments are not the same",
				expected, args);
	}

	/**
	 * Test method for
	 * {@link org.valhalla.cli.Options#processArguements(java.lang.String[], java.lang.Object[])}
	 * .
	 * 
	 * @throws OptionsException
	 */
	@Test
	public void testShortNameOptions() throws OptionsException {
		String[] args = new String[] { "-T", "-B", "true" };
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String remaining[] = options.processArguements(args,
				new Object[] { simple });
		for (String value : remaining) {
			System.out.println(value);
		}
		assertTrue("Returned arguments is not empty", remaining.length == 0);
		assertTrue("The T parameter was not set", simple.isNoValueOption());
		assertTrue("The B option was not set", simple.getBooleanValue()
				.booleanValue());
	}

	/**
	 * Test method for
	 * {@link org.valhalla.cli.Options#Options(java.util.Collection)}.
	 * 
	 * @throws OptionsException
	 */
	@Test(expected = OptionsException.class)
	public void testDuplicateOption() throws OptionsException {
		new Options(new Class<?>[] { DuplicateOption.class });
	}

	@Test(expected = OptionsException.class)
	public void testDuplicateOptionForTwoClasses() throws OptionsException {
		new Options(new Class<?>[] { Simple.class, Simple.class });
	}

	/**
	 * @throws OptionsException
	 */
	@Test
	public void testLongNamesOptions() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String args[] = options.processArguements(new String[] { "--trace",
				"--zillow", "true" }, new Object[] { simple });
		assertTrue("Returned arguments is not empty", args.length == 0);
		assertTrue("The trace option was not set", simple.isNoValueOption());
		assertTrue("The zillion option was not set",
				simple.getPrimitiveBooleanValue());
	}

	@Test(expected=OptionsException.class)
	public void testLongNamesOptionsWithMissingEmbeddedValue()
			throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		options.processArguements(new String[] { "--trace", "--zillow=" },
				new Object[] { simple });
	}

	@Test
	public void testDefaultValues() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String args[] = options.processArguements(new String[0],
				new Object[] { simple });
		assertEquals("Default value was not set", 100, simple.getDefautlValue());
		assertTrue("Returned arguments is not empty", args.length == 0);
	}

	@Test
	public void testPropertyOptions() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String args[] = options.processArguements(new String[] { "foo=bar" },
				new Object[] { simple });
		assertTrue("Returned arguments is not empty", args.length == 0);
		assertEquals("The property option was not correctly", "bar",
				simple.getPropertyOption());
	}

	@Test
	public void testPropertyOptionsWithDefaultValue() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String args[] = options.processArguements(new String[0],
				new Object[] { simple });
		assertTrue("Returned arguments is not empty", args.length == 0);
		assertEquals("The property option was not correctly", "default",
				simple.getPropertyOptionWithDefault());
	}

	@Test
	public void testParameterValue() throws OptionsException {
		Options options = new Options(new Class<?>[] { Simple.class });
		Simple simple = new Simple();
		String args[] = options.processArguements(new String[] { "-C",
				"ParameterValue" }, new Object[] { simple });
		assertTrue("Returned arguments is not empty", args.length == 0);
		ParameterValue value = simple.getParameterValue();
		assertNotNull("The parameter value was not set", value);
		assertEquals("Patameter value value was not set to ParameterValue",
				"ParameterValue", value.getValue());
	}

	class DuplicateOption {

		@Option(shortName = 'a')
		public void oneA() {

		}

		@Option(shortName = 'a')
		public void duplicateA() {

		}
	}

}
