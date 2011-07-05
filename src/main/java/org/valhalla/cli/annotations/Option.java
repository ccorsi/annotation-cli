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
package org.valhalla.cli.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to state which of the methods will be used in command
 * line processing. These annotated method have the option to define short and
 * long names. <p/>
 * 
 * The defined names can then expect a parameter depending if the annotated
 * method expects a parameter value or not.<p/>
 * 
 * There are three options that a parameter can have is regular, embedded or
 * property like.</p>
 * 
 * There are four types of options that can be defined using this annotation. <p/>
 * 
 * <ul>
 * 	<li>short name type</li>
 * 	<li>long name type</li>
 *  <li>property type</li>
 *  <li>embedded type</li>
 * </ul>
 * 
 * Short names are defined as -T. <p/>
 * 
 * Long names are defined as --trace. <p/>
 * 
 * Property type names are defined as name=value. <p/>
 * 
 * Embedded type names are defined as -O2, where the 2 is embedded within the command line parameter. <p/>
 * 
 * @author Claudio Corsi
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Option {

	/**
	 * This is the short name of the option. The option is prefixed with -
	 * 
	 * @return The short name, else ' '
	 */
	char shortName() default ' ';

	/**
	 * This is the long name of the option. The option is prefixed with --
	 * 
	 * @return The long name, else ""
	 */
	String longName() default "";

	/**
	 * The value is included as part of the option name. For instance, the
	 * option is O then an embedded option would given as -O2.
	 * 
	 * @return true, if this is an embedded option, default is false
	 */
	boolean embeddedValue() default false;

	/**
	 * This is set to true if the parameter is a property like setting passed on
	 * the command line. </p>
	 * 
	 * by default, this is set to false. </p>
	 * 
	 * Only one of the three options are allowed, expectedValue, embeddedValue.
	 * 
	 * @return true if property like, else false
	 */
	boolean propertyValue() default false;

	/**
	 * This is the default value that this option will use
	 * 
	 * @return The default value for this option, default is ""
	 */
	String defaultValue() default "";

	/**
	 * This is the description of this option for the given method associated to
	 * the class that the option will be set to.
	 * 
	 * @return The description of this command line option
	 */
	String description() default "";

}
