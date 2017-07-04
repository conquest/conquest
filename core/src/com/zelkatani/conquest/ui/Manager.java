package com.zelkatani.conquest.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.zelkatani.conquest.Assets;

public class Manager {
    private Stage stage;
    private static NumberField numberField = new NumberField("10", Assets.SKIN);
    private static SendButton sendButton = new SendButton("Send All", Assets.SKIN);

    public Manager() {
        stage = new Stage();

        stage.addActor(numberField);
        stage.addActor(sendButton);

        stage.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (!(event.getTarget() instanceof NumberField)) stage.setKeyboardFocus(null);

                if (event.getTarget() instanceof Group) {
                    for (Actor actor : stage.getActors()) {
                        actor.setVisible(false);
                    }
                }
                return false;
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
        return numberField.isVisible() && sendButton.isVisible();
    }
}
