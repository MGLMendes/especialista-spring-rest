package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteService {

    Restaurante salvar(Restaurante restaurante);

    List<Restaurante> listar();

    Restaurante buscar(Long restauranteId);

    Restaurante atualizar(Long restauranteId, Restaurante restaurante);
}
