package com.example.pokemon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.service.PokemonService;
import com.example.pokemon.service.helper.ResponseHelper;

@RestController
@RequestMapping(value = "/api/v1/pokemon", produces = "application/json; charset=UTF-8")
public class PokemonController {
    @Autowired
    private PokemonService pokemonService;
    @Autowired
    private ResponseHelper response;

    @GetMapping()
    public Map<String, Object> getList(@RequestParam(defaultValue = "0") int offset) {
        List<Pokemon> data = pokemonService.getList(offset);
        return response.success("get list", data);
    }

    @PostMapping
    public Map<String, Object> add(@RequestBody Pokemon input) {
        Pokemon data = pokemonService.add(input);
        return response.success("add", data);
    }

    @PutMapping
    public Map<String, Object> update(@RequestBody Pokemon input) {
        Pokemon data = pokemonService.update(input);
        return response.success("update", data);
    }

    @GetMapping("{name}")
    public Map<String, Object> getPokemonByName(@PathVariable String name) {
        Pokemon data = pokemonService.get(name);
        return response.success("getPokemonByName", data);
    }

    @DeleteMapping("{name}")
    public Map<String, Object> delete(@PathVariable String name) {
        pokemonService.delete(name);
        return response.success("get list");
    }

}
