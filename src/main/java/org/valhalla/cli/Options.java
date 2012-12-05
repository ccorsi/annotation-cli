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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.valhalla.cli.annotations.Option;
import org.valhalla.cli.annotations.OptionReference;

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
	
	private static final String FOUR_SPACE_TAB = "    ";
	private static final Logger logger = LoggerFactory.getLogger(Options.class);

	/**
	 * This interface is used by the Options instance to convert the string
	 * value that is passed as part of the command line parameter list into the
	 * required type that the processor instance will require when applying the
	 * update to the object.
	 * 
	 * @author Claudio Corsi
	 * 
	 */
	private static interface ConvertCommand {
		/**
		 * This method will convert the passed string value into the required
		 * type instance.
		 * 
		 * @param value
		 *            The value to be converted
		 * @return An instance of the converted type
		 * @throws Exception
		 *             If there was an issue while trying to convert the passed
		 *             value into the expected type instance.
		 */
		Object execute(String value) throws Exception;
	}

	private static class StringConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return value;
		}

	}

	private static class IntegerConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Integer.valueOf(value);
		}

	}
	
	private static class LongConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Long.valueOf(value);
		}

	}
	
	private static class DoubleConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Double.valueOf(value);
		}

	}
	
	private static class FloatConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Float.valueOf(value);
		}

	}
	
	private static class ShortConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Short.valueOf(value);
		}

	}
	
	private static class ByteConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Byte.valueOf(value);
		}

	}
	
	private static class AtomicLongConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return new AtomicLong(Long.parseLong(value));
		}

	}
	
	private static class AtomicIntegerConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return new AtomicInteger(Integer.parseInt(value));
		}

	}
	
	private static class BigIntegerConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return new BigInteger(value);
		}

	}
	
	private static class BigDecimalConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return new BigDecimal(value);
		}

	}

	private static class BooleanConvertCommand implements ConvertCommand {

		@Override
		public Object execute(String value) throws Exception {
			return Boolean.valueOf(value);
		}

	}

	private static class ClassConvertCommand implements ConvertCommand {

		private Class<?> type;

		ClassConvertCommand(Class<?> type) {
			this.type = type;
		}
		
		@Override
		public Object execute(String value) throws Exception {
			// We are assuming that whatever class instance is being
			// created it contains a constructor that expects a String
			// as a parameter.
			Constructor<?> constructor = type
					.getConstructor(String.class);
			return constructor.newInstance(value);
		}

	}

	private static Map<Class<?>, ConvertCommand> converters = new HashMap<Class<?>, ConvertCommand>();

	static {
		converters.put(String.class, new StringConvertCommand());
		converters.put(Integer.class, new IntegerConvertCommand());
		converters.put(Integer.TYPE, converters.get(Integer.class));
		converters.put(Long.class, new LongConvertCommand());
		converters.put(Long.TYPE, converters.get(Long.class));
		converters.put(Double.class, new DoubleConvertCommand());
		converters.put(Double.TYPE, converters.get(Double.class));
		converters.put(Float.class, new FloatConvertCommand());
		converters.put(Float.TYPE, converters.get(Float.class));
		converters.put(Short.class, new ShortConvertCommand());
		converters.put(Short.TYPE, converters.get(Short.class));
		converters.put(Byte.class, new ByteConvertCommand());
		converters.put(Byte.TYPE, converters.get(Byte.class));
		converters.put(AtomicLong.class, new AtomicLongConvertCommand());
		converters.put(AtomicInteger.class, new AtomicIntegerConvertCommand());
		converters.put(BigInteger.class, new BigIntegerConvertCommand());
		converters.put(BigDecimal.class, new BigDecimalConvertCommand());
		converters.put(Boolean.class, new BooleanConvertCommand());
		converters.put(Boolean.TYPE, converters.get(Boolean.class));
	}
	
	private Map<String, OptionProcessor> shortNames = new HashMap<String, OptionProcessor>();
	private Map<String, OptionProcessor> longNames = new HashMap<String, OptionProcessor>();
	private Map<Class<?>, Collection<OptionProcessor>> defaultValues = new HashMap<Class<?>, Collection<OptionProcessor>>();
	private Map<String, OptionProcessor> propsNames = new HashMap<String, OptionProcessor>();
	private Collection<Option> options = new LinkedList<Option>();
	private Collection<Map.Entry<Method, Option>> methodOptions = new LinkedList<Map.Entry<Method, Option>>();
	private Collection<Option> requiredOptions = new LinkedList<Option>();

	/**
	 * This constructor will expect an array of classes that contains methods
	 * annotated with the Option annotation.
	 * 
	 * @param cliOptions
	 *            Array of classes with Option annotation
	 * @throws OptionsException
	 *             If there is any inconsistency when processing the class array
	 */
	public Options(Class<?> cliOptions[]) throws OptionsException {
		// Process all of the classes.
		for (Class<?> clz : findOptionClasses(cliOptions)) {
			processCLIOptions(clz);
		}
	}

	/**
	 * This method will determine all classes that contain Option references.
	 * 
	 * @param cliOptions Array of top-level Classes with Option references
	 * 
	 * @return Array of classes that contain Option references
	 */
	private List<Class<?>> findOptionClasses(Class<?>[] cliOptions) {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		Queue<Class<?>> moreClasses = new LinkedList<Class<?>>();
		for(Class<? >cliOption : cliOptions) { 
			classes.add(cliOption); 
		}
		moreClasses.addAll(classes);
		
		while (!moreClasses.isEmpty()) {
			Class<?> clazz = moreClasses.remove();
			for(Field field : clazz.getFields()) {
				if (field.getAnnotation(OptionReference.class) != null) {
					Class<?> type = field.getType();
					logger.debug("Adding class: {} from field {}", type, field.getName());
					// This field contains references to Options that need to be processed.
					moreClasses.add(type);
					classes.add(type);
				}
			}
			for(Method method : clazz.getMethods()) {
				if (method.getAnnotation(OptionReference.class) != null) {
					Class<?> type = method.getReturnType();
					if (!classes.contains(type)) {
						logger.debug("Adding class: {} from method {}", type, method.getName());
						// This method contains references to Options that need
						// to be processed.
						moreClasses.add(type);
						classes.add(type);
					}
				}
			}
		}
		
		return classes;
	}

	/**
	 * This method will process the command line parameters and apply the
	 * results to the passed objects. It will return all remaining parameters
	 * that were not processed. Those remaining arguments allow the calling
	 * method to not concern itself with purging any options from the argument
	 * list.
	 * 
	 * @param args
	 *            The command line parameters
	 * @param objects
	 *            The instances that the passed arguments will be applied to
	 * @return An array of the remaining arguments that were not processed
	 * @throws OptionsException
	 *             If any inconsistency happened while processing the command
	 *             line parameters
	 */
	public String[] processArguements(String[] args, Object[] objects)
			throws OptionsException {
		objects = findOptionObjects(objects);
		// Process all default values
		for (Object object : objects) {
			Collection<OptionProcessor> defaultOptions = defaultValues
					.get(object.getClass());
			if (defaultOptions != null) {
				for (OptionProcessor option : defaultOptions) {
					try {
						option.process(object, option.getOption()
								.defaultValue());
					} catch (Exception e) {
						logger.error("An exception was raised while trying to set the default value for option {}", option.getOption(), e);
						throw new OptionsException(
								"An exception was raised while trying to set the default value for option "
										+ option.getOption(), e);
					}
				}
			}
		}
		List<String> argsList = new LinkedList<String>();
		// This is used to determine if all of the required options have been
		// satisfied.
		Set<Option> processedOptions = new HashSet<Option>();
		// Process each argument on the command line
		for (int idx = 0; idx < args.length; idx++) {
			String arg = args[idx];
			if (arg.charAt(0) == '-') {
				Object object = null;
				String value = null;
				if (arg.charAt(1) == '-') {
					String name = arg.substring(2);
					OptionProcessor processor = this.longNames.get(name);
					if (processor == null) {
						// Possible long name as --foo=bar, look for = character
						int eq = name.indexOf('=');
						if (eq > 0 && eq != (name.length() - 1)) {
							processor = this.longNames.get(name
									.substring(0, eq));
							// The value is embedded with the long name
							value = name.substring(eq + 1);
						}
					} else {
						if (processor.hasValue()) {
							idx++;
							if (idx == args.length) {
								logger.error("Missing value for option {}", name);
								throw new OptionsException(
										"Missing value for option " + name);
							}
							value = args[idx];
						}
					}
					object = checkAndReturnTypeInstance(objects, name,
							processor);
					// Pass the name from the option longName value.
					processedOptions.add(applyValue(processor.getOption()
							.longName(), processor, object, value));
				} else {
					// This can be multiple short names or an embedded name.
					String name = arg.substring(1);
					OptionProcessor processor = this.shortNames.get(name);
					if (processor != null) {
						object = checkAndReturnTypeInstance(objects, name,
								processor);
						if (processor.hasValue()) {
							if (!processor.getOption().embeddedValue()) {
								idx++;
								if (idx == args.length) {
									logger.error("Missing value for option {}", name);
									throw new OptionsException(
											"Missing value for option " + name);
								}
								value = args[idx];
							} else {
								// The value is part of the passed option
								if (name.length() < 2) {
									logger.error("Missing embedded value for option {}", name);
									throw new OptionsException(
											"Missing embedded value for option "
													+ name);
								}
								value = name.substring(1);
							}
						}
						processedOptions.add(applyValue(name, processor,
								object, value));
					} else {
						String embeddedName = name.substring(0, 1);
						processor = this.shortNames.get(embeddedName);
						object = checkAndReturnTypeInstance(objects,
								embeddedName, processor);
						if (processor.getOption().embeddedValue()) {
							// The value is part of the passed option
							if (name.length() < 2) {
								logger.error("Missing embedded value for option {}", name);
								throw new OptionsException(
										"Missing embedded value for option "
												+ name);
							}
							value = name.substring(1);
							processedOptions.add(applyValue(embeddedName,
									processor, object, value));
						} else {
							if (processor.hasValue()) {
								idx++;
								if (idx == args.length) {
									logger.error("Missing value for option {}", embeddedName);
									throw new OptionsException(
											"Missing value for option "
													+ embeddedName);
								}
								value = args[idx];
							}
							processedOptions.add(applyValue(embeddedName,
									processor, object, value));
							// Process all short names
							for (int innerIdx = 1; innerIdx < name.length(); innerIdx++) {
								embeddedName = name.substring(innerIdx,
										innerIdx + 1);
								processor = this.shortNames.get(embeddedName);
								object = checkAndReturnTypeInstance(objects,
										embeddedName, processor);
								if (processor.hasValue()) {
									if (processor.getOption().embeddedValue()) {
										logger.error(
												"Embedded value not supported for embedded value when passed as multiple options command line parameter {} used as part command line options {}",
												embeddedName, name);
										// This is not supported
										throw new OptionsException(
												"Embedded value not supported for embedded value when passed as multiple options command line parameter "
														+ embeddedName
														+ " used as part command line options "
														+ name);
									}
									idx++;
									if (idx == args.length) {
										logger.error("Missing value for option {}", embeddedName);
										throw new OptionsException(
												"Missing value for option "
														+ embeddedName);
									}
									value = args[idx];
								}
								processedOptions.add(applyValue(embeddedName,
										processor, object, value));
							}
						}
					}
				}
			} else if (arg.indexOf('=') > -1) {
				int eqIdx = arg.indexOf('=');
				String name = arg.substring(0, eqIdx);
				OptionProcessor processor = this.propsNames.get(name);
				Object object = checkAndReturnTypeInstance(objects, name,
						processor);
				eqIdx++; // Move to the next index
				if (eqIdx == arg.length()) {
					logger.error("The passed option does not contain a value for option {}", name);
					throw new OptionsException(
							"The passed option does not contain a value for option "
									+ name);
				}
				String value = arg.substring(eqIdx);
				try {
					processor.process(object, value);
					processedOptions.add(processor.getOption());
				} catch (Exception e) {
					logger.error("An exception was thrown when processing option {}", name, e);
					throw new OptionsException(
							"An exception was thrown when processing option "
									+ name, e);
				}
			} else {
				argsList.add(arg);
			}
		}
		if (processedOptions.containsAll(requiredOptions) == false) {
			StringBuilder message = new StringBuilder(
					"Not all required options where included");
			message.append("missing:");
			// Get the list of options that were not passed.
			for (Option option : requiredOptions) {
				if (processedOptions.contains(option) == false) {
					message.append('[');
					if (option.shortName() != ' ') {
						message.append('-').append(option.shortName());
					}
					if (option.longName().length() > 0) {
						if (message.charAt(message.length() - 1) == '[') {
							message.append('|');
						} else {
							message.append('[');
						}
						if (option.propertyValue()) {
							message.append(option.longName())
									.append("=<value>");
						} else {
							message.append("--").append(option.longName());
						}
					}
					message.append(']');
				}
			}
			logger.error(message.toString());
			throw new OptionsException(message.toString());
		}
		return argsList.toArray(new String[0]);
	}

	/**
	 * @param objects
	 * @return
	 */
	private Object[] findOptionObjects(Object[] objects) {
		Queue<Object> objectList = new LinkedList<Object>();
		Queue<Object> moreObjects = new LinkedList<Object>();
		for(Object object : objects) {
			objectList.add(object);
		}
		moreObjects.addAll(objectList);
		logger.debug("moreObjects: {}", moreObjects);
		Object object;
		while( ( object = moreObjects.poll() ) != null ) {
			logger.debug("Processing object: {}", object);
			Class<?> clz = object.getClass();
			for(Field field : clz.getFields()) {
				logger.debug("Looking at field: {}", field.getName());
				if (field.getAnnotation(OptionReference.class) != null) {
					try {
						logger.debug("Found field with OptionReference: {}", field.getName());
						Object value = field.get(object);
						logger.debug("Returned field value: {}", value);
						moreObjects.add(value);
						objectList.add(value);
					} catch (IllegalArgumentException e) {
						logger.error(
								"Unable to retrieve value for field: {} for class: {}",
								new Object[] { field.getName(), clz.getName() },
								e);
						new OptionsException(
								"Unable to retrieve value for field: "
										+ field.getName() + " for class: "
										+ clz.getName(), e);
					} catch (IllegalAccessException e) {
						logger.error(
								"Unable to retrieve value for field: {} for class: {}",
								new Object[] { field.getName(), clz.getName() },
								e);
						new OptionsException(
								"Unable to retrieve value for field: "
										+ field.getName() + " for class: "
										+ clz.getName(), e);
					}
				}
			}
			for(Method method : clz.getMethods()) {
				logger.debug("Looking at method: {}", method.getName());
				if (method.getAnnotation(OptionReference.class) != null) {
					try {
						logger.debug("Found method with OptionReference: {}",
								method.getName());
						Object value = method.invoke(object);
						if (!objectList.contains(value)) {
							logger.debug("Returned method return value: {}",
									value);
							moreObjects.add(value);
							objectList.add(value);
						}
					} catch (IllegalArgumentException e) {
						logger.error(
								"Unable to retrieve value for method: {} for class: {}",
								new Object[] { method.getName(), clz.getName() },
								e);
						new OptionsException(
								"Unable to retrieve value for method: "
										+ method.getName() + " for class: "
										+ clz.getName(), e);
					} catch (IllegalAccessException e) {
						logger.error(
								"Unable to retrieve value for method: {} for class: {}",
								new Object[] { method.getName(), clz.getName() },
								e);
						new OptionsException(
								"Unable to retrieve value for method: "
										+ method.getName() + " for class: "
										+ clz.getName(), e);
					} catch (InvocationTargetException e) {
						logger.error(
								"Unable to retrieve value for method: {} for class: {}",
								new Object[] { method.getName(), clz.getName() },
								e);
						new OptionsException(
								"Unable to retrieve value for method: "
										+ method.getName() + " for class: "
										+ clz.getName(), e);
					}
				}
			}
		}
		return objectList.toArray();
	}

	/**
	 * The method will apply the value to the passed object using the passed
	 * processor. The name is only used to generate the exception if anything
	 * went wrong.
	 * 
	 * @param name
	 *            The name of the option that is being processed
	 * @param processor
	 *            The processor instance used to apply the value
	 * @param object
	 *            The object that the value will be applied to
	 * @param value
	 *            The value that will be applied to the object
	 * 
	 * @return Returns the passed processor associated Option annotation
	 * 
	 * @throws OptionsException
	 *             If any issues occurred when applying the value to the object
	 */
	private Option applyValue(String name, OptionProcessor processor,
			Object object, String value) throws OptionsException {
		try {
			logger.debug("Applying value: {} to object: {} for option: {}",
					new Object[] { value, object, name });
			processor.process(object, value);
			return processor.getOption();
		} catch (Exception e) {
			logger.error("An exception was thrown when processing option {}", name, e);
			throw new OptionsException(
					"An exception was thrown when processing option " + name, e);
		}
	}

	/**
	 * This method will check that the passed processor is non-null. It will
	 * then determine which of the passed instances in the array of objects that
	 * the processor instance will require.
	 * 
	 * @param objects
	 *            Array of objects containing the object used by the passed
	 *            processor
	 * @param name
	 *            The name of the option that the processor will process
	 * @param processor
	 *            The processor used to apply the option for the returned object
	 * @return The object that will be updated
	 * @throws OptionsException
	 *             If the passed processor is null or if there is no instance
	 *             that can be used by the processor
	 */
	private Object checkAndReturnTypeInstance(Object[] objects, String name,
			OptionProcessor processor) throws OptionsException {
		if (processor == null) {
			logger.error("No available option for {}", name);
			throw new OptionsException("No available option for " + name);
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
			logger.error("No object available to set option {}", name);
			throw new OptionsException("No object available to set option "
					+ name);
		}
		return object;
	}

	/**
	 * This method will extract all methods with the Option annotation and
	 * generate an internal data structure used to process the passed command
	 * line parameters.
	 * 
	 * @param clz
	 *            The class that contains method with the Option annotation
	 * @throws OptionsException
	 *             If there is any inconsistency while processing the class
	 */
	private void processCLIOptions(Class<?> clz) throws OptionsException {
		for (Method method : clz.getMethods()) {
			Option option = method.getAnnotation(Option.class);
			if (option != null) {
				OptionCommand command;
				Class<?> parameterTypes[] = method.getParameterTypes();
				if (parameterTypes.length > 1) {
					logger.error(
							"Invalid parameter length, allowed only none or one parameter for option class: {}",
							clz.getName());
					throw new OptionsException(
							"Invalid parameter length, allowed only none or one parameter for option class: "
									+ clz.getName());
				}
				boolean isEmbedded = option.embeddedValue();
				boolean isProperty = option.propertyValue();
				if (parameterTypes.length == 0) {
					if (isEmbedded || isProperty) {
						logger.error(
								"You can not set the embedded or property type option with no-parameter method {} for class {}",
								method.getName(), clz.getName());
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

						@Override
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
						logger.error(
								"An exception was raised while processing option: {} for class {}",
								new Object[] { method.getName(), clz.getName() }, e);
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
							logger.error("Option {} is already defined", shortName);
							throw new OptionsException("Option " + shortName
									+ " is already defined");
						}
					} else {
						if (shortNames.put(shortName, optionProcessor) != null) {
							logger.error("Option {} is already defined", shortName);
							throw new OptionsException("Option " + shortName
									+ " is already defined");
						}
					}
				}
				String longName;
				if ((longName = option.longName()).length() > 0) {
					if (option.propertyValue()) {
						if (propsNames.put(longName, optionProcessor) != null) {
							logger.error("Option {} is already defined", longName);
							throw new OptionsException("Option " + longName
									+ " is already defined");
						}
					} else {
						if (longNames.put(longName, optionProcessor) != null) {
							logger.error("Option {} is already defined", longName);
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
				methodOptions.add(new Map.Entry<Method, Option>() {

					private Method method;
					private Option option;

					@Override
					public Method getKey() {
						return this.method;
					}

					public Entry<Method, Option> setFields(Method method,
							Option option) {
						this.method = method;
						this.option = option;
						return this;
					}

					@Override
					public Option getValue() {
						return this.option;
					}

					@Override
					public Option setValue(Option arg0) {
						return this.option;
					}

				}.setFields(method, option));
				if (option.required()) {
					requiredOptions.add(option);
				}
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
	 * This method will generate an instance of a ConvertCommand instance that
	 * will transform the string value into the passed type class.
	 * 
	 * @param type
	 *            The type of instance that the ConvertCommand execute method
	 *            will return
	 * @return A ConvertCommand instance
	 */
	private ConvertCommand convert(Class<?> type) {
		ConvertCommand convertCommand = converters.get(type);
		if (convertCommand == null) {
			convertCommand = new ClassConvertCommand(type);
		}
		return convertCommand;
	}

	/**
	 * This constructor will expect a collection of class instances with method
	 * annotated with the Option annotation.
	 * 
	 * @param cliOptions
	 *            Collection of class to process
	 * @throws OptionsException
	 *             If there was any inconsistency when processing the collection
	 *             of classes
	 */
	public Options(Collection<Class<?>> cliOptions) throws OptionsException {
		this(cliOptions.toArray(new Class<?>[0]));
	}

	/**
	 * This method is used to display the usage command with all of the
	 * available options. It will return a string that will contain a printable
	 * string with consistent spacing. </p>
	 * 
	 * This method will assume that the screen will only display 120 characters
	 * per line and will format the returning string accordingly.
	 * 
	 * @param mainClass
	 *            The main class that a user calls and passed the command line
	 *            parameters to.
	 * @param message
	 *            The message that will be displayed explaining parameter line
	 *            choices.
	 * @return A formatted string with all of the options available
	 */
	public String usage(Class<?> mainClass, String message) {
		String lineSep = System.getProperty("line.separator");
		StringBuffer str = new StringBuffer("usage: java ");
		int startIndex = str.length();
		String className = mainClass.getName();
		if ( ( (str.length() - startIndex) + className.length() + message.length() ) <= 120 ) {
			str.append(className).append(" ").append(message).append(lineSep);
		} else if (((str.length() - startIndex) + className.length()) <= 120) {
			str.append(className).append(" ").append(lineSep);
			int maxLength = 120 - str.length() + startIndex;
			while (message.charAt(--maxLength) != ' ' && maxLength > 0)
				;
			if (maxLength != 0) {
				str.append(message, 0, maxLength);
			}
			// Add the line separator and continue to process the message string... 
			str.append(lineSep);
			// It is possible that we are just copying the whole string into the truncatedMessage string which is fine...
			String truncatedMessage = message.substring(maxLength);
			while (truncatedMessage.length() > 116) {
				logger.debug("Message is greater than 116 characters: " + truncatedMessage);
				maxLength = 120;
				while (truncatedMessage.charAt(--maxLength) != ' '
						&& maxLength > 0)
					;
				// Prepend each string with four spaces...
				str.append(FOUR_SPACE_TAB);
				if (maxLength != 0) {
					str.append(truncatedMessage, 0, maxLength);
					truncatedMessage = truncatedMessage.substring(maxLength);
				} else {
					// What, this single set of characters does not contain a
					// space for 120 characters!!!!!
					str.append(truncatedMessage);
					truncatedMessage = "";
				}
				// Always include the line separator...
				str.append(lineSep);
			}
			// Check if there is any remaining message information that needs to be displayed...
			if ( ! truncatedMessage.isEmpty() ) {
				str.append(FOUR_SPACE_TAB).append(truncatedMessage).append(lineSep);
			}
		} else {
			// What am I going to do here!!!!
			// For now, just display the extended method name and move on...
			str.append(className).append(" ").append(lineSep).append(message).append(lineSep);
		}
		// Let us process all of the different options and generate a standard output....
		for (Entry<Method, Option> methodOption : this.methodOptions) {
			Option option = methodOption.getValue();
			Method method = methodOption.getKey();
			startIndex = str.length();
			logger.debug("startIndex=" + startIndex);
			if (option.shortName() != ' ') {
				str.append("  ");
				if (!option.propertyValue()) {
					str.append("-").append(option.shortName());
					if (option.embeddedValue()) {
						str.append("[value]");
					} else {
						if (method.getParameterTypes().length > 0) {
							str.append(" [value]");
						}
					}
				} else {
					str.append(option.shortName()).append("=[value]");
				}
			}
			logger.debug("After short check str=[[" + str.toString() + "]]");
			if (option.longName().length() > 0) {
				// We've already included an option definition and add some spaces instead of a comma...
				if (startIndex == str.length()) {
					str.append("  ");
				} else {
					str.append(", ");
				}
				if (!option.propertyValue()) {
					str.append("--").append(option.longName());
					// Determine if a value is expected.
					if (method.getParameterTypes().length > 0) {
						str.append("[=value| value]");
					}
				} else {
					str.append(option.longName()).append("=[value]");
				}
			}
			logger.debug("After long check str=[[" + str.toString() + "]]");
			if (option.defaultValue().length() > 0) {
				str.append(", default=").append(option.defaultValue());
			}
			logger.debug("After default value check str=[[" + str.toString() + "]]");
			if (option.description().length() > 0) {
				String description = option.description();
				if (description.length() + (str.length() - startIndex) < 116) {
					str.append(FOUR_SPACE_TAB).append(option.description());
				} else {
					while(description.length() > 0) {
						logger.debug("Processing descrition: " + description);
						// Setup the current length of the current display string....
						int curLen = str.length() - startIndex;
						// Determine the last character that can be displayed on the screen
						int maxSize = 116 - curLen;
						// Find the end of the last space before the max
						if( description.length() >= maxSize) {
							while(description.charAt(--maxSize) != ' ' && maxSize > 0);
							if (maxSize == 0) {
								str.append(FOUR_SPACE_TAB).append(description);
								description = "";
							} else {
								str.append(FOUR_SPACE_TAB).append(description.substring(0, maxSize));
								int max = description.length();
								while(description.charAt(maxSize) == ' ' && ++maxSize < max);
								if (maxSize != max) {
									// Extract the smaller string and append a line separator...
									description = description.substring(maxSize);
									str.append(lineSep);
									startIndex = str.length();
								} else {
									description = "";
								}
							}
						} else {
							str.append(FOUR_SPACE_TAB).append(description);
							description = "";
						}
					}
				}
			}
			str.append(lineSep);
			logger.debug("After description check str=[[" + str.toString() + "]]");
		}
		return str.toString();
	}

	/**
	 * This method will return all of the Options that where part of the passed
	 * array of classes.
	 * 
	 * @return Collection containing all defined Option annotations
	 */
	public Collection<Option> getOptions() {
		return Collections.unmodifiableCollection(this.options);
	}
}
