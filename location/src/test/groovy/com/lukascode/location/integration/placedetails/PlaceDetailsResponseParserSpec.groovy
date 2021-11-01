package com.lukascode.location.integration.placedetails

import com.lukascode.location.infrastructure.configuration.LocalizationAppConfiguration
import spock.lang.Specification

class PlaceDetailsResponseParserSpec extends Specification {

    def setupSpec() {
        new LocalizationAppConfiguration()
    }

    def "should parse response properly"() {
        given:
        def parser = PlaceDetailsResponseParser.of(TEST_RESPONSE)

        when:
        def placeDetails = parser.parse()

        then:
        with(placeDetails) {
            status == "OK"
            placeId == "ChIJHZdBjwT8H0cRLHvwi8x9MXI"
            formattedAddress == "Białystok, Polska"
            photos.size() == 2
            with(coordinates) {
                lat == 53.13248859999999
                lng == 23.1688403
            }
        }
    }

    static String TEST_RESPONSE = """
        {
           "result" : {
              "formatted_address" : "Białystok, Polska",
              "geometry" : {
                 "location" : {
                    "lat" : 53.13248859999999,
                    "lng" : 23.1688403
                 },
                 "viewport" : {
                    "northeast" : {
                       "lat" : 53.18855134519848,
                       "lng" : 23.24717120431783
                    },
                    "southwest" : {
                       "lat" : 53.06669923477883,
                       "lng" : 23.06496045834089
                    }
                 }
              },
              "name" : "Białystok",
              "photos" : [
                 {
                    "height" : 3072,
                    "width" : 4096,
                    "photo_reference" : "http://photo1"
                 },
                 {
                    "height" : 3000,
                    "width" : 4000,
                    "photo_reference" : "http://photo2"
                 }
              ],
              "place_id" : "ChIJHZdBjwT8H0cRLHvwi8x9MXI"
           },
           "status" : "OK"
        }
    """
}
