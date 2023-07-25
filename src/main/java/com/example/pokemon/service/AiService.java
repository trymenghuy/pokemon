package com.example.pokemon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pokemon.input.AiInput;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import java.util.stream.Collectors;

@Service
public class AiService {
    private static String token = "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";

    public String generateImage(final AiInput aiRequestDTO) {
        try {

            OpenAiService openAiService = new OpenAiService(token);
            CreateImageRequest imageRequest = CreateImageRequest.builder()
                    .prompt(aiRequestDTO.getText())
                    .build();

            return openAiService.createImage(imageRequest).getData().get(0).getUrl();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public List<String> generateText(final AiInput aiRequestDTO) {
        try {

            OpenAiService openAiService = new OpenAiService(token);
            CompletionRequest completionRequest = CompletionRequest.builder()
                    .model("text-davinci-003")
                    .prompt(aiRequestDTO.getText())
                    .build();

            return openAiService
                    .createCompletion(completionRequest)
                    .getChoices()
                    .stream()
                    .map(CompletionChoice::getText)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}