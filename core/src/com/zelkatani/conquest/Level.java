package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;

public class Level {
    private Array<Tile> tiles;

    public Level() {
        JsonReader reader = new JsonReader();
        tiles = new Array<>();

        JsonValue value = reader.parse(Gdx.files.internal("maps/new-york.json"));
        JsonValue regions = value.get("regions");
        float scale = value.get("scale").asFloat();

        for (JsonValue region : regions) {
            Color color = Color.valueOf(region.get("color").asString());

            for (JsonValue tile : region.get("tiles")) {
                int x = tile.get("x").asInt(), y = tile.get("y").asInt();
                int width = tile.get("w").asInt(), height = tile.get("h").asInt();
                Tile t = new Tile(x * scale, y * scale, (int) (width * scale), (int) (height * scale), color);

                if (tile.get("city") != null) {
                    JsonValue city = tile.get("city");
                    float cx = city.get("x").asInt() * scale, cy = city.get("y").asInt() * scale;
                    City c = new City(cx + t.getX(), cy + t.getY(), city.get("major").asBoolean());

                    t.setCity(c);
                }

                tiles.add(t);
            }
        }

        for (int i = 0; i < tiles.size; i++) {
            tiles.get(i).setContacts(tiles);
            tiles.get(i).setIndex(i);
        }
    }

    public Array<Tile> getTiles() {
        return tiles;
    }
}
