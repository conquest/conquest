package com.zelkatani.conquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.zelkatani.conquest.Match;

public class MatchScreen implements Screen {
    private Match match;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    public MatchScreen(Match match) {
        this.match = match;

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        batch = new SpriteBatch();
        font = new BitmapFont();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.22f, 0.22f, 0.22f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

        match.draw(renderer);
        match.drawText(batch, font, layout);
    }

    @Override
    public void resize(int width, int height) {

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
        font.dispose();
    }
}
