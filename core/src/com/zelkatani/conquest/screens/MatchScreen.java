package com.zelkatani.conquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zelkatani.conquest.Match;
import com.zelkatani.conquest.ui.Grabber;
import com.zelkatani.conquest.ui.Hud;

public class MatchScreen implements Screen {
    private Match match;
    private Hud hud;
    private Grabber grabber;
    private ShapeRenderer renderer;
    private SpriteBatch batch;

    public MatchScreen(Match match) {
        this.match = match;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        grabber = new Grabber(match.getTiles(), match.getStage().getCamera());

        batch = new SpriteBatch();

        hud = new Hud();
        Gdx.input.setInputProcessor(new InputMultiplexer(match.getStage(), grabber));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.22f, 0.22f, 0.22f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        match.draw();

        grabber.grab();
        grabber.draw(renderer);

        hud.draw(renderer);
    }

    @Override
    public void resize(int width, int height) {
        hud.getStage().getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }
}
