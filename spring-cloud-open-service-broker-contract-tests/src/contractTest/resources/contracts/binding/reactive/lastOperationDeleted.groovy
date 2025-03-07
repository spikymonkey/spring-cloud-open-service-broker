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

package binding.reactive

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method GET()
        url '/v2/service_instances/service-instance-three-id/service_bindings/service-binding-one-id/last_operation'
    }
    response {
        status GONE()
        body([
                "state"      : "succeeded",
                "description": "all good"
        ])
        headers {
            contentType('application/json')
        }
    }
}
