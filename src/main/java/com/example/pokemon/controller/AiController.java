package com.example.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokemon.input.AiInput;
import com.example.pokemon.service.AiService;

@RequestMapping(value = "/open-ai/")
@RestController
public class AiController {
    @Autowired
    private AiService aiService;

    @PostMapping("/generate-image")
    public String generateImage(@RequestBody AiInput aiRequestDTO) {
        return aiService.generateImage(aiRequestDTO);
    }

    @PostMapping("/generate-text")
    public List<String> generateText(@RequestBody AiInput aiRequestDTO) {
        return aiService.generateText(aiRequestDTO);
    }
}