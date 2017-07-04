package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

class SendButton extends TextButton {
    public SendButton(String text, Skin skin) {
        super(text, skin);

        setSize(200,100);
        setPosition(Gdx.graphics.getWidth() * 2 / 3 - 100, Gdx.graphics.getHeight() / 2 - 50);
        getLabel().setAlignment(Align.center);
    }
}
