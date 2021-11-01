package com.lukascode.location.integration.autocomplete

import org.springframework.web.util.UriTemplateHandler
import spock.lang.Specification

class AutocompleteUriBuilderSpec extends Specification {

    def templateHandler = Mock(UriTemplateHandler)

    def setup() {
        templateHandler.expand(_) >> { args -> new URI(args[0]) }
    }

    def "should build uri properly"() {
        given:
        def builder = AutocompleteUriBuilder
                .of(new AutocompleteQuery("Warsaw", "pl"))

        when:
        def uri = builder.build(templateHandler)

        then:
        uri.toString() == "/place/autocomplete/json?input=Warsaw&language=pl&types=(cities)"
    }

}
