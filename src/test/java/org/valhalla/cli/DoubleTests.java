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
public class DoubleTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveDoubleShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-K", "3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", 3.5, simple.getPrimitiveDoubleValue(), 0.5);
	}
	
	@Test
	public void testDoubleShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-N", "2.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", Double.valueOf(2.5), simple.getDoubleValue());
	}
	
	@Test
	public void testPrimitiveDoubleLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--kName", "3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", 3.5, simple.getPrimitiveDoubleValue(), 0.5);
	}
	
	@Test
	public void testDoubleLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--nName", "4.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", Double.valueOf(4.5), simple.getDoubleValue());
	}
	
	@Test
	public void testPrimitiveDoubleEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--kName=3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", 3.5, simple.getPrimitiveDoubleValue(), 0.5);
	}
	
	@Test
	public void testDoubleEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--nName=4.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double value was not properly", Double.valueOf(4.5), simple.getDoubleValue());
	}
	
	@Test
	public void testPrimitiveDoubleDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double default value was not set", 4.5, simple.getPrimitiveDoubleValue(), .5);
	}
	
	@Test
	public void testDoubleDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Double default value was not set", Double.valueOf(6.5), simple.getDoubleValue());
	}
	
}
