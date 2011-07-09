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
public class ShortTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveShortShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-S", "23" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The short value was not properly", 23, simple.getPrimitiveShortValue());
	}
	
	@Test
	public void testShortShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-G", "13" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The short value was not properly", Short.valueOf((short) 13), simple.getShortValue());
	}
	
	@Test
	public void testPrimitiveShortLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--sName", "43" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The short value was not properly", 43, simple.getPrimitiveShortValue());
	}
	
	@Test
	public void testShortLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--gName", "33" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Short value was not properly", Short.valueOf((short) 33), simple.getShortValue());
	}
	
	@Test
	public void testPrimitiveShortEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--sName=43" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The short value was not properly", 43, simple.getPrimitiveShortValue());
	}
	
	@Test
	public void testShortEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--gName=33" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Short value was not properly", Short.valueOf((short) 33), simple.getShortValue());
	}
	
	@Test
	public void testPrimitiveShortDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Short default value was not set", 3, simple.getPrimitiveShortValue());
	}
	
	@Test
	public void testShortDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Short default value was not set", Short.valueOf((short) 2), simple.getShortValue());
	}
	
}
