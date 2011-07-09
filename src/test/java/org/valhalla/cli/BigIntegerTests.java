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
public class BigIntegerTests extends AbstractOptionsTestSupport {

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
	public void testBigIntegerShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-Q", "11501234" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The BigInteger value was not properly",
				new BigInteger("11501234"), simple
						.getBigIntegerValue());
	}

	@Test
	public void testBigIntegerLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--qName", "35001234567890" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The BigInteger value was not properly",
				new BigInteger("35001234567890"), simple
						.getBigIntegerValue());
	}

	@Test
	public void testBigIntegerEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--qName=35001234567890" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The BigInteger value was not properly",
				new BigInteger("35001234567890"), simple
						.getBigIntegerValue());
	}

	@Test
	public void testBigIntegerDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple },
				new String[0]);
		Assert.assertEquals("The BigInteger default value was not set",
				new BigInteger("12345678901234567890"), simple
						.getBigIntegerValue());
	}

}
