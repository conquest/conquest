package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class Tile {
    private Color color;
    private int width, height;
    private int x, y;
    private int troops;

    private Array<City> cities;

    public Tile(int width, int height, int x, int y, Color color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        this.color = color;

        troops = 10;
        cities = new Array<>();
    }

    public void update() {
        int base = 5;
        for (City c : cities) {
            base += c.isMajor() ? 10 : 7;
        }

        troops += base;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTroops() {
        return troops;
    }

    public Array<City> getCities() {
        return cities;
    }

    public void addCity(City city) {
        // place city relative to rectangle origin, but make sure city fits in boundary
        if (city.getX() < width && city.getY() < height) {
            cities.add(city);
        }
    }
}
