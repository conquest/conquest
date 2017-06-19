package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;

public class Tile {
    private Color color;
    private int width, height;
    private int x, y;
    private int troops;

    private City city;

    public Tile(int width, int height, int x, int y, Color color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        this.color = color;

        troops = 1;
        city = null;
    }

    public void update() {
        troops += 2;

        if (city != null) {
            troops += city.isMajor() ? 5 : 2;
        }
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        // place city relative to rectangle origin, but make sure city fits in boundary
        if (city.getX() < width && city.getY() < height) {
            this.city = city;
        }
    }
}
