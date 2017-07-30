package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.multiplayer.SerialArray;

public class Level {
    private SerialArray<Tile> tiles;

    public Level() {
        JsonReader reader = new JsonReader();
        tiles = new SerialArray<>();

        JsonValue value = reader.parse(Gdx.files.internal("maps/new-york.json"));
        JsonValue regions = value.get("regions");
        float scale = value.getFloat("scale");

        for (JsonValue region : regions) {
            Color color = Color.valueOf(region.getString("color"));

            for (JsonValue tile : region.get("tiles")) {
                int x = tile.getInt("x"), y = tile.getInt("y");
                int width = tile.getInt("w"), height = tile.getInt("h");
                Tile t = new Tile(x * scale, y * scale, (int) (width * scale), (int) (height * scale), color);

                if (tile.get("city") != null) {
                    JsonValue city = tile.get("city");
                    float cx = city.getInt("x") * scale, cy = city.getInt("y") * scale;
                    City c = new City(cx + t.getX(), cy + t.getY(), city.getBoolean("major"));

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

    public SerialArray<Tile> getTiles() {
        return tiles;
    }
}
