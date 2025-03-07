/*
 * Copyright 2002-2021 the original author or authors.
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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BindingMetadataTest {

	@Test
	void bindingMetadataIsBuiltWithDefaults() {
		BindingMetadata metadata = BindingMetadata.builder()
				.build();

		assertThat(metadata.getExpiresAt()).isNull();
	}

	@Test
	void bindingMetadataIsBuildWithAllValues() {
		BindingMetadata metadata = BindingMetadata.builder()
				.expiresAt("2019-12-31T23:59:59.0Z")
				.build();

		assertThat(metadata.getExpiresAt()).isEqualTo("2019-12-31T23:59:59.0Z");
	}

	@Test
	void equalsAndHashCode() {
		EqualsVerifier
				.forClass(BindingMetadata.class)
				.verify();
	}
}
