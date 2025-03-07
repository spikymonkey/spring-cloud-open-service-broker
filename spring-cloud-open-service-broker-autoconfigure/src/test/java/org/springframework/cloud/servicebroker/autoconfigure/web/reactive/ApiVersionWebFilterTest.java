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

package org.springframework.cloud.servicebroker.autoconfigure.web.reactive;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import org.springframework.cloud.servicebroker.model.BrokerApiVersion;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class ApiVersionWebFilterTest {

	private static final String V2_API_PATH_PATTERN = "/v2/**";

	private MockServerWebExchange exchange;

	@Mock
	private WebFilterChain chain;

	@Test
	void noBrokerApiVersionConfigured() {
		setUpVersionResponse("9.9");
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter();
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void anyVersionAccepted() {
		setUpVersionResponse("9.9");
		BrokerApiVersion brokerApiVersion = new BrokerApiVersion("header", BrokerApiVersion.API_VERSION_ANY);
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter(brokerApiVersion);
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void versionsMatch() {
		setUpVersionResponse("9.9");
		BrokerApiVersion brokerApiVersion = new BrokerApiVersion("header", "9.9");
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter(brokerApiVersion);
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void versionMismatch() {
		setUpVersionResponse("9.9");
		BrokerApiVersion brokerApiVersion = new BrokerApiVersion("header", "8.8");
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter(brokerApiVersion);
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
	}

	@Test
	void versionHeaderIsMissing() {
		setUpVersionResponse(null);
		BrokerApiVersion brokerApiVersion = new BrokerApiVersion("header", "9.9");
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter(brokerApiVersion);
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void versionHeaderIsMissingAnyVersionAccepted() {
		setUpVersionResponse(null);
		BrokerApiVersion brokerApiVersion = new BrokerApiVersion("header", BrokerApiVersion.API_VERSION_ANY);
		ApiVersionWebFilter webFilter = new ApiVersionWebFilter(brokerApiVersion);
		webFilter.filter(exchange, chain).block();
		assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	private void setUpVersionResponse(String version) {
		MockServerHttpRequest request;
		if (version == null) {
			request = MockServerHttpRequest
					.get(V2_API_PATH_PATTERN)
					.build();
		}
		else {
			request = MockServerHttpRequest
					.get(V2_API_PATH_PATTERN)
					.header("header", version)
					.build();
		}
		this.exchange = MockServerWebExchange.from(request);
		openMocks(this);
		exchange.getResponse().setStatusCode(HttpStatus.OK);
		given(chain.filter(exchange))
				.willReturn(Mono.empty());
	}

}
