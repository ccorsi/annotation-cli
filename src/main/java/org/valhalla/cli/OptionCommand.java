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


/**
 * This interface is used to generate a command used to apply a value to a given
 * object. </p>
 * 
 * @author Claudio Corsi
 * 
 */
public interface OptionCommand {

	/**
	 * This method will apply the passed value to the passed object.  The
	 * value is passed as a string and can be converted into the appropriate
	 * value by this method. 
	 * 
	 * @param object The object that is being applied the pass value to
	 * @param value The value that will be passed to the object
	 * @throws Exception If we are unable to apply the passed value to the
	 * 			passed object
	 */
	void execute(Object object, String value) throws Exception;

	/**
	 * This is used to determine if this instance Option expects a value
	 * 
	 * @return true if this instance Option expects a value, else false
	 */
	boolean hasValue();
	
	/**
	 * This method will return the class that this command will be able to apply
	 * the passed value to.
	 * 
	 * @return The class that this command works with
	 */
	Class<?> forClass();
	
}
