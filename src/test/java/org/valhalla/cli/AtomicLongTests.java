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

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Claudio Corsi
 *
 */
public class AtomicLongTests extends AbstractOptionsTestSupport {

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
	public void testAtomicLongShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-P", "1150" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The AtomicLong value was not properly",
				new AtomicLong(1150).intValue(), simple
						.getAtomicLongValue().intValue());
	}

	@Test
	public void testAtomicLongLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--pName", "3500" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The AtomicLong value was not properly",
				new AtomicLong(3500).intValue(), simple
						.getAtomicLongValue().intValue());
	}

	@Test
	public void testAtomicLongEmbeddedLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--pName=3500" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The AtomicLong value was not properly",
				new AtomicLong(3500).intValue(), simple
						.getAtomicLongValue().intValue());
	}

	@Test
	public void testAtomicLongDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple },
				new String[0]);
		Assert.assertEquals("The AtomicLong default value was not set",
				new AtomicLong(10100).intValue(), simple
						.getAtomicLongValue().intValue());
	}

}
