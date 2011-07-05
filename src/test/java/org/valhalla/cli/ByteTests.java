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
public class ByteTests extends AbstractOptionsTestSupport {

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
	public void testPrimitiveByteShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-A", "105" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte value was not properly", 105, simple.getPrimitiveByteValue());
	}
	
	@Test
	public void testByteShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-M", "115" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte value was not properly", Byte.valueOf((byte) 115), simple.getByteValue());
	}
	
	@Test
	public void testPrimitiveByteLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--aName", "125" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte value was not properly", 125, simple.getPrimitiveByteValue());
	}
	
	@Test
	public void testByteLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--mName", "35" }, new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte value was not properly", Byte.valueOf((byte) 35), simple.getByteValue());
	}
	
	@Test
	public void testPrimitiveByteDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte default value was not set", 75, simple.getPrimitiveByteValue());
	}
	
	@Test
	public void testByteDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The Byte default value was not set", Byte.valueOf((byte) 100), simple.getByteValue());
	}
	
}
