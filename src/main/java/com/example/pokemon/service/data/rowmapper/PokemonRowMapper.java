package com.example.pokemon.service.data.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.pokemon.model.Pokemon;

public class PokemonRowMapper implements RowMapper<Pokemon> {

    @Override
    public Pokemon mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Pokemon(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getDouble("price"),
                rs.getString("type"),
                rs.getString("ability"));
    }

}
