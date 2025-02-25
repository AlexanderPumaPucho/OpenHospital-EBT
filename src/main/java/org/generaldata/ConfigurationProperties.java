/*
 * Open Hospital (www.open-hospital.org)
 * Copyright © 2006-2024 Informatici Senza Frontiere (info@informaticisenzafrontiere.org)
 *
 * Open Hospital is a free and open source software for healthcare data management.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.generaldata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Prototype (abstract) class for Open Hospital configuration files (.properties)
 *
 * @author Mwithi
 *
 */
public abstract class ConfigurationProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProperties.class);
	private static final boolean EXIT_ON_FAIL = false;
	private static boolean initialized = true;

	private Properties prop;

	/**
	 * Constructor for standard configuration
	 * (if missing the application will exit)
	 *
	 * @param fileProperties - the file name (to be available in the classpath)
	 */
	protected ConfigurationProperties(String fileProperties) {
		this.prop = loadPropertiesFile(fileProperties, LOGGER, EXIT_ON_FAIL);
	}

	/**
	 * Constructor for standard configuration
	 * @param fileProperties - the file name (to be available in the classpath)
	 * @param exitOnFail - if {@code true} the application will exit if configuration 
	 * is missing, otherwise default values will be used
	 */
	protected ConfigurationProperties(String fileProperties, boolean exitOnFail) {
		this.prop = loadPropertiesFile(fileProperties, LOGGER, exitOnFail);
	}

	/**
	 * Static configuration loader
	 * @param fileProperties - the file name (to be available in the classpath)
	 * @param logger - the {@link Logger} of the concrete class
	 */
	public static Properties loadPropertiesFile(String fileProperties, Logger logger) {
		return loadPropertiesFile(fileProperties, logger, false);
	}

	/**
	 * Static configuration loader
	 * @param fileProperties - the file name (to be available in the classpath)
	 * @param logger - the {@link Logger} of the concrete class
	 * @param exitOnFail - if {@code true} the application will exit if configuration 
	 * is missing, otherwise default values will be used
	 */
	private static Properties loadPropertiesFile(String fileProperties, Logger logger, boolean exitOnFail) {
		Properties prop = new Properties();
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileProperties)) {
			if (null == in) {
				logger.error(">> '{}' file not found.", fileProperties);
				initialized = false;
				if (exitOnFail) {
					System.exit(1);
				}
				return prop;
			}
			prop.load(in);
			logger.info("File {} loaded.", fileProperties);
		} catch (IOException e) {
			logger.error(">> '{}' file not found.", fileProperties);
			initialized = false;
			if (exitOnFail) {
				System.exit(1);
			}
		}
		return prop;
	}

	/**
	 * Method to retrieve a property
	 *
	 * @param property
	 * @return
	 */
	protected String myGetProperty(String property) {
		return prop.getProperty(property);
	}

	/**
	 * Method to retrieve an integer property
	 *
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	protected int myGetProperty(String property, int defaultValue) {
		int value;
		try {
			value = Integer.parseInt(prop.getProperty(property));
		} catch (Exception e) {
			LOGGER.warn("{} property not found: default is {}", property, defaultValue);
			return defaultValue;
		}
		return value;
	}

	/**
	 * Method to retrieve a boolean property
	 *
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	protected boolean myGetProperty(String property, boolean defaultValue) {
		boolean value;
		try {
			value = prop.getProperty(property).equalsIgnoreCase("YES");
		} catch (Exception e) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * Method to retrieve a double property
	 *
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	protected double myGetProperty(String property, double defaultValue) {
		double value;
		try {
			value = Double.parseDouble(prop.getProperty(property));
		} catch (Exception e) {
			LOGGER.warn("{} property not found: default is {}", property, defaultValue);
			return defaultValue;
		}
		return value;
	}

	/**
	 * Method to retrieve a string property
	 *
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	protected String myGetProperty(String property, String defaultValue) {
		String value;
		value = prop.getProperty(property);
		if (value == null) {
			LOGGER.warn(">> {} property not found: default is {}", property, defaultValue);
			return defaultValue;
		}
		return value;
	}

	/**
	 * Method to know the {@link ConfigurationProperties} initialization status.
	 * 
	 * @return {@code true} if the ConfigurationProperties is initialized, {@code false} otherwise
	 */
	public boolean isInitialized() {
		return initialized;
	}
}
