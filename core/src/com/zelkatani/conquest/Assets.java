package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public final class Assets {
    public static final Texture LOGO = new Texture(Gdx.files.internal("textures/logo.png"));

    public static final class TileTexture {
        private Texture texture;

        public TileTexture(int width, int height) {
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

            pixmap.setColor(Color.LIGHT_GRAY);
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

    public static final class ConquestLabel extends Label {
        public ConquestLabel(String text, float x, float y, float width, float height) {
            super(text, Assets.SKIN);
            setAlignment(Align.center, Align.center);
            setBounds(x, y, width, height);
        }

        public ConquestLabel(String text, float x, float y) {
            this(text, x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        public ConquestLabel(int number, float x, float y, float width, float height) {
            this(null, x, y, width, height);
            setText(number);
        }

        public void setText(int number) {
            if (number >= 1e3f) {
                setText(String.format("%sk", number / 1e3f));
            } else {
                setText(String.format("%s", number));
            }
        }
    }

    static final TextureAtlas ATLAS = new TextureAtlas(Gdx.files.internal("textures/conquest.atlas"));
    public static final TextureRegion TROOP = ATLAS.findRegion("troop");
    public static final TextureRegion CITY = ATLAS.findRegion("city");
    public static final Texture FOGGY;
    static {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 1, 1);

        FOGGY = new Texture(pixmap);

        pixmap.dispose();
    }

    public static Color highlight(Color color) {
        return color.cpy().mul(1.25f);
    }
    public static Color select(Color color) {
        return color.cpy().mul(1.75f);
    }
    public static Color random() {
        return new Color(MathUtils.random(0.25f, 1), MathUtils.random(0.25f, 1), MathUtils.random(0.25f, 1), 1);
    }

    public static final Skin SKIN = new Skin();
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 22;
        parameter.borderWidth = 2;

        SKIN.add("roboto", generator.generateFont(parameter), BitmapFont.class);
        generator.dispose();

        SKIN.addRegions(new TextureAtlas(Gdx.files.internal("skin/skin.atlas")));
        SKIN.load(Gdx.files.internal("skin/skin.json"));
    }
}
