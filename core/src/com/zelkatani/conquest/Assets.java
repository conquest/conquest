package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Assets {
    public static final class TileTexture {
        private Texture texture;

        public TileTexture(int width, int height) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

            pixmap.setColor(Color.BLACK);
            pixmap.fillRectangle(0, 0, width, height);

            pixmap.setColor(Color.WHITE);
            pixmap.fillRectangle(1, 1, width - 2, height - 2);

            texture = new Texture(pixmap);

            pixmap.dispose();
        }

        public Texture getTexture() {
            return texture;
        }
    }

    public static final Texture CITY;
    static {
        Pixmap pixmap = new Pixmap(21, 21, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(10, 10, 10);

        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(10, 10, 9);

        CITY = new Texture(pixmap);

        pixmap.dispose();
    }

    public static final class ConquestLabel extends Label {
        public ConquestLabel(String text, float x, float y, float width, float height) {
            super(text, Assets.SKIN);
            setBounds(x, y, width, height);
        }

        public void setText(int number) {
            if (number >= 1e3f) {
                setText(String.format("%sk", number / 1e3f));
            } else {
                setText(String.format("%s", number));
            }
        }
    }

    public static final Texture TROOP;
    static {
        int width = 40, height = 20;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);

        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, width, height);

        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(1, 1, width - 2, height - 2);

        TROOP = new Texture(pixmap);

        pixmap.dispose();
    }

    public static Color highlight(Color color) {
        return color.cpy().mul(1.25f);
    }

    public static Color select(Color color) {
        return color.cpy().mul(1.5f);
    }

    public static final Skin SKIN = new Skin();
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.borderWidth = 2;

        SKIN.add("roboto", generator.generateFont(parameter), BitmapFont.class);
        generator.dispose();

        SKIN.addRegions(new TextureAtlas(Gdx.files.internal("skin/skin.atlas")));
        SKIN.load(Gdx.files.internal("skin/skin.json"));
    }
}
