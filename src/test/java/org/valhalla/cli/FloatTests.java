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
public class FloatTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveFloatShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-H", "3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", 3.5, simple.getPrimitiveFloatValue(), 0.5);
	}
	
	@Test
	public void testFloatShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-J", "1.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", Float.valueOf((float) 1.5), simple.getFloatValue());
	}
	
	@Test
	public void testPrimitiveFloatLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--hName", "4.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", 4.5, simple.getPrimitiveFloatValue(), 0.5);
	}
	
	@Test
	public void testFloatLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--jName", "3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", Float.valueOf((float) 3.5), simple.getFloatValue());
	}
	
	@Test
	public void testPrimitiveFloatEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--hName=4.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", 4.5, simple.getPrimitiveFloatValue(), 0.5);
	}
	
	@Test
	public void testFloatEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--jName=3.5" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float value was not properly", Float.valueOf((float) 3.5), simple.getFloatValue());
	}
	
	@Test
	public void testPrimitiveFloatDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float default value was not set", 1.5, simple.getPrimitiveFloatValue(), .5);
	}
	
	@Test
	public void testFloatDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Float default value was not set", Float.valueOf((float) 2.5), simple.getFloatValue());
	}
	
}
