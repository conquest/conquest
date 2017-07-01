package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.zelkatani.conquest.Assets;

public class Tile extends Actor implements Disposable {
    private int troops;
    private Label label;
    private Texture texture;
    private City city;

    private boolean hovered = false,
            selected = false;

    public Tile(int width, int height, int x, int y, Color color) {
        setWidth(width);
        setHeight(height);
        setX(x);
        setY(y);
        setColor(color);

        texture = new Assets.TileTexture(width, height).getTexture();

        troops = 1;

        addListener(new TileListener());

        label = new Assets.ConquestLabel("" + troops, x, y, width, height);
        label.setAlignment(Align.center, Align.center);
    }

    private class TileListener extends ClickListener {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            super.enter(event, x, y, pointer, fromActor);
            hovered = true;
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            if (pointer == 0) return;

            super.exit(event, x, y, pointer, toActor);
            hovered = false;
        }

        @Override
        public void clicked (InputEvent event, float x, float y) {
            selected = !selected;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        if (selected) {
            color = Assets.select(color);
        } else if (hovered) {
            color = Assets.highlight(color);
        }

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
