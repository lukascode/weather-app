package com.lukascode.location.api;

import com.lukascode.location.integration.LocalizationService;
import com.lukascode.location.integration.autocomplete.AutocompleteQuery;
import com.lukascode.location.integration.autocomplete.Predictions;
import com.lukascode.location.integration.placedetails.PlaceDetails;
import com.lukascode.location.integration.placedetails.PlaceDetailsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/localization")
public class LocalizationApi {

    private final LocalizationService localizationService;

    public LocalizationApi(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @GetMapping("/autocomplete")
    public Predictions searchLocation(@RequestParam String input, @RequestParam String lang) {
        return localizationService.autocomplete(new AutocompleteQuery(input, lang));
    }

    @GetMapping("/places/{placeId}")
    public ResponseEntity<PlaceDetails> getPlaceDetails(@PathVariable String placeId, @RequestParam String lang) {
        return localizationService
                .getPlaceDetails(new PlaceDetailsQuery(placeId, lang))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
