package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.City;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.multiplayer.SerialArray;

import java.util.HashMap;

public class Level {
    private static JsonReader reader = new JsonReader();

    public static SerialArray<Tile> load(String map, HashMap<Integer, Player> players) {
        JsonValue value = reader.parse(map);
        float scale = value.getFloat("scale");
        JsonValue regions = value.get("regions");

        SerialArray<Tile> tiles = new SerialArray<>();

        for (JsonValue region : regions) {
            Color color = Color.valueOf(region.getString("color"));

            for (JsonValue tile : region.get("tiles")) {
                int x = tile.getInt("x"), y = tile.getInt("y");
                int width = tile.getInt("w"), height = tile.getInt("h");
                Tile t = new Tile(x * scale, y * scale, (int) (width * scale), (int) (height * scale), color);
                t.setRegion(region.name);
                t.setTroops(tile.getInt("troops"));

                if (tile.get("city") != null) {
                    JsonValue city = tile.get("city");
                    float cx = city.getInt("x") * scale, cy = city.getInt("y") * scale;
                    City c = new City(cx + t.getX(), cy + t.getY(), city.getBoolean("major"));

                    t.setCity(c);
                }

                if (players.get(tile.getInt("owner")) != null) {
                    t.setOwner(players.get(tile.getInt("owner")));
                }

                t.setIndex(tile.getInt("index"));
                tiles.add(t);
            }
        }

        for (int i = 0; i < tiles.size; i++) {
            tiles.get(i).setContacts(tiles);
        }

        return tiles;
    }
}
