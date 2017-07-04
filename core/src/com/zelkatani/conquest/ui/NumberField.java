package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

class NumberField extends TextField {
    public NumberField(String text, Skin skin) {
        super(text, skin);
        setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
        setPosition(Gdx.graphics.getWidth() / 3 - getWidth() / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);
        setAlignment(Align.center);
    }

    public int getValue() {
        return Integer.parseInt(getMessageText());
    }
}
