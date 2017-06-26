package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class Hud {
    private Stage stage;
    private Label timer;

    private float time;

    public Hud(BitmapFont font) {
        stage = new Stage();
        timer = new Label(null, new Label.LabelStyle(font, Color.WHITE));

        timer.setAlignment(Align.bottom, Align.center);
        timer.setBounds(0, 10, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(timer);

        time = 0;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                time++;
            }
        }, 1, 1);
    }

    public void draw(ShapeRenderer renderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setProjectionMatrix(stage.getCamera().combined);
        renderer.setColor(new Color(98 / 255f, 98 / 255f, 98 / 255f, 0.5f));

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect((Gdx.graphics.getWidth() - 125) / 2, 10, 125, 30);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();

        update();
    }

    private void update() {
        timer.setText("" + (int) time / 60 + ":" + (time % 60 < 10 ? "0" : "") + (int) time % 60);
    }

    public Stage getStage() {
        return stage;
    }
}
