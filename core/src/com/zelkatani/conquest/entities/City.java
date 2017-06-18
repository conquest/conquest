package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;

public class City {
    public static final int SIZE = 10;

    private int x, y;
    private boolean major;

    public City(int x, int y, boolean major) {
        this.x = x;
        this.y = y;
        this.major = major;
    }

    public Color getColor() {
        return major ? Color.GOLD : Color.GRAY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMajor() {
        return major;
    }
}
