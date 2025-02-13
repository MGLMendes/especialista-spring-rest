package com.algaworks.algafood.client.api;

import com.algaworks.algafood.client.model.RestauranteResumoModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantes";


    private RestTemplate restTemplate;
    private String url;


    public List<RestauranteResumoModel> listar() {
        URI uri = URI.create(url + RESOURCE_PATH);

        RestauranteResumoModel[] restaurantes = restTemplate.getForObject(uri, RestauranteResumoModel[].class);

        assert restaurantes != null;
        return Arrays.asList(restaurantes);
    }
}
