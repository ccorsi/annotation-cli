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
public class IntegerTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveIntegerShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-I", "23" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", 23, simple.getPrimitiveIntegerValue());
	}
	
	@Test
	public void testIntegerShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-E", "13" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", Integer.valueOf(13), simple.getIntegerValue());
	}
	
	@Test
	public void testPrimitiveIntegerLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--iName", "43" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", 43, simple.getPrimitiveIntegerValue());
	}
	
	@Test
	public void testIntegerLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--eName", "33" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", Integer.valueOf(33), simple.getIntegerValue());
	}
	
	@Test
	public void testPrimitiveIntegerDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", 3, simple.getPrimitiveIntegerValue());
	}
	
	@Test
	public void testIntegerDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The integer value was not properly", Integer.valueOf(2), simple.getIntegerValue());
	}
	
}
