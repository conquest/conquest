package com.zelkatani.conquest.entities;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.zelkatani.conquest.Assets;

public class Troop extends Actor implements Disposable {
    private int troops;
    private GraphPath<Tile> path;
    private Tile current;

    private Texture texture;
    private Label label;

    public Troop(int troops, GraphPath<Tile> path) {
        this.troops = troops;
        this.path = new DefaultGraphPath<>();

        for (Tile tile : path) {
            this.path.add(tile);
        }

        texture = Assets.TROOP;
        setSize(texture.getWidth(), texture.getHeight());

        current = this.path.get(0);
        setPosition(current.getX(Align.center) - getWidth() / 2, current.getY(Align.center) - getHeight() / 2);

        label = new Assets.ConquestLabel("" + this.troops, getX(), getY(), getWidth(), getHeight());
        label.setAlignment(Align.center, Align.center);

        setOrigin(Align.center);
        setRotation(angleToNext());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);

        label.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    private float angleToNext() {
        Tile next = path.get(1);
        float dX = current.getX(Align.center) - next.getX(Align.center);
        float dY = current.getY(Align.center) - next.getY(Align.center);
        return MathUtils.atan2(dY, dX) * MathUtils.radDeg;
    }
}
