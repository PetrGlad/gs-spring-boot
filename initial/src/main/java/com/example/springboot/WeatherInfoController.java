package com.example.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class WeatherInfoController {

	final String SOURCE_URL_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid=3fc9cb54394cc7ad0010daa12eb9e286";

	@GetMapping("/")
	public double index(@RequestParam(name="city") String cityName) throws IOException {
		final var rest = new RestTemplate();
		final var queryParams = new HashMap<String, Object>();
		queryParams.put("cityName", cityName);
		final var response = rest.getForEntity(SOURCE_URL_TEMPLATE, String.class, queryParams);
		final var jsonParser = new ObjectMapper();
		final double tempStr = jsonParser.readTree(response.getBody())
						.get("main")
						.get("temp")
						.asDouble();

		return tempStr;
	}
}
