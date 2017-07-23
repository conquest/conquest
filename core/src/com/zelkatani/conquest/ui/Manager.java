package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.zelkatani.conquest.Assets;
import com.zelkatani.conquest.pathfinding.Pathway;

public class Manager {
    private Stage stage;
    private static PartialSendGroup sendGroup = new PartialSendGroup();
    private static TextButton maxSendButton = new TextButton("Send All", Assets.SKIN);

    private Pathway pathway;

    public Manager(Pathway pathway) {
        setVisible(false);
        stage = new Stage();

        maxSendButton.setSize(200,100);
        maxSendButton.setPosition(Gdx.graphics.getWidth() * 2 / 3 - 100, Gdx.graphics.getHeight() / 2 - 50);
        maxSendButton.getLabel().setAlignment(Align.center);

        stage.addActor(maxSendButton);
        stage.addActor(sendGroup);

        stage.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (!Manager.isVisible()) return false;
                if (!(event.getTarget() instanceof NumberField)) stage.setKeyboardFocus(null);

                if (event.getTarget() instanceof Group && event.getTarget() != sendGroup) {
                    setVisible(false);
                    pathway.deselect();
                    return false;
                }
                return true;
            }
        });

        this.pathway = pathway;

        sendGroup.getSendButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                send();
            }
        });
        maxSendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                sendMax();
            }
        });

        stage.addListener(new Macro());
    }

    private void send() {
        Manager.this.pathway.deselect();
        Manager.this.pathway.send(sendGroup.getValue());

        setVisible(false);
    }

    private void sendMax() {
        Manager.this.pathway.deselect();
        Manager.this.pathway.sendMax();

        setVisible(false);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public static boolean isVisible() {
        return sendGroup.isVisible() && maxSendButton.isVisible();
    }

    public void setVisible(boolean visible) {
        sendGroup.setVisible(visible);
        maxSendButton.setVisible(visible);
    }

    private class Macro extends InputListener {
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (!Manager.isVisible()) return false;
            if (Input.Keys.Q == keycode) {
                send();
            }
            if (Input.Keys.E == keycode) {
                sendMax();
            }

            return false;
        }
    }
}
