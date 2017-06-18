package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class Camera extends OrthographicCamera {
    public Camera() {
        super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update() {
        handle();
        update(true);
    }

    private void handle() {
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

        position.x = MathUtils.clamp(position.x, 0, Gdx.graphics.getWidth());
        position.y = MathUtils.clamp(position.y, 0, Gdx.graphics.getHeight());
    }
}
