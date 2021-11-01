package com.lukascode.location.integration.placedetails.photo


import org.springframework.web.util.UriTemplateHandler
import spock.lang.Specification

class PhotoResolverUriBuilderSpec extends Specification {

    def templateHandler = Mock(UriTemplateHandler)

    def setup() {
        templateHandler.expand(_) >> { args -> new URI(args[0]) }
    }

    def "should build uri properly"() {
        given:
        def builder = PhotoResolverUriBuilder
                .of(new Photo(800, 600, "test_reference"))

        when:
        def uri = builder.build(templateHandler)

        then:
        uri.toString() == "/place/photo?photo_reference=test_reference&maxwidth=800"
    }
}
