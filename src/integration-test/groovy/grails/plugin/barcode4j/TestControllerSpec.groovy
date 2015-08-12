/*
 * Copyright 2010 Grails Plugin Collective
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
package grails.plugin.barcode4j

import grails.plugin.barcode4j.test.TestController
import grails.test.mixin.integration.Integration
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.krysalis.barcode4j.impl.code39.Code39Bean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.mock.http.client.MockClientHttpRequest
import org.springframework.mock.http.client.MockClientHttpResponse
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import spock.lang.*

/**
 * This actually exercises most of the code in the barcode4j service, as well as tests
 * the controller method support and support for reloading.
 */
@Integration
class TestControllerSpec extends Specification {

	@Autowired
	TestController controller

	@Unroll
	def "rendering #action works from controllers"() {
		setup:
		def request = new MockHttpServletRequest()
		def response = new MockHttpServletResponse()
		def servletContext = new MockServletContext()
		RequestContextHolder.setRequestAttributes(new GrailsWebRequest(request, response, servletContext), true)

		when:
//		def controller = createController()
		controller."$action"()

		then:
		notThrown(MissingMethodException)
		response.contentType == contentType
		response.content.size() == contentLength
		
		where:
		action     | contentType  | contentLength
//FIXME: For some reason bean code39 from spring/resources.groovy is not created
//		'gif'      | "image/gif"  | 1
//		'png'      | "image/png"  | 1
//		'jpeg'     | "image/jpeg" | 10086
		'gifBean'  | "image/gif"  | 3506
		'pngBean'  | "image/png"  | 353
		'jpegBean' | "image/jpeg" | 10086
	}
	
}
