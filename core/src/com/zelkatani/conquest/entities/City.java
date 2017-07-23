package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zelkatani.conquest.Assets;

public class City extends Actor {
    private TextureRegion texture;
    private boolean major;

    public City(float x, float y, boolean major) {
        setX(x);
        setY(y);
        this.major = major;

        texture = Assets.CITY;
    }

    @Override
    public Color getColor() {
        return major ? Color.GOLDENROD : Color.LIGHT_GRAY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX() - 10, getY() - 10);
    }

    public boolean isMajor() {
        return major;
    }
}
