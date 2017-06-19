package com.zelkatani.conquest;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;

public class Match {
    private Camera cam;
    private Array<Tile> tileArray;

    public Match(Array<Tile> tileArray) {
        cam = new Camera();
        this.tileArray = tileArray;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Tile t : tileArray) {
                    t.update();
                }
            }
        }, 10, 10);
    }

    public void draw(ShapeRenderer renderer) {
        cam.update();

        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Tile t : tileArray) {
            drawTile(renderer, t);
        }
        renderer.end();
    }

    private void drawTile(ShapeRenderer renderer, Tile t) {
        renderer.setColor(Color.BLACK);
        renderer.rect(t.getX(), t.getY(), t.getWidth(), t.getHeight());

        renderer.setColor(t.getColor());
        renderer.rect(t.getX() + 1, t.getY() + 1, t.getWidth() - 1, t.getHeight() - 1);

        if (t.getCity() != null) {
            drawCity(renderer, t);
        }
    }

    private void drawCity(ShapeRenderer renderer, Tile t) {
        City city = t.getCity();

        renderer.setColor(Color.BLACK);
        renderer.circle(t.getX() + city.getX(), t.getY() + city.getY(), City.SIZE);

        renderer.setColor(city.getColor());
        renderer.circle(t.getX() + city.getX(), t.getY() + city.getY(), City.SIZE - 1);
    }

    public void drawText(SpriteBatch batch, BitmapFont font, GlyphLayout layout) {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Tile t : tileArray) {
            String prod = "" + t.getTroops();
            layout.setText(font, prod);
            font.draw(batch, prod, t.getX() - layout.width / 2 + t.getWidth() / 2, t.getY() + layout.height / 2 + t.getHeight() / 2);
        }
        batch.end();
    }
}
