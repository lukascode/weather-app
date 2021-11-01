package com.lukascode.location.infrastructure.configuration.google


import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import spock.lang.Specification

class GoogleApiKeyInterceptorSpec extends Specification {

    static String TEST_API_KEY = UUID.randomUUID().toString()

    def "should add apiKey as request parameter"() {
        given:
        byte[] body = [1, 2, 3, 4]
        def httpRequest = Mock(HttpRequest) {
            getMethod() >> HttpMethod.GET
            getURI() >> new URI("")
            getHeaders() >> new HttpHeaders()
        }
        def execution = Mock(ClientHttpRequestExecution)
        def interceptor = new GoogleApiKeyInterceptor(TEST_API_KEY)

        when:
        interceptor.intercept(httpRequest, body, execution)

        then:
        1 * execution.execute(_ as HttpRequest, body) >> {
            assert (it[0] as HttpRequest).URI.toString().contains("?key=" + TEST_API_KEY)
        }
    }
}
