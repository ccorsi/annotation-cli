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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.valhalla.cli.annotations.Option;

/**
 * @author Claudio Corsi
 *
 */
public 	class Simple {
	private boolean trace = false;
	private Boolean Bool;
	private boolean bool;
	private String propertyOption;
	private int defaultValue = -1;
	private ParameterValue value;
	private byte pByte;
	private Byte wByte;
	private int pInt;
	private Integer wInt;
	private long pLong;
	private Long wLong;
	private short pShort;
	private Short wShort;
	private float pFloat;
	private Float wFloat;
	private double pDouble;
	private Double wDouble;
	private AtomicInteger atomicInteger;
	private AtomicLong atomicLong;
	private BigInteger bigInteger;
	private BigDecimal bigDecimal;
	private URI uri;

	@Option(shortName = 'C', longName = "cName")
	public void setParameterValue(ParameterValue value) {
		this.value = value;
	}
	
	public ParameterValue getParameterValue() {
		return this.value;
	}

	@Option(shortName = 'A', longName = "aName", defaultValue = "75")
	public void setPrimitiveByteValue(byte value) {
		this.pByte = value;
	}
	
	public byte getPrimitiveByteValue() {
		return this.pByte;
	}

	@Option(shortName = 'M', longName = "mName", defaultValue = "100")
	public void setByteValue(Byte value) {
		this.wByte = value;
	}
	
	public Byte getByteValue() {
		return this.wByte;
	}
	
	@Option(shortName = 'I', longName = "iName", defaultValue = "3") 
	public void setPrimitiveIntegerValue(int value){
		this.pInt = value;
	}
	
	public int getPrimitiveIntegerValue() {
		return this.pInt;
	}
	
	@Option(shortName = 'E', longName = "eName", defaultValue = "2") 
	public void setIntegerValue(Integer value){
		this.wInt = value;
	}
	
	public Integer getIntegerValue() {
		return this.wInt;
	}
	
	@Option(shortName = 'L', longName = "lName", defaultValue = "3") 
	public void setPrimitiveLongValue(long value){
		this.pLong = value;
	}
	
	public long getPrimitiveLongValue() {
		return this.pLong;
	}
	
	@Option(shortName = 'F', longName = "fName", defaultValue = "2") 
	public void setLongValue(Long value){
		this.wLong = value;
	}
	
	public Long getLongValue() {
		return this.wLong;
	}
	
	@Option(shortName = 'S', longName = "sName", defaultValue = "3") 
	public void setPrimitiveShortValue(short value){
		this.pShort = value;
	}
	
	public short getPrimitiveShortValue() {
		return this.pShort;
	}
	
	@Option(shortName = 'G', longName = "gName", defaultValue = "2") 
	public void setShortValue(Short value){
		this.wShort = value;
	}
	
	public Short getShortValue() {
		return this.wShort;
	}
	
	@Option(shortName = 'H', longName = "hName", defaultValue = "1.5") 
	public void setPrimitiveFloatValue(float value){
		this.pFloat = value;
	}
	
	public float getPrimitiveFloatValue() {
		return this.pFloat;
	}
	
	@Option(shortName = 'J', longName = "jName", defaultValue = "2.5") 
	public void setFloatValue(Float value){
		this.wFloat = value;
	}
	
	public Float getFloatValue() {
		return this.wFloat;
	}
	
	@Option(shortName = 'K', longName = "kName", defaultValue = "4.5") 
	public void setPrimitiveDoubleValue(double value){
		this.pDouble = value;
	}
	
	public double getPrimitiveDoubleValue() {
		return this.pDouble;
	}
	
	@Option(shortName = 'N', longName = "nName", defaultValue = "6.5") 
	public void setDoubleValue(Double value){
		this.wDouble = value;
	}
	
	public Double getDoubleValue() {
		return this.wDouble;
	}
	
	@Option(shortName = 'D', defaultValue = "100")
	public void useDefaultValue(int value) {
		this.defaultValue = value;
	}

	public int getDefautlValue() {
		return this.defaultValue;
	}

	@Option(shortName = 'T', longName = "trace",
			description="This option will enable the tracing option")
	public void noValueOption() {
		this.trace = true;
	}

	public boolean isNoValueOption() {
		return this.trace;
	}

	@Option(shortName = 'B', longName = "boolean", defaultValue = "true",
			description="This option expected a boolean and contain a default value of true")
	public void setBooleanValue(Boolean value) {
		this.Bool = value;
	}

	public Boolean getBooleanValue() {
		return this.Bool;
	}

	@Option(shortName = 'Z', longName = "zillow", defaultValue = "true",
			description="This option expects a boolean with a default value of true")
	public void setPrimitiveBooleanValue(boolean value) {
		this.bool = value;
	}

	public boolean getPrimitiveBooleanValue() {
		return this.bool;
	}

	@Option(longName = "foo", propertyValue = true,
			description="This is an example of a property value")
	public void properyOption(String value) {
		this.propertyOption = value;
	}

	public String getPropertyOption() {
		return this.propertyOption;
	}

	@Option(longName = "default", propertyValue = true, defaultValue = "default",
			description="This is a property option with a default of default")
	public void properyOptionWithDefault(String value) {
		this.propertyOption = value;
	}

	public String getPropertyOptionWithDefault() {
		return this.propertyOption;
	}

	@Option(shortName = 'O', longName = "oName", defaultValue = "101")
	public void setAtomicIntegerValue(AtomicInteger value) {
		this.atomicInteger = value;
	}
	
	public AtomicInteger getAtomicIntegerValue() {
		return this.atomicInteger;
	}

	@Option(shortName = 'P', longName = "pName", defaultValue = "10100",
			description="This is a long option with a default value of 10100")
	public void setAtomicLongValue(AtomicLong value) {
		this.atomicLong = value;
	}
	
	public AtomicLong getAtomicLongValue() {
		return this.atomicLong;
	}

	@Option(shortName = 'Q', longName = "qName", defaultValue = "12345678901234567890")
	public void setBigIntegerValue(BigInteger value) {
		this.bigInteger = value;
	}
	
	public BigInteger getBigIntegerValue() {
		return this.bigInteger;
	}

	@Option(shortName = 'R', longName = "rName", defaultValue = "12345678901234567890.1234567890")
	public void setBigDecimalValue(BigDecimal value) {
		this.bigDecimal = value;
	}
	
	public BigDecimal getBigDecimalValue() {
		return this.bigDecimal;
	}

	@Option(shortName = 'U', longName = "uName", defaultValue = "http://localhost:8080/default")
	public void setURIValue(URI value) {
		this.uri = value;
	}
	
	public URI getURIValue() {
		return this.uri;
	}
}
