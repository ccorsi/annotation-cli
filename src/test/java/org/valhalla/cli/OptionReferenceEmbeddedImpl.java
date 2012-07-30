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

import org.valhalla.cli.annotations.Option;
import org.valhalla.cli.annotations.OptionReference;

/**
 * @author Claudio Corsi
 *
 */
public class OptionReferenceEmbeddedImpl {
	
	@OptionReference
	public Embedded embedded = new Embedded();
	
	private int level;
	private int defIntValue;

	@Option(shortName = 'C', embeddedValue = true)
	public void optionReferenceIntValue(int level) {
		this.level = level;
	}

	@Option(shortName = 'D', embeddedValue = true, defaultValue = "20")
	public void optionReferenceDefaultIntValue(int defIntValue) {
		this.defIntValue = defIntValue;
	}
	
	public int getOptionReferenceIntValue() {
		return this.level;
	}
	
	public int getOptionReferenceDefaultIntValue() {
		return this.defIntValue;
	}
	
	public Embedded getEmbedded() {
		return this.embedded;
	}

}
