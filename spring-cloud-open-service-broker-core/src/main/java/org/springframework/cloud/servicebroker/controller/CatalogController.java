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

package org.springframework.cloud.servicebroker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.cloud.servicebroker.annotation.ServiceBrokerRestController;
import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.service.CatalogService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Provide endpoints for the catalog API.
 *
 * @author sgreenberg@pivotal.io
 * @author Scott Frederick
 * @see <a href="https://github.com/openservicebrokerapi/servicebroker/blob/master/spec.md#catalog-management">Open
 * 		Service Broker API specification</a>
 */
@ServiceBrokerRestController
public class CatalogController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

	/**
	 * Construct a new {@link CatalogController}
	 *
	 * @param service the catalog service
	 */
	public CatalogController(CatalogService service) {
		super(service);
	}

	/**
	 * REST controller for getting a catalog
	 *
	 * @return the catalog
	 */
	@GetMapping({"/v2/catalog", "{platformInstanceId}/v2/catalog"})
	public Mono<Catalog> getCatalog() {
		return catalogService.getCatalog()
				.doOnRequest(v -> LOG.info("Retrieving catalog"))
				.doOnSuccess(catalog -> {
					LOG.info("Success retrieving catalog");
					LOG.debug("catalog={}", catalog);
				})
				.doOnError(e -> LOG.error("Error retrieving catalog. error=" + e.getMessage(), e));
	}

}
