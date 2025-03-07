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

package org.springframework.cloud.servicebroker.model.binding;

import java.util.HashMap;
import java.util.Map;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import org.springframework.cloud.servicebroker.JsonUtils;

import static org.assertj.core.api.Assertions.assertThat;

class BindResourceTest {

	@Test
	void bindResourceIsBuiltWithDefaults() {
		BindResource bindResource = BindResource.builder()
				.build();

		assertThat(bindResource.getAppGuid()).isNull();
		assertThat(bindResource.getRoute()).isNull();
		assertThat(bindResource.getProperties()).isEmpty();
	}

	@Test
	void bindResourceIsBuiltWithAllValues() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("parameter3", "value3");
		parameters.put("parameter4", true);

		BindResource bindResource = BindResource.builder()
				.appGuid("app-guid")
				.route("route")
				.properties("parameter1", "value1")
				.properties("parameter2", 2)
				.properties(parameters)
				.build();

		assertThat(bindResource.getAppGuid()).isEqualTo("app-guid");
		assertThat(bindResource.getRoute()).isEqualTo("route");
		assertThat(bindResource.getProperties()).hasSize(4);
		assertThat(bindResource.getProperties().get("parameter1")).isEqualTo("value1");
		assertThat(bindResource.getProperties().get("parameter2")).isEqualTo(2);
		assertThat(bindResource.getProperties().get("parameter3")).isEqualTo("value3");
		assertThat(bindResource.getProperties().get("parameter4")).isEqualTo(true);
	}

	@Test
	void bindResourceIsDeserializedFromJson() {
		BindResource bindResource = JsonUtils.readTestDataFile("bindResource.json", BindResource.class);

		assertThat(bindResource.getAppGuid()).isEqualTo("test-app-guid");
		assertThat(bindResource.getRoute()).isEqualTo("https://test.app.local");
		assertThat(bindResource.getProperties()).hasSize(3);
		assertThat(bindResource.getProperty("property1")).isEqualTo(1);
		assertThat(bindResource.getProperty("property2")).isEqualTo("value2");
		assertThat(bindResource.getProperty("property3")).isEqualTo(true);
	}

	@Test
	void equalsAndHashCode() {
		EqualsVerifier
				.forClass(BindResource.class)
				.verify();
	}

}
