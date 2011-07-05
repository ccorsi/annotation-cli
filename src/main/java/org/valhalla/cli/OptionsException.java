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
 * This exception is thrown whenever there is an exception or an error while processing the
 * command line options.
 * 
 * @author Claudio Corsi
 *
 */
public class OptionsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4270307173617437780L;

	/**
	 * This constructor will be passed the error message stating what was wrong when
	 * processing the options.
	 * 
	 * @param message The error message
	 */
	public OptionsException(String message) {
		super(message);
	}

	/**
	 * This constructor will define the error message and the exception that was
	 * caught during the processing of the command line options.
	 * 
	 * @param message The error message
	 * @param e  The exception that was caught
	 */
	public OptionsException(String message, Exception e) {
		super(message, e);
	}

}
