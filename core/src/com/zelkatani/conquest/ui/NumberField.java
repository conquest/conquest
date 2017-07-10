package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

class NumberField extends TextField {
    NumberField(String text, Skin skin) {
        super(text, skin);
        setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
        setPosition(Gdx.graphics.getWidth() / 3 - getWidth() / 2, Gdx.graphics.getHeight() / 2 + getHeight() / 4);
        setAlignment(Align.center);
    }

    int getValue() {
        return Integer.parseInt(getText());
    }
}
