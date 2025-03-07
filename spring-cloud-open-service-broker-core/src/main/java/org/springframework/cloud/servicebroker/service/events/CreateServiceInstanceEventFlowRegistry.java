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

package org.springframework.cloud.servicebroker.service.events;

import java.util.List;

import reactor.core.publisher.Flux;

import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.events.flows.CreateServiceInstanceCompletionFlow;
import org.springframework.cloud.servicebroker.service.events.flows.CreateServiceInstanceErrorFlow;
import org.springframework.cloud.servicebroker.service.events.flows.CreateServiceInstanceInitializationFlow;

/**
 * Event flow registry for create service instance requests
 *
 * @author Roy Clarkson
 */
public class CreateServiceInstanceEventFlowRegistry extends EventFlowRegistry<CreateServiceInstanceInitializationFlow,
		CreateServiceInstanceCompletionFlow, CreateServiceInstanceErrorFlow, CreateServiceInstanceRequest,
		CreateServiceInstanceResponse> {

	/**
	 * Construct a new {@link CreateServiceInstanceEventFlowRegistry}
	 */
	@Deprecated
	public CreateServiceInstanceEventFlowRegistry() {
		super();
	}

	/**
	 * Construct a new {@link CreateServiceInstanceEventFlowRegistry}
	 *
	 * @param initializationFlows the initialization flows
	 * @param completionFlows the completion flows
	 * @param errorFlows the error flows
	 */
	public CreateServiceInstanceEventFlowRegistry(
			final List<CreateServiceInstanceInitializationFlow> initializationFlows,
			final List<CreateServiceInstanceCompletionFlow> completionFlows,
			final List<CreateServiceInstanceErrorFlow> errorFlows) {
		super(initializationFlows, completionFlows, errorFlows);
	}

	@Override
	public Flux<Void> getInitializationFlows(CreateServiceInstanceRequest request) {
		return getInitializationFlowsInternal()
				.flatMap(flow -> flow.initialize(request));
	}

	@Override
	public Flux<Void> getCompletionFlows(CreateServiceInstanceRequest request, CreateServiceInstanceResponse response) {
		return getCompletionFlowsInternal()
				.flatMap(flow -> flow.complete(request, response));
	}

	@Override
	public Flux<Void> getErrorFlows(CreateServiceInstanceRequest request, Throwable t) {
		return getErrorFlowsInternal()
				.flatMap(flow -> flow.error(request, t));
	}

}
