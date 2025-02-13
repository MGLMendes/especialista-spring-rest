package com.algaworks.algafood.client.api;

import com.algaworks.algafood.client.exception.ClientApiException;
import com.algaworks.algafood.client.model.RestauranteResumoModel;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantess";


    private RestTemplate restTemplate;
    private String url;


    public List<RestauranteResumoModel> listar() {
        try {
            URI uri = URI.create(url + RESOURCE_PATH);

            RestauranteResumoModel[] restaurantes = restTemplate.getForObject(uri, RestauranteResumoModel[].class);

            assert restaurantes != null;
            return Arrays.asList(restaurantes);
        } catch (RestClientResponseException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
}
