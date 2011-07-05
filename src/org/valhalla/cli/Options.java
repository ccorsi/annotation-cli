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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.valhalla.cli.annotations.Option;

/**
 * This is the main class used to process options that are available for a user
 * to pass on the command line for a given application. This allows the user to
 * provide one or more classes that define the command line options that a given
 * application can be passed on the command line. </p>
 * 
 * The options are defined using the Option annotation. The developer defines
 * one or more classes that will be passed options on the command line. Usually
 * a developer would create the command line options and then process these
 * options about call the appropriate method to set the option and/or set the
 * appropriate field. </p>
 * 
 * @author Claudio Corsi
 * 
 */
public class Options {

	/**
	 * @author Claudio Corsi
	 * 
	 */
	public interface ConvertCommand {
		Object execute(String value) throws Exception;
	}

	private Map<String, OptionProcessor> shortNames = new HashMap<String, OptionProcessor>();
	private Map<String, OptionProcessor> longNames = new HashMap<String, OptionProcessor>();
	private Map<Class<?>, Collection<OptionProcessor>> defaultValues = new HashMap<Class<?>, Collection<OptionProcessor>>();
	private Map<String, OptionProcessor> propsNames = new HashMap<String, OptionProcessor>();
	private Collection<Option> options = new LinkedList<Option>();

	/**
	 * @param cliOptions
	 * @throws OptionsException
	 */
	public Options(Class<?> cliOptions[]) throws OptionsException {
		// Process all of the classes.
		for (Class<?> clz : cliOptions) {
			processCLIOptions(clz);
		}
	}

	/**
	 * @param args
	 * @param objects
	 * @return
	 * @throws OptionsException
	 */
	public String[] processArguements(String[] args, Object[] objects)
			throws OptionsException {
		// Process all default values
		for (Object object : objects) {
			Collection<OptionProcessor> defaultOptions = defaultValues
					.get(object.getClass());
			for (OptionProcessor option : defaultOptions) {
				try {
					option.process(object, option.getOption().defaultValue());
				} catch (Exception e) {
					throw new OptionsException(
							"An exception was raised while trying to set the default value for option "
									+ option.getOption(), e);
				}
			}
		}
		List<String> argsList = new LinkedList<String>();
		// Process each argument on the command line
		for (int idx = 0; idx < args.length; idx++) {
			String arg = args[idx];
			if (arg.charAt(0) == '-') {
				if (arg.charAt(1) == '-') {
					String name = arg.substring(2);
					OptionProcessor processor = this.longNames.get(name);
					idx = processOption(args, objects, idx, name, processor);
				} else {
					String name = arg.substring(1); // This can be multiple
													// short names or an
													// embedded name.
					OptionProcessor processor = this.shortNames.get(name);
					if (processor != null) {
						idx = processOption(args, objects, idx, name, processor);
					} else {
						processor = this.shortNames.get(name.substring(0, 1));
						if (processor != null
								&& processor.getOption().embeddedValue()) {
							idx = processOption(args, objects, idx, name,
									processor);
						} else {
							// Process all short names
							for (int innerIdx = 0; innerIdx < name.length(); innerIdx++) {
								processor = this.shortNames.get(name.substring(
										innerIdx, innerIdx + 1));
								idx = processOption(args, objects, idx,
										name.substring(0, 1), processor);
							}
						}
					}
				}
			} else if (arg.indexOf('=') > -1) {
				String name = arg.substring(0, arg.indexOf('='));
				OptionProcessor processor = this.propsNames.get(name);
				if (processor == null) {
					throw new OptionsException("No available option for "
							+ name);
				}
				Class<?> type = processor.forClass();
				Object object = null;
				for (Object o : objects) {
					if (o.getClass() == type) {
						object = o;
						break;
					}
				}
				if (object == null) {
					throw new OptionsException(
							"No object available to set option " + name);
				}
				String value = arg.substring(arg.indexOf('=') + 1);
				try {
					processor.process(object, value);
				} catch (Exception e) {
					throw new OptionsException(
							"An exception was thrown when processing option "
									+ name, e);
				}
			} else {
				argsList.add(arg);
			}
		}
		return argsList.toArray(new String[0]);
	}

	/**
	 * @param args
	 * @param objects
	 * @param idx
	 * @param name
	 * @param processor
	 * @return
	 * @throws OptionsException
	 */
	private int processOption(String[] args, Object[] objects, int idx,
			String name, OptionProcessor processor) throws OptionsException {
		if (processor == null) {
			throw new OptionsException("No available option for " + name);
		}
		Class<?> type = processor.forClass();
		// System.out.println("type: " + type);
		Object object = null;
		for (Object o : objects) {
			// System.out.println("Comparing with type " + o.getClass());
			if (o.getClass() == type) {
				object = o;
				break;
			}
		}
		if (object == null) {
			throw new OptionsException("No object available to set option "
					+ name);
		}
		try {
			if (processor.hasValue()) {
				String value = null;
				if (!processor.getOption().embeddedValue()) {
					value = args[++idx];
				} else {
					value = name.substring(1);
				}
				processor.process(object, value);
			} else {
				processor.process(object, null);
			}
		} catch (Exception e) {
			throw new OptionsException(
					"An exception was thrown when processing option " + name, e);
		}
		return idx;
	}

	/**
	 * @param clz
	 * @throws OptionsException
	 */
	private void processCLIOptions(Class<?> clz) throws OptionsException {
		for (Method method : clz.getMethods()) {
			Option option = method.getAnnotation(Option.class);
			if (option != null) {
				OptionCommand command;
				Class<?> parameterTypes[] = method.getParameterTypes();
				if (parameterTypes.length > 1) {
					throw new OptionsException(
							"Invalid parameter length, allowed only none or one parameter for option class: "
									+ clz.getName());
				}
				boolean isEmbedded = option.embeddedValue();
				boolean isProperty = option.propertyValue();
				if (parameterTypes.length == 0) {
					if (isEmbedded || isProperty) {
						throw new OptionsException(
								"You can not set the embedded or property type option with no-parameter method "
										+ method.getName()
										+ " for class "
										+ clz.getName());
					}
					// Create an option command that will be used to process the
					// current option
					command = new OptionCommand() {
						private Method method;
						private Class<?> clz;

						public void execute(Object object, String value)
								throws Exception {
							method.invoke(object);
						}

						OptionCommand setFields(Method method, Class<?> clz) {
							this.method = method;
							this.clz = clz;
							return this;
						}

						@Override
						public boolean hasValue() {
							return false;
						}

						@Override
						public Class<?> forClass() {
							return this.clz;
						}

					}.setFields(method, clz);
				} else {
					Class<?> type = parameterTypes[0];
					// Create an option command that will be used to process the
					// current option
					try {
						command = new OptionCommand() {
							private Method method;
							private ConvertCommand convert;
							private Class<?> clz;

							OptionCommand setFields(Method method,
									Class<?> type, Class<?> clz)
									throws Exception {
								this.method = method;
								this.convert = convert(type);
								this.clz = clz;
								return this;
							}

							@Override
							public void execute(Object object, String value)
									throws Exception {
								method.invoke(object, convert.execute(value));

							}

							@Override
							public boolean hasValue() {
								return true;
							}

							@Override
							public Class<?> forClass() {
								return this.clz;
							}
						}.setFields(method, type, clz);
					} catch (Exception e) {
						throw new OptionsException(
								"An exception was raised while processing option: "
										+ method.getName() + " for class "
										+ clz.getName(), e);
					}
				}
				String shortName;
				OptionProcessor optionProcessor = new OptionProcessor(command,
						option);
				if (option.shortName() != ' ') {
					shortName = new String(new char[] { option.shortName() });
					if (option.propertyValue()) {
						if (propsNames.put(shortName, optionProcessor) != null) {
							throw new OptionsException("Option " + shortName
									+ " is already defined");
						}
					} else {
						if (shortNames.put(shortName, optionProcessor) != null) {
							throw new OptionsException("Option " + shortName
									+ " is already defined");
						}
					}
				}
				String longName;
				if ((longName = option.longName()).length() > 0) {
					if (option.propertyValue()) {
						if (propsNames.put(longName, optionProcessor) != null) {
							throw new OptionsException("Option " + longName
									+ " is already defined");
						}
					} else {
						if (longNames.put(longName, optionProcessor) != null) {
							throw new OptionsException("Option " + longName
									+ " is already defined");
						}
					}
				}
				if (option.defaultValue().length() > 0) {
					Collection<OptionProcessor> defaultOptions = defaultValues
							.get(clz);
					if (defaultOptions == null) {
						defaultOptions = new HashSet<OptionProcessor>();
						defaultValues.put(clz, defaultOptions);
					}
					defaultOptions.add(optionProcessor);
				}
				options.add(option);
			}
		}
	}

	static Collection<Class<?>> primitiveTypes = new HashSet<Class<?>>();

	static {
		primitiveTypes.add(Byte.TYPE);
		primitiveTypes.add(Short.TYPE);
		primitiveTypes.add(Integer.TYPE);
		primitiveTypes.add(Long.TYPE);
		primitiveTypes.add(Double.TYPE);
		primitiveTypes.add(Float.TYPE);
	}

	/**
	 * @param type
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 */
	private ConvertCommand convert(Class<?> type) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		if (type == String.class) {
			return new ConvertCommand() {

				@Override
				public Object execute(String value) throws Exception {
					return value;
				}

			};
		} else if (Number.class.isAssignableFrom(type)
				|| primitiveTypes.contains(type)) {
			// This is a Number type instance...
			if (type == Integer.class || type == Integer.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Integer.valueOf(value);
					}

				};
			} else if (type == Long.class || type == Long.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Long.valueOf(value);
					}

				};
			} else if (type == Double.class || type == Double.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Double.valueOf(value);
					}

				};
			} else if (type == Float.class || type == Float.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Float.valueOf(value);
					}

				};
			} else if (type == Short.class || type == Short.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Short.valueOf(value);
					}

				};
			} else if (type == Byte.class || type == Byte.TYPE) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return Byte.valueOf(value);
					}

				};
			} else if (type == AtomicLong.class) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return new AtomicLong(Long.parseLong(value));
					}

				};
			} else if (type == AtomicInteger.class) {
				return new ConvertCommand() {

					@Override
					public Object execute(String value) throws Exception {
						return new AtomicInteger(Integer.parseInt(value));
					}

				};
			} else {
				throw new RuntimeException("Unknown class type "
						+ type.getName());
			}
		} else if (type == Boolean.class || type == Boolean.TYPE) {
			return new ConvertCommand() {

				@Override
				public Object execute(String value) throws Exception {
					return Boolean.valueOf(value);
				}

			};
		} else {

			return new ConvertCommand() {

				private Class<?> type;

				@Override
				public Object execute(String value) throws Exception {
					// We are assuming that whatever class instance is being
					// created it contains a constructor that expects a String
					// as a parameter.
					Constructor<?> constructor = type
							.getConstructor(String.class);
					return constructor.newInstance(value);
				}

				public ConvertCommand setFields(Class<?> type) {
					this.type = type;
					return this;
				}

			}.setFields(type);
		}
	}

	public Options(Collection<Class<?>> cliOptions) throws OptionsException {
		this(cliOptions.toArray(new Class<?>[0]));
	}
}
