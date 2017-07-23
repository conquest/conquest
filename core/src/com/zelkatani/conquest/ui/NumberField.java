package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

class NumberField extends TextField {
    NumberField(String text, Skin skin) {
        super(text, skin);
        setSize(115, 40);
        setPosition(Gdx.graphics.getWidth() / 3 - getWidth() / 2, Gdx.graphics.getHeight() / 2 + getHeight() / 4);
        setAlignment(Align.center);
        setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
    }

    int getValue() {
        if (getText().isEmpty()) setText("1");
        return Integer.parseInt(getText());
    }

    void setValue(int value) {
        setText(String.valueOf(MathUtils.clamp(value, 1, Integer.MAX_VALUE)));
    }
}
