package com.example.pokemon.model;

public class Pokemon {
    private int id;
    private String name;
    private String imageUrl;
    private double price;
    private String type;
    private String ability;

    public Pokemon(String name, String imageUrl, double price, String type, String ability) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
        this.ability = ability;
    }

    public Pokemon(String name) {
        this.name = name;
    }

    public Pokemon(String name, String imageUrl, double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Pokemon(int id, String name, String imageUrl, double price, String type, String ability) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
        this.ability = ability;
    }

    public Pokemon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }
}
