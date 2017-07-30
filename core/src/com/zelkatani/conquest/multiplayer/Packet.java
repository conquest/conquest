package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.entities.Tile;

public class Packet implements Json.Serializable {
    private SerialArray<Tile> tiles;
    private Player player;

    public Packet(Player player, SerialArray<Tile> tiles) {
        this.player = player;
        this.tiles = new SerialArray<>();
        for (Tile tile : tiles) {
            if (!tile.isHidden() && (tile.getOwner() instanceof Player && tile.getOwner() == player)) {
                this.tiles.add(tile);
            }
        }
    }

    @Override
    public void write(Json json) {
        tiles.write(json);
        player.write(json);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
    }
}
