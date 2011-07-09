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

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Claudio Corsi
 * 
 */
public class MultipleOptionsTests extends AbstractOptionsTestSupport {

	private Simple simple;

	@Before
	public void createSimple() {
		this.simple = new Simple();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.valhalla.cli.AbstractOptionsTestSupport#getClasses()
	 */
	@Override
	Class<?>[] getClasses() {
		return new Class<?>[] { Simple.class };
	}

	@Test
	public void testMultipleOptions() throws OptionsException {
		executeOptions(new String[] { "-QTZ", "11501234", "true" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The BigInteger value was not properly",
				new BigInteger("11501234"), simple.getBigIntegerValue());
		Assert.assertTrue("The boolean value was not set to true",
				simple.getPrimitiveBooleanValue());
		Assert.assertTrue("The trace option was not set",
				simple.isNoValueOption());
	}
	
	@Test(expected=OptionsException.class)
	public void testMultipleOptionsWithEmbeddedOption() throws OptionsException {
		executeOptions(new String[] { "-QA5TZ", "11501234", "true" },
				new Object[] { this.simple }, new String[0]);
	}
	
	@Test(expected = OptionsException.class)
	public void testMultipleOptionsInvalid() throws OptionsException {
		executeOptions(new String[] { "-QTZ", "11501234" },
				new Object[] { this.simple }, new String[0]);		
	}

	@Test
	public void testMultipleOptionsAndNonOptionValues() throws OptionsException {
		executeOptions(new String[] { "-QT", "11501234", "value", "-Z", "true" },
				new Object[] { this.simple }, new String[] { "value" });
		Assert.assertEquals("The BigInteger value was not properly",
				new BigInteger("11501234"), simple.getBigIntegerValue());
		Assert.assertTrue("The boolean value was not set to true",
				simple.getPrimitiveBooleanValue());
		Assert.assertTrue("The trace option was not set",
				simple.isNoValueOption());
	}
	
}
