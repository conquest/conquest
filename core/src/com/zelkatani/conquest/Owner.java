package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;

public class Owner {
    private Color color;
    private static int total = 0;
    private int id;

    public static Owner None = new Owner(Color.WHITE);

    public Owner(Color color) {
        this.color = color;
        id = total++;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
