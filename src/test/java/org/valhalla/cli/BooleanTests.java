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
import org.junit.Test;

/**
 * @author Claudio Corsi
 *
 */
public class BooleanTests extends AbstractOptionsTestSupport {

	private Simple simple;
	
	@Before
	public void createSimple() {
		this.simple = new Simple();
	}
	
	/* (non-Javadoc)
	 * @see org.valhalla.cli.AbstractOptionsTestSupport#getClasses()
	 */
	@Override
	Class<?>[] getClasses() {
		return new Class<?>[] { Simple.class };
	}

	@Test
	public void testPrimitiveBooleanShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-Z", "false" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean value was not properly", false, simple.getPrimitiveBooleanValue());
	}
	
	@Test
	public void testBooleanShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-B", "false" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean value was not properly", Boolean.FALSE, simple.getBooleanValue());
	}
	
	@Test
	public void testPrimitiveBooleanLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--zillow", "false" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean value was not properly", false, simple.getPrimitiveBooleanValue());
	}
	
	@Test
	public void testBooleanLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--boolean", "false" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean value was not properly", Boolean.FALSE, simple.getBooleanValue());
	}
	
	@Test
	public void testPrimitiveBooleanDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean default value was not set", true, simple.getPrimitiveBooleanValue());
	}
	
	@Test
	public void testBooleanDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Boolean default value was not set", Boolean.TRUE, simple.getBooleanValue());
	}
	
}
