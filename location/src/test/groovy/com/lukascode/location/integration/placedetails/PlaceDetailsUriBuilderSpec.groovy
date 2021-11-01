package com.lukascode.location.integration.placedetails

import org.springframework.web.util.UriTemplateHandler
import spock.lang.Specification

class PlaceDetailsUriBuilderSpec extends Specification {

    def templateHandler = Mock(UriTemplateHandler)

    def setup() {
        templateHandler.expand(_) >> { args -> new URI(args[0]) }
    }

    def "should build uri properly"() {
        given:
        def placeId = "ChIJEcHIDqKw2YgRZU-t3XHylv8"
        def builder = PlaceDetailsUriBuilder.of(new PlaceDetailsQuery(placeId, "en"))

        when:
        def uri = builder.build(templateHandler)

        then:
        uri.toString() == "/place/details/json?place_id=ChIJEcHIDqKw2YgRZU-t3XHylv8&language=en"
    }
}
