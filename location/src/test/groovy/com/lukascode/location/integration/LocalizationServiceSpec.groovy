package com.lukascode.location.integration

import com.lukascode.location.infrastructure.configuration.LocalizationAppConfiguration
import com.lukascode.location.integration.autocomplete.AutocompleteQuery
import com.lukascode.location.integration.autocomplete.Predictions
import com.lukascode.location.integration.placedetails.PlaceDetailsQuery
import com.lukascode.location.integration.placedetails.timezone.Timezone
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriTemplateHandler
import spock.lang.Specification
import spock.lang.Subject

class LocalizationServiceSpec extends Specification {

    @Subject
    LocalizationService localizationService

    def setup() {
        def templateHandler = Mock(UriTemplateHandler) {
            expand(_) >> { args -> new URI(args[0]) }
        }
        def googleClientMock = Mock(RestTemplate)

        googleClientMock.getUriTemplateHandler() >> templateHandler

        googleClientMock.getForEntity(
                { it.toString().contains("/place/autocomplete/json")} as URI,
                String.class
        ) >> prepareAutocompleteResponse()

        googleClientMock.getForEntity(
                { it.toString().contains("/place/details/json")} as URI,
                String.class
        ) >> preparePlaceDetailsResponse()

        googleClientMock.getForEntity(
                { it.toString().contains("/timezone/json")} as URI,
                Timezone.class
        ) >> prepareTimezone()

        googleClientMock.getForEntity(
                { it.toString().contains("/place/photo")} as URI,
                Void.class
        ) >> prepareResolvedPhotoUri()

        localizationService = new LocalizationAppConfiguration()
                                    .localizationService(googleClientMock)
    }

    def "should find predictions properly"() {
        given:
        def query = new AutocompleteQuery("Bialystok", "pl")

        when:
        Predictions predictions = localizationService.autocomplete(query)

        then:
        with(predictions) {
            status == "OK"
            places.size() == 1
            with(places.get(0)) {
                placeId == "ChIJHZdBjwT8H0cRLHvwi8x9MXI"
                description == "Białystok, Polska"
            }
        }
    }

    def "should get place details properly"() {
        given:
        def query = new PlaceDetailsQuery("ChIJHZdBjwT8H0cRLHvwi8x9MXI", "pl")

        when:
        def placeDetails = localizationService.getPlaceDetails(query).orElse(null)

        then:
        placeDetails != null
        with(placeDetails) {
            status == "OK"
            placeId == "ChIJHZdBjwT8H0cRLHvwi8x9MXI"
            formattedAddress == "Białystok, Polska"
            with(coordinates) {
                lat == 53.13248859999999
                lng == 23.1688403
            }
            with(timezone) {
                timeZoneId == "Europe/Warsaw"
                timeZoneName == "Central European Standard Time"
            }
            photos.size() == 1
            with(photos.get(0)) {
                reference == "http://real-photo-uri"
            }
        }
    }

    private ResponseEntity<Void> prepareResolvedPhotoUri() {
        HttpHeaders headers = new HttpHeaders()
        headers.setLocation(URI.create("http://real-photo-uri"))
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    private ResponseEntity<TimeZone> prepareTimezone() {
        return ResponseEntity.ok(new Timezone("Europe/Warsaw", "Central European Standard Time", "OK"));
    }

    private ResponseEntity<String> prepareAutocompleteResponse() {
        return ResponseEntity.ok("""
        {
          "status": "OK",
          "predictions": [
            {
              "place_id": "ChIJHZdBjwT8H0cRLHvwi8x9MXI",
              "description": "Białystok, Polska"
            }
          ]
        }
        """)
    }

    private ResponseEntity<String> preparePlaceDetailsResponse() {
        return ResponseEntity.ok("""
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
                 }
              ],
              "place_id" : "ChIJHZdBjwT8H0cRLHvwi8x9MXI"
           },
           "status" : "OK"
        }
        """)
    }
}
