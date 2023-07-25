package com.example.pokemon.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokemon.service.PokemonService;
import com.example.pokemon.service.helper.ResponseHelper;

@RestController
@RequestMapping(value = "/api/v1/craw", produces = "application/json; charset=UTF-8")
public class CrawController {
    @Autowired
    private PokemonService pokemonService;
    @Autowired
    private ResponseHelper response;

    @GetMapping()
    public Map<String, Object> crawPokemon(@RequestParam(defaultValue = "1") int page) {
        try {
            String data = pokemonService.craw(page);
            return response.success("get list", data);
        } catch (Exception e) {
            return response.fail(e.getMessage());
        }

    }

}
