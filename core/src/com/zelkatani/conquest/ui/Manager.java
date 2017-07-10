package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.zelkatani.conquest.Assets;
import com.zelkatani.conquest.entities.Pathway;

public class Manager {
    private Stage stage;
    private static NumberField numberField = new NumberField("10", Assets.SKIN);
    private static TextButton maxSendButton = new TextButton("Send All", Assets.SKIN);
    private static TextButton partialSendButton = new TextButton("Send", Assets.SKIN);

    private Pathway pathway;

    public Manager(Pathway pathway) {
        setVisible(false);
        stage = new Stage();

        maxSendButton.setSize(200,100);
        maxSendButton.setPosition(Gdx.graphics.getWidth() * 2 / 3 - 100, Gdx.graphics.getHeight() / 2 - 50);
        maxSendButton.getLabel().setAlignment(Align.center);

        partialSendButton.setSize(200, 50);
        partialSendButton.setPosition(Gdx.graphics.getWidth() / 3 - 100, Gdx.graphics.getHeight() / 2 - 50);
        partialSendButton.getLabel().setAlignment(Align.center);

        stage.addActor(numberField);
        stage.addActor(maxSendButton);
        stage.addActor(partialSendButton);

        stage.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (!Manager.isVisible()) return false;
                if (!(event.getTarget() instanceof NumberField)) stage.setKeyboardFocus(null);

                if (event.getTarget() instanceof Group) {
                    setVisible(false);
                }
                return false;
            }
        });

        this.pathway = pathway;

        partialSendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Manager.this.pathway.send(numberField.getValue());

                setVisible(false);
            }
        });

        maxSendButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Manager.this.pathway.sendMax();

                setVisible(false);
            }
        });
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public static boolean isVisible() {
        return numberField.isVisible() && maxSendButton.isVisible();
    }

    public void setVisible(boolean visible) {
        numberField.setVisible(visible);
        maxSendButton.setVisible(visible);
        partialSendButton.setVisible(visible);
    }
}
