package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.ui.Manager;

public class ConquestCamera extends OrthographicCamera {
    private float top, bottom, left, right;

    public ConquestCamera(Array<Tile> tiles) {
        super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bottom = left = Integer.MAX_VALUE;
        top = right = Integer.MIN_VALUE;
        for (Tile tile : tiles) {
            if (tile.getX() < left) {
                left = tile.getX();
            }
            if (tile.getRight() > right) {
                right = tile.getRight();
            }
            if (tile.getY() < bottom) {
                bottom = tile.getY();
            }
            if (tile.getTop() > top) {
                top = tile.getTop();
            }
        }
    }

    @Override
    public void update() {
        if (!Manager.isVisible()) {
            handle();
        }

        update(true);
    }

    public void handle() {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translate(0, 10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translate(-10, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translate(0, -10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translate(10, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            zoom -= 0.05;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            zoom += 0.05;
        }

        zoom = MathUtils.clamp(zoom, 0.5f, 2);
        position.x = MathUtils.clamp(position.x, left, right);
        position.y = MathUtils.clamp(position.y, bottom, top);
    }
}
