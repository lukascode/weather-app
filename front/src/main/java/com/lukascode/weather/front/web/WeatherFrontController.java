package com.lukascode.weather.front.web;

import com.lukascode.weather.front.domain.WeatherFrontService;
import com.lukascode.weather.front.infrastructure.configuration.AppVersion;
import com.lukascode.weather.front.util.CountryFlagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class WeatherFrontController {

    @Autowired
    private AppVersion appVersion;

    @Autowired
    private WeatherFrontService weatherService;

    @GetMapping("/")
    public String index(Model model, @RequestParam Optional<String> placeId) {
        model.addAttribute("appVersion", appVersion.getAppVersion());
        placeId.flatMap(id -> weatherService.getWeather(id))
                .ifPresent(weatherAndLocation -> {
                    model.addAttribute("weatherAndLocation", weatherAndLocation);
                    model.addAttribute("countryFlag",
                            CountryFlagUtil.getCountryFlag(weatherAndLocation.placeWeather.country));
                });
        return "index";
    }

    @GetMapping("/locations")
    public @ResponseBody List<Location> autocomplete(@RequestParam("q") String query) {
        return weatherService.getLocations(query);
    }
}
