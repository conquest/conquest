package com.zelkatani.conquest;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;

public class Match {
    private Array<Tile> tileArray;

    public Match() {
        tileArray = new Array<>();

        tileArray.add(new Tile(100, 100, 100, 50, Color.GREEN));
        tileArray.add(new Tile(20, 30, 50, 100, Color.BLUE));
        tileArray.add(new Tile(100, 100, 150, 200, Color.RED));
        tileArray.add(new Tile(100, 100, 300, 25, Color.SCARLET));

        tileArray.get(0).addCity(new City(20, 40, false));
    }

    public void draw(ShapeRenderer renderer) {
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
}
