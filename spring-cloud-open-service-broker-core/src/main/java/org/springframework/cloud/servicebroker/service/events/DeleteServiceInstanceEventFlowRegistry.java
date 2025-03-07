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

import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.events.flows.DeleteServiceInstanceCompletionFlow;
import org.springframework.cloud.servicebroker.service.events.flows.DeleteServiceInstanceErrorFlow;
import org.springframework.cloud.servicebroker.service.events.flows.DeleteServiceInstanceInitializationFlow;

/**
 * Event flow registry for delete service instance requests
 *
 * @author Roy Clarkson
 */
public class DeleteServiceInstanceEventFlowRegistry extends EventFlowRegistry<DeleteServiceInstanceInitializationFlow,
		DeleteServiceInstanceCompletionFlow, DeleteServiceInstanceErrorFlow, DeleteServiceInstanceRequest,
		DeleteServiceInstanceResponse> {

	/**
	 * Constructs a new {@link DeleteServiceInstanceEventFlowRegistry}
	 */
	@Deprecated
	public DeleteServiceInstanceEventFlowRegistry() {
		super();
	}

	/**
	 * Construct a new {@link DeleteServiceInstanceEventFlowRegistry}
	 *
	 * @param initializationFlows the initialization flows
	 * @param completionFlows the completion flows
	 * @param errorFlows the error flows
	 */
	public DeleteServiceInstanceEventFlowRegistry(
			final List<DeleteServiceInstanceInitializationFlow> initializationFlows,
			final List<DeleteServiceInstanceCompletionFlow> completionFlows,
			final List<DeleteServiceInstanceErrorFlow> errorFlows) {
		super(initializationFlows, completionFlows, errorFlows);
	}

	@Override
	public Flux<Void> getInitializationFlows(DeleteServiceInstanceRequest request) {
		return getInitializationFlowsInternal()
				.flatMap(flow -> flow.initialize(request));
	}

	@Override
	public Flux<Void> getCompletionFlows(DeleteServiceInstanceRequest request, DeleteServiceInstanceResponse response) {
		return getCompletionFlowsInternal()
				.flatMap(flow -> flow.complete(request, response));
	}

	@Override
	public Flux<Void> getErrorFlows(DeleteServiceInstanceRequest request, Throwable t) {
		return getErrorFlowsInternal()
				.flatMap(flow -> flow.error(request, t));
	}

}
