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

/**
 * @author Claudio Corsi
 *
 */
public class OptionProcessor {

	private OptionCommand command;
	private Option option;

	/**
	 * @param command
	 * @param option
	 */
	public OptionProcessor(OptionCommand command, Option option) {
		this.command = command;
		this.option  = option;
	}

	public Option getOption() {
		return this.option;
	}
	
	public Class<?> forClass() {
		return this.command.forClass();
	}
	
	public boolean hasValue() {
		return this.command.hasValue();
	}
	
	public void process(Object object, String value) throws Exception {
		this.command.execute(object, value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OptionProcessor [command=" + command + ", option=" + option
				+ "]";
	}
}
