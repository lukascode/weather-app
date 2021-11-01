package com.lukascode.location.integration.autocomplete

import com.lukascode.location.infrastructure.configuration.LocalizationAppConfiguration
import spock.lang.Specification

import java.util.function.Function
import java.util.stream.Collectors

class AutoCompleteResponseParserSpec extends Specification {

    def setupSpec() {
        new LocalizationAppConfiguration() // we need this class to run our static code block
    }

    def "should parse response properly"() {
        given:
        def parser = AutoCompleteResponseParser.of(TEST_RESPONSE)

        when:
        def predictions = parser.parse()

        then:
        with(predictions) {
            status == "OK"
            places.size() == 2
            with(mapToSpecificField(places, Place::getPlaceId)) {
                containsAll("place1", "place2")
            }
            with(mapToSpecificField(places, Place::getDescription)) {
                containsAll("Warsaw, Poland", "New York, USA")
            }
        }
    }

    private List<String> mapToSpecificField(List<Place> places, Function<Place, String> mapper) {
        return places.stream().map(mapper).collect(Collectors.toList())
    }

    static String TEST_RESPONSE = """
        {
          "status": "OK",
          "predictions": [
            {
              "place_id": "place1",
              "description": "Warsaw, Poland"
            },
            {
              "place_id": "place2",
              "description": "New York, USA"
            }
          ]
        }
    """
}
