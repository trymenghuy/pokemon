package com.example.pokemon.service;

import java.util.List;

import com.example.pokemon.model.Pokemon;

public interface PokemonService {
    List<Pokemon> getList(int offset);

    String craw(int page) throws Exception;

    Pokemon get(String name);

    Pokemon update(Pokemon pokemon);

    void delete(String name);

    Pokemon add(Pokemon pokemon);

}
