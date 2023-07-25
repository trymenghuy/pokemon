package com.example.pokemon.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.model.PokemonWrapper;
import com.example.pokemon.service.data.rowmapper.PokemonRowMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OkHttpClient httpClient;

    private String callOpenAI(String prompt) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt
                        + "\"}], \"temperature\": 0.7}",
                mediaType);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer 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")
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body().string());
            // Navigate through the JSON to get the content
            String content = root.path("choices").get(0).path("message").path("content").asText();
            return content;
        }
    }

    @Override
    public List<Pokemon> getList(int offset) {
        final String sql = "SELECT * FROM pokemons LIMIT ?,10";
        Object[] p = { offset };
        List<Pokemon> result = jdbcTemplate.query(sql, new PokemonRowMapper(), p);
        return result;
    }

    @Override
    public Pokemon get(String name) {
        final String sql = "SELECT * FROM pokemons WHERE name=?";
        Object[] p = { name };
        List<Pokemon> result = jdbcTemplate.query(sql, new PokemonRowMapper(), p);
        if (result.size() == 0)
            return null;
        return result.get(0);
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        final String sql = "UPDATE pokemons SET name=?, image_url=?, price=?, type=?, ability=? WHERE id=?";
        Object[] params = { pokemon.getName(), pokemon.getImageUrl(), pokemon.getPrice(), pokemon.getType(),
                pokemon.getAbility(), pokemon.getId() };

        int updatedRows = jdbcTemplate.update(sql, params);

        if (updatedRows > 0) {
            System.out.println("Pokemon updated successfully!");
        } else {
            System.out.println("Failed to update Pokemon.");
        }
        return pokemon;
    }

    @Override
    public void delete(String name) {
        final String sql = "DELETE FROM pokemons WHERE name=? LIMIT 1";
        Object[] params = { name };
        int deletedRows = jdbcTemplate.update(sql, params);
        if (deletedRows > 0) {
            System.out.println("Pokemon deleted successfully!");
        } else {
            System.out.println("Failed to delete Pokemon.");
        }
    }

    @Override
    public Pokemon add(Pokemon pokemon) {

        final String sql = "INSERT INTO pokemons (name, image_url, price, type, ability) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, pokemon.getName());
            ps.setString(2, pokemon.getImageUrl());
            ps.setDouble(3, pokemon.getPrice());
            ps.setString(4, pokemon.getType());
            ps.setString(5, pokemon.getAbility());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            pokemon.setId(keyHolder.getKey().intValue());
        }
        return pokemon;
    }

    @Override
    public String craw(int page) throws Exception {
        Map<String, Pokemon> map = new HashMap<>();

        try {
            String url = "https://scrapeme.live/shop/page/" + page;
            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select("ul.products.columns-4 li");
            for (Element product : products) {
                // String id = product.attr("class").split(" ")[0].split("-")[1];
                String name = product.select("h2.woocommerce-loop-product__title").text();
                String imageUrl = product.select("img").attr("src");
                String priceText = product.select("span.woocommerce-Price-amount.amount").text();
                double price = Double.parseDouble(priceText.replace("£", ""));
                Pokemon pokemon = new Pokemon(name, imageUrl, price);
                map.put(name, pokemon);
            }
        } catch (IOException e) {
            throw new Exception("Jsoup error" + e.getMessage());
        }
        String prompt = "Base on name, help accordingly generate type and ability of  this pokemon"
                + map.keySet() +
                ". Combine all result in a list json format without any text beside json";
        try {
            String data = callOpenAI(prompt);
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("data-----" + data);
            PokemonWrapper pokemonWrapper = objectMapper.readValue(data, PokemonWrapper.class);
            for (Pokemon pokemon : pokemonWrapper.getPokemon()) {
                String name = pokemon.getName();
                Pokemon top = map.get(name);
                add(new Pokemon(name, top.getImageUrl(), top.getPrice(), pokemon.getType(), pokemon.getAbility()));
            }
            return data;
        } catch (IOException e) {
            throw new Exception("open ai error" + e.getMessage());
        }

    }

}
