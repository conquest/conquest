package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.zelkatani.conquest.Assets;

class PartialSendGroup extends Group {
    private NumberField numberField;
    private TextButton sendButton;

    PartialSendGroup() {
        numberField = new NumberField("10", Assets.SKIN);
        float dimensions = numberField.getHeight();

        TextButton divideButton = new TextButton("/ 2", Assets.SKIN);
        divideButton.setSize(dimensions, dimensions);
        divideButton.setPosition(Gdx.graphics.getWidth() / 3 - 100, Gdx.graphics.getHeight() / 2 + dimensions / 4);
        divideButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numberField.setValue(numberField.getValue() / 2);
            }
        });

        TextButton doubleButton = new TextButton("* 2", Assets.SKIN);
        doubleButton.setSize(dimensions, dimensions);
        doubleButton.setPosition(Gdx.graphics.getWidth() / 3 + 100 - dimensions, Gdx.graphics.getHeight() / 2 + dimensions / 4);
        doubleButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                numberField.setValue(numberField.getValue() * 2);
            }
        });

        sendButton = new TextButton("Send", Assets.SKIN);
        sendButton.setSize(200, 50);
        sendButton.setPosition(Gdx.graphics.getWidth() / 3 - 100, Gdx.graphics.getHeight() / 2 - 50);
        sendButton.getLabel().setAlignment(Align.center);

        addActor(numberField);
        addActor(divideButton);
        addActor(doubleButton);
        addActor(sendButton);
    }

    int getValue() {
        return numberField.getValue();
    }

    TextButton getSendButton() {
        return sendButton;
    }
}
