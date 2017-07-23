package com.zelkatani.conquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zelkatani.conquest.Match;
import com.zelkatani.conquest.pathfinding.Pathway;
import com.zelkatani.conquest.ui.Grabber;
import com.zelkatani.conquest.ui.Hud;
import com.zelkatani.conquest.ui.Manager;

public class MatchScreen implements Screen {
    private Match match;
    private Hud hud;

    private Manager manager;
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
        batch = new SpriteBatch();

        hud = new Hud();
        Pathway pathway = new Pathway(match.getTiles(), match.getPlayer());
        manager = new Manager(pathway);
        grabber = new Grabber(match.getTiles(), match.getCam(), manager, pathway, match.getPlayer());

        Gdx.input.setInputProcessor(new InputMultiplexer(manager.getStage(), grabber, match.getStage()));
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.32f, 0.42f, 0.52f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        match.draw();

        grabber.grab();
        grabber.draw(renderer);

        hud.draw(renderer);
        manager.draw();
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
