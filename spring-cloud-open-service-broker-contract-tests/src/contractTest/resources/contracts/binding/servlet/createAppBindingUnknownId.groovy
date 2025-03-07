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

package binding.servlet

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method PUT()
        body(file("create_app_binding.json"))
        url '/v2/service_instances/service-instance-four-id/service_bindings/service-binding-one-id'
        headers {
            contentType('application/json')
        }
    }
    response {
        status UNPROCESSABLE_ENTITY()
        body([
                "description": $(regex('.+id=service-instance-four-id'))
        ])
        headers {
            contentType('application/json')
        }
    }
}
