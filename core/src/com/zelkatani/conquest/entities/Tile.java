package com.zelkatani.conquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.zelkatani.conquest.Assets;

public class Tile extends Actor implements Disposable {
    private int troops;
    private Label label;

    private Texture texture;

    private City city;

    public Tile(int width, int height, int x, int y, Color color) {
        setWidth(width);
        setHeight(height);
        setX(x);
        setY(y);
        setColor(color);

        texture = new Assets.TileTexture(width, height).getTexture();

        troops = 1;

        label = new Assets.ConquestLabel("" + troops, x, y, width, height);
        label.setAlignment(Align.center, Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

        if (city != null) {
            city.draw(batch, parentAlpha);
        }

        label.draw(batch, parentAlpha);
    }

    public void update() {
        troops += 2;

        if (city != null) {
            troops += city.isMajor() ? 5 : 2;
        }

        Gdx.app.log("troops", label.getText().toString());
        label.setText("" + troops);
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public void dispose() {
        texture.dispose();
        if (city != null) {
            city.dispose();
        }
    }
}
