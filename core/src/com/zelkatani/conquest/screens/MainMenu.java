package com.zelkatani.conquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.zelkatani.conquest.Assets;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.multiplayer.Client;
import com.zelkatani.conquest.ui.PlayGroup;

public class MainMenu implements Screen {
    private Player player;
    private Client client;

    private Stage stage;

    @Override
    public void show() {
        player = new Player(Assets.random());
        client = new Client(player);

        stage = new Stage();

        Image logo = new Image(Assets.LOGO);
        logo.setPosition((Gdx.graphics.getWidth() - logo.getWidth()) / 2, Gdx.graphics.getHeight() * 0.85f);
        Image shadow = dropShadow(logo);

        stage.addActor(logo);
        stage.addActor(shadow);
        stage.addActor(new PlayGroup(client));

        stage.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(event.getTarget());
                if (!(event.getTarget() instanceof TextField)) stage.setKeyboardFocus(null);
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private Image dropShadow(Image image) {
        Image drop = new Image(Assets.LOGO);
        drop.setPosition(image.getX() + 2.5f, image.getY() - 2.5f);
        drop.setColor(0, 0, 0, 0.15f);

        return drop;
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0.32f, 0.42f, 0.52f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
