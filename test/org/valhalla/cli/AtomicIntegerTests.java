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

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Claudio Corsi
 * 
 */
public class AtomicIntegerTests extends AbstractOptionsTestSupport {

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
	public void testAtomicIntegerShortNameOption() throws OptionsException {
		executeOptions(new String[] { "-O", "115" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The AtomicInteger value was not properly",
				new AtomicInteger(115).intValue(), simple
						.getAtomicIntegerValue().intValue());
	}

	@Test
	public void testAtomicIntegerLongNameOption() throws OptionsException {
		executeOptions(new String[] { "--oName", "35" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The AtomicInteger value was not properly",
				new AtomicInteger(35).intValue(), simple
						.getAtomicIntegerValue().intValue());
	}

	@Test
	public void testAtomicIntegerDefaultOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { this.simple },
				new String[0]);
		Assert.assertEquals("The AtomicInteger default value was not set",
				new AtomicInteger(101).intValue(), simple
						.getAtomicIntegerValue().intValue());
	}

}
