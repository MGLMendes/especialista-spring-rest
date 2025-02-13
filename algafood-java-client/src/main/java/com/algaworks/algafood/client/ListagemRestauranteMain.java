package com.algaworks.algafood.client;

import com.algaworks.algafood.client.api.RestauranteClient;
import com.algaworks.algafood.client.exception.ClientApiException;
import org.springframework.web.client.RestTemplate;

public class ListagemRestauranteMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RestauranteClient restauranteClient = new RestauranteClient(restTemplate, "http://localhost:8080");

            restauranteClient.listar().stream()
                    .forEach(
                            restauranteResumoModel -> {
                                System.out.println(restauranteResumoModel.toString());
                            }
                    );
        } catch (ClientApiException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem());
                System.out.println(e.getProblem().getUserMessage());
            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
            }
        }
    }
}
