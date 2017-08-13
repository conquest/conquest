package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.entities.Tile;

public class Packet implements Json.Serializable {
    private Player player;
    private Tile tile;

    public Packet(Player player, Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public void write(Json json) {
        tile.write(json);
        player.write(json);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
    }
}
