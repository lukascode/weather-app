package com.lukascode.location.integration.timezone

import com.lukascode.location.integration.placedetails.Coordinates
import com.lukascode.location.integration.placedetails.timezone.TimezoneQuery
import com.lukascode.location.integration.placedetails.timezone.TimezoneUriBuilder
import org.springframework.web.util.UriTemplateHandler
import spock.lang.Specification

import java.time.Instant

class TimezoneUriBuilderSpec extends Specification {

    Instant now = Instant.ofEpochSecond(1634513158)

    def templateHandler = Mock(UriTemplateHandler)

    def setup() {
        templateHandler.expand(_) >> { args -> new URI(args[0]) }
    }

    def "should build uri properly"() {
        given:
        def builder = TimezoneUriBuilder
                .of(new TimezoneQuery(new Coordinates(25.7616798, -80.1917902), "en", now))

        when:
        def uri = builder.build(templateHandler)

        then:
        uri.toString() == "/timezone/json?location=25.7616798,-80.1917902&timestamp=1634513158&language=en"
    }
}
