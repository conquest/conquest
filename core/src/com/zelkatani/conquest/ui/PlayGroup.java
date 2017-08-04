package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.zelkatani.conquest.Assets;
import com.zelkatani.conquest.Assets.ConquestLabel;
import com.zelkatani.conquest.multiplayer.Client;

import java.io.IOException;

public class PlayGroup extends Group {
    private TextButton play;
    private TextField host;
    private Label hostLabel;
    private Label server;

    public PlayGroup(Client client) {
        play = new TextButton("Play", Assets.SKIN);
        play.setSize(200, 40);
        play.setPosition((Gdx.graphics.getWidth() - play.getWidth()) / 2, Gdx.graphics.getHeight() / 8);

        host = new TextField(null, Assets.SKIN);
        host.setSize(200, 40);
        host.setPosition(play.getX(), play.getY() + host.getHeight() * 1.1f);

        hostLabel = new ConquestLabel("Host:", host.getX() - 75, host.getY() - 5, 50, 50);

        server = new ConquestLabel("Failed to connect.", play.getX(), host.getY() + 75, 100, 1);
        server.setVisible(false);

        play.addCaptureListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    client.connect(host.getText());
                } catch (IOException io) {
                    server.setVisible(true);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            server.setVisible(false);
                        }
                    }, 1);
                }
            }
        });

        addActor(play);
        addActor(host);
        addActor(hostLabel);
        addActor(server);
    }
}
