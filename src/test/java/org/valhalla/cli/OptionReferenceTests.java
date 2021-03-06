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
public class OptionReferenceTests extends AbstractOptionsTestSupport {

	private OptionReferenceEmbeddedMethodImpl optionReference;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		optionReference = new OptionReferenceEmbeddedMethodImpl();
	}
	
	@Test
	public void testOptionReferenceOption() throws OptionsException {
		executeOptions(new String[] { "-C5" }, new Object[] { optionReference }, new String[0]);
		Assert.assertEquals("optionReference value was incorrect", 5, optionReference.getOptionReferenceIntValue());
	}
	
	@Test
	public void testOptionReferenceDefaultValueOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { optionReference }, new String[0]);
		Assert.assertEquals("optionReference value was incorrect", 20, optionReference.getOptionReferenceDefaultIntValue());
	}

	@Test(expected = OptionsException.class)
	public void testInvalidOptionReferenceOption() throws OptionsException {
		executeOptions(new String[] { "-C10.5" }, new Object[] { optionReference }, new String[0]);
	}
	
	@Test
	public void testOptionReferenceEmbeddedOption() throws OptionsException {
		executeOptions(new String[] { "-A5" }, new Object[] { optionReference }, new String[0]);
		Assert.assertEquals("optionReference value was incorrect", 5, optionReference.getEmbedded().getEmbeddedIntValue());
	}
	
	@Test
	public void testOptionReferenceEmbeddedDefaultValueOption() throws OptionsException {
		executeOptions(new String[0], new Object[] { optionReference }, new String[0]);
		Assert.assertEquals("optionReference value was incorrect", 10, optionReference.getEmbedded().getEmbeddedDefaultIntValue());
	}

	@Test(expected = OptionsException.class)
	public void testInvalidOptionReferenceEmbeddedOption() throws OptionsException {
		executeOptions(new String[] { "-A10.5" }, new Object[] { optionReference }, new String[0]);
	}

	/* (non-Javadoc)
	 * @see org.valhalla.cli.AbstractOptionsExTestSupport#getClasses()
	 */
	@Override
	Class<?>[] getClasses() {
		return new Class<?>[] { OptionReferenceEmbeddedMethodImpl.class };
	}

}
