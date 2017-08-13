package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;

public class Owner {
    private Color color;
    private int id;

    public static final Owner None = new Owner(Color.WHITE);
    static {
        None.id = 0;
    }

    public Owner(Color color) {
        this.color = color;
        id = 1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
