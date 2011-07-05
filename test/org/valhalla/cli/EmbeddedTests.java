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
import org.junit.Test;


/**
 * @author Claudio Corsi
 *
 */
public class EmbeddedTests {
	
	@Test
	public void testEmbeddedOption() throws OptionsException {
		Options options = new Options(new Class<?>[] { Embedded.class });
		Embedded embedded = new Embedded();
		String args[] = options.processArguements(new String[] { "-A5" }, new Object[] { embedded });
		Assert.assertArrayEquals("Command line arguements where returned", new String[0], args);
		Assert.assertEquals("Embedded value was incorrect", 5, embedded.getEmbeddedIntValue());
	}
	
	@Test
	public void testEmbeddedDefaultValueOption() throws OptionsException {
		Options options = new Options(new Class<?>[] { Embedded.class });
		Embedded embedded = new Embedded();
		String args[] = options.processArguements(new String[0], new Object[] { embedded });
		Assert.assertArrayEquals("Command line arguements where returned", new String[0], args);
		Assert.assertEquals("Embedded value was incorrect", 10, embedded.getEmbeddedDefaultIntValue());
	}

}
