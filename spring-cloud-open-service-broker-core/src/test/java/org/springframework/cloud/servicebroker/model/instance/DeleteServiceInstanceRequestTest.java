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

package org.springframework.cloud.servicebroker.model.instance;

import com.jayway.jsonpath.DocumentContext;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.servicebroker.JsonPathAssert;
import org.springframework.cloud.servicebroker.JsonUtils;
import org.springframework.cloud.servicebroker.model.Context;
import org.springframework.cloud.servicebroker.model.PlatformContext;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteServiceInstanceRequestTest {

	@Test
	void requestWithDefaultsIsBuilt() {
		DeleteServiceInstanceRequest request = DeleteServiceInstanceRequest.builder()
				.build();

		assertThat(request.getServiceDefinitionId()).isNull();
		assertThat(request.getServiceInstanceId()).isNull();
		assertThat(request.getPlanId()).isNull();
		assertThat(request.getServiceDefinition()).isNull();
		assertThat(request.isAsyncAccepted()).isEqualTo(false);
		assertThat(request.getApiInfoLocation()).isNull();
		assertThat(request.getPlatformInstanceId()).isNull();
		assertThat(request.getOriginatingIdentity()).isNull();
		assertThat(request.getRequestIdentity()).isNull();
	}

	@Test
	void requestWithAllValuesIsBuilt() {
		Context originatingIdentity = PlatformContext.builder()
				.platform("test-platform")
				.build();

		DeleteServiceInstanceRequest request = DeleteServiceInstanceRequest.builder()
				.serviceInstanceId("service-instance-id")
				.serviceDefinitionId("service-definition-id")
				.planId("plan-id")
				.asyncAccepted(true)
				.platformInstanceId("platform-instance-id")
				.apiInfoLocation("https://api.app.local")
				.originatingIdentity(originatingIdentity)
				.requestIdentity("request-id")
				.build();

		assertThat(request.getServiceInstanceId()).isEqualTo("service-instance-id");
		assertThat(request.getServiceDefinitionId()).isEqualTo("service-definition-id");
		assertThat(request.getPlanId()).isEqualTo("plan-id");
		assertThat(request.isAsyncAccepted()).isEqualTo(true);

		assertThat(request.getPlatformInstanceId()).isEqualTo("platform-instance-id");
		assertThat(request.getApiInfoLocation()).isEqualTo("https://api.app.local");
		assertThat(request.getOriginatingIdentity()).isEqualTo(originatingIdentity);
		assertThat(request.getRequestIdentity()).isEqualTo("request-id");
	}

	@Test
	void serializesAccordingToOsbSpecs() {
		Context originatingIdentity = PlatformContext.builder()
				.platform("test-platform")
				.build();

		DeleteServiceInstanceRequest request = DeleteServiceInstanceRequest.builder()
				.serviceInstanceId("service-instance-id")
				.serviceDefinitionId("service-definition-id")
				.planId("plan-id")
				.asyncAccepted(true)
				.platformInstanceId("platform-instance-id")
				.apiInfoLocation("https://api.app.local")
				.originatingIdentity(originatingIdentity)
				.requestIdentity("request-id")
				.plan(Plan.builder().build())
				.serviceDefinition(ServiceDefinition.builder().build())
				.build();

		DocumentContext json = JsonUtils.toJsonPath(request);

		//OSB fields
		JsonPathAssert.assertThat(json).hasPath("$.service_id").isEqualTo("service-definition-id");
		JsonPathAssert.assertThat(json).hasPath("$.plan_id").isEqualTo("plan-id");
		JsonPathAssert.assertThat(json).hasPath("$.accepts_incomplete").isEqualTo(true);
		//Other internals should not be polluting the JSON representation
		JsonPathAssert.assertThat(json).hasMapAtPath("$").hasSize(3);
		JsonPathAssert.assertThat(json).hasNoPath("$.request_identity");
	}

	@Test
	void equalsAndHashCode() {
		EqualsVerifier
				.forClass(DeleteServiceInstanceRequest.class)
				.withRedefinedSuperclass()
				.suppress(Warning.NONFINAL_FIELDS)
				.suppress(Warning.TRANSIENT_FIELDS)
				.verify();
	}

}
