package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.zelkatani.conquest.Assets.ConquestLabel;

public class Hud {
    private Stage stage;
    private static ConquestLabel timer = new ConquestLabel("00:00", 0, 12.5f);
    private Color background;

    public Hud() {
        stage = new Stage();

        background = Color.valueOf("#626262");
        timer.setAlignment(Align.bottom, Align.center);

        stage.addActor(timer);
    }

    public void draw(ShapeRenderer renderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setProjectionMatrix(stage.getCamera().combined);
        renderer.setColor(background);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect((Gdx.graphics.getWidth() - 125) / 2, 10, 125, 30);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public static ConquestLabel getTimer() {
        return timer;
    }
}
