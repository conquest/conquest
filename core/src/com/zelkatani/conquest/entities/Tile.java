package com.zelkatani.conquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    private Rectangle rectangle;

    private boolean hovered = false,
            selected = false;

    public Tile(int x, int y, int width, int height, Color color) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setColor(color);

        rectangle = new Rectangle(x, y, width, height);
        texture = new Assets.TileTexture(width, height).getTexture();
        troops = 1;

        label = new Assets.ConquestLabel("" + troops, x, y, width, height);
        label.setAlignment(Align.center, Align.center);
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

        updateLabel();
    }

    public void updateLabel() {
        label.setText("" + troops);
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getCenter() {
        return rectangle.getCenter(new Vector2());
    }

    @Override
    public void dispose() {
        texture.dispose();
        if (city != null) {
            city.dispose();
        }
    }

    public int getTroops() {
        return troops;
    }

    public void addTroops(int value) {
        troops += value;
    }

    public void transferTroops(Tile to, int value) {
        troops -= value;
        to.addTroops(value);
    }
}
