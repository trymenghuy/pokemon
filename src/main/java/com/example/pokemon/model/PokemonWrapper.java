package com.example.pokemon.model;

import java.util.List;

public class PokemonWrapper {
    private List<Pokemon> pokemon;

    public PokemonWrapper(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public PokemonWrapper() {
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
