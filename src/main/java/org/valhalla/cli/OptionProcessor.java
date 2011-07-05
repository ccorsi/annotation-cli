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
 * This class is responsible in managing the applying of the value to a given instance
 * for a given option.
 * 
 * @author Claudio Corsi
 *
 */
public class OptionProcessor {

	private OptionCommand command;
	private Option option;

	/**
	 * This constructors expects the option command that is used to apply
	 * the value with the given option.  This option processor will then
	 * be passed an object and a value that will be passed to the appropriate
	 * method of the object.
	 * 
	 * @param command  The command used to apply the passed value to an instance
	 * @param option  The option associated to the particular command line option
	 *                being applied
	 */
	public OptionProcessor(OptionCommand command, Option option) {
		this.command = command;
		this.option  = option;
	}

	/**
	 * The option annotation associated with this given option processor instance
	 * 
	 * @return This processor associated option
	 * 
	 */
	public Option getOption() {
		return this.option;
	}
	
	/**
	 * @return
	 */
	public Class<?> forClass() {
		return this.command.forClass();
	}
	
	/**
	 * @return
	 */
	public boolean hasValue() {
		return this.command.hasValue();
	}
	
	/**
	 * This method will apply the passed value to the appropriate method of the 
	 * passed object.
	 * 
	 * @param object The object that this option value will be applied to
	 * @param value The value that will be passed to the object
	 * @throws Exception  Thrown if unable to pass the passed value to the passed object
	 */
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
