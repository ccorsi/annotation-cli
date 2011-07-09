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

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Claudio Corsi
 *
 */
public class ObjectTests extends AbstractOptionsTestSupport {

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
	public void testURIShortNameOption() throws OptionsException, URISyntaxException {
		executeOptions(new String[] { "-U", "http://www.apache.org" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The URI value was not properly",
				new URI("http://www.apache.org"), simple
						.getURIValue());
	}

	@Test
	public void testURILongNameOption() throws OptionsException, URISyntaxException {
		executeOptions(new String[] { "--uName", "http://www.w3c.org" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The URI value was not properly",
				new URI("http://www.w3c.org"), simple
						.getURIValue());
	}

	@Test
	public void testURIEmbeddedLongNameOption() throws OptionsException, URISyntaxException {
		executeOptions(new String[] { "--uName=http://www.w3c.org" },
				new Object[] { this.simple }, new String[0]);
		Assert.assertEquals("The URI value was not properly",
				new URI("http://www.w3c.org"), simple
						.getURIValue());
	}

	@Test
	public void testURIDefaultOption() throws OptionsException, URISyntaxException {
		executeOptions(new String[0], new Object[] { this.simple },
				new String[0]);
		Assert.assertEquals("The URI default value was not set",
				new URI("http://localhost:8080/default"), simple
						.getURIValue());
	}

}
