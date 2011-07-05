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
public class LongTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveLongShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-L", "23" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Long value was not properly", 23, simple.getPrimitiveLongValue());
	}
	
	@Test
	public void testLongShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-F", "13" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Long value was not properly", Long.valueOf(13), simple.getLongValue());
	}
	
	@Test
	public void testPrimitiveLongLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--lName", "43" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Long value was not properly", 43, simple.getPrimitiveLongValue());
	}
	
	@Test
	public void testLongLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--fName", "33" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Long value was not properly", Long.valueOf(33), simple.getLongValue());
	}
	
	@Test
	public void testPrimitiveLongDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The long default value was not set", 3, simple.getPrimitiveLongValue());
	}
	
	@Test
	public void testLongDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The long default value was not set", Long.valueOf(2), simple.getLongValue());
	}
	
}
