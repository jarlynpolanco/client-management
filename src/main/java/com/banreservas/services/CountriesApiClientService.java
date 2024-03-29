package com.banreservas.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.spi.HttpResponseCodes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named
@ApplicationScoped
public class CountriesApiClientService {
    @ConfigProperty(name = "rest.countries.api.url")
    String restCountriesApiUrl;

    public String getDemonymByCountryCode(final String countryCode) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(restCountriesApiUrl + countryCode))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != HttpResponseCodes.SC_OK) {
            throw new NotFoundException("El codigo solicitado no fue encontrado.");
        }

        String jsonResponse = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        return jsonNode
                .get(0)
                .path("translations")
                .path("spa")
                .path("common")
                .asText();
    }
}
