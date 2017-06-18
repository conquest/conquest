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

    public Match() {
        cam = new Camera();
        tileArray = new Array<>();

        tileArray.add(new Tile(100, 100, 100, 50, Color.GREEN));
        tileArray.add(new Tile(20, 30, 50, 100, Color.BLUE));
        tileArray.add(new Tile(100, 100, 150, 200, Color.RED));
        tileArray.add(new Tile(100, 100, 300, 25, Color.SCARLET));

        tileArray.get(0).addCity(new City(20, 40, false));

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
            renderer.setColor(t.getColor());
            renderer.rect(t.getX(), t.getY(), t.getWidth(), t.getHeight());

            for (City city : t.getCities()) {
                renderer.setColor(city.getColor());
                renderer.circle(t.getX() + city.getX(), t.getY() + city.getY(), City.SIZE);
            }
        }
        renderer.end();
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
