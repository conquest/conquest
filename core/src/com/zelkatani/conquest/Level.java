package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;

public class Level {
    private Array<Tile> tileArray;

    public Level() {
        JsonReader reader = new JsonReader();
        tileArray = new Array<>();

        JsonValue value = reader.parse(Gdx.files.internal("maps/test.json"));

        // All regions MUST have tiles in them.
        for (JsonValue region : value) {
            int[] rgb = region.get("color").asIntArray();
            Color color = new Color(rgb[0] / 255f, rgb[1] / 255f, rgb[2] / 255f, 1);

            for (JsonValue tile : region.get("tiles")) {
                Tile t = new Tile(tile.get("w").asInt(), tile.get("h").asInt(), tile.get("x").asInt(), tile.get("y").asInt(), color);

                if (tile.get("city") != null) {
                    JsonValue city = tile.get("city");
                    City c = new City(city.get("x").asInt() + t.getX(), city.get("y").asInt() + t.getY(), city.get("major").asBoolean());

                    t.setCity(c);
                }

                tileArray.add(t);
            }
        }
    }

    public Array<Tile> getTileArray() {
        return tileArray;
    }
}
