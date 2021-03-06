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
public class OptionImplThree {

	private boolean bOption;

	@Option(shortName='B', description="This is option B that will contain a description that will span multiple lines to test that the usage mechanism will properly display this information on the screen", required=true)
	public void setBOption() {
		this.bOption = true;
	}
	
	public boolean getBOption() {
		return this.bOption;
	}
}
