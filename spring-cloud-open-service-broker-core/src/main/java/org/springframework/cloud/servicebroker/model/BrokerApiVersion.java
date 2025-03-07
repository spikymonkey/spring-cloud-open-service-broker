/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.servicebroker.model;

/**
 * Object for configuring the version of the API supported by the service broker
 */
public class BrokerApiVersion {

	/**
	 * API Version header
	 */
	public final static String DEFAULT_API_VERSION_HEADER = "X-Broker-Api-Version";

	/**
	 * API Version value representing any version
	 */
	public final static String API_VERSION_ANY = "*";

	/**
	 * Current API Version supported by this library
	 */
	public final static String API_VERSION_CURRENT = "2.16";

	private final String brokerApiVersionHeader;

	private final String apiVersion;

	/**
	 * Specify the name of the service broker API version header and the API version supported by the service broker.
	 *
	 * @param apiVersionHeader the name of the HTTP header field expected to contain the service broker API version of
	 * 		the service broker client
	 * @param apiVersion the version of the service broker API supported by the broker; a value of {@literal null} or
	 *        {@literal API_VERSION_ANY} will disable API version validation
	 */
	public BrokerApiVersion(String apiVersionHeader, String apiVersion) {
		this.brokerApiVersionHeader = apiVersionHeader;
		this.apiVersion = apiVersion;
	}

	/**
	 * Specify the service broker API version supported by the service broker.
	 *
	 * @param apiVersion the version of the broker API supported by the broker; a value of {@literal null} or
	 *        {@literal API_VERSION_ANY} will disable API version validation
	 */
	public BrokerApiVersion(String apiVersion) {
		this(DEFAULT_API_VERSION_HEADER, apiVersion);
	}

	/**
	 * Specify that service broker API validation should be disabled.
	 */
	public BrokerApiVersion() {
		this(API_VERSION_ANY);
	}

	/**
	 * Get the service broker API version supported by the service broker.
	 *
	 * @return the service broker API version supported by the service broker
	 */
	public String getApiVersion() {
		return apiVersion;
	}

	/**
	 * Get the name of the service broker API version header.
	 *
	 * @return the name of the service broker API version header
	 */
	public String getBrokerApiVersionHeader() {
		return brokerApiVersionHeader;
	}

}
