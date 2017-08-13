package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.multiplayer.SerialArray;

public class Player extends Owner implements Json.Serializable {
    private SerialArray<Tile> owned;
    private Tile capital;

    public Player() {
        this(Color.WHITE);
    }

    public Player(Color color) {
        super(color);

        owned = new SerialArray<>();
    }

    public void setCapital(Tile capital) {
        this.capital = capital;
        capital.setOwner(this);
        owned.add(capital);

        int sum = 0;
        for (Tile tile : capital.getNeighbors()) {
            sum += 10;
            if (tile.getCity() != null) {
                sum += tile.getCity().isMajor() ? 10 : 5;
            }
        }
        capital.setTroops(sum);
    }

    public SerialArray<Tile> getOwned() {
        return owned;
    }

    public void add(Tile tile) {
        owned.add(tile);
    }

    public void remove(Tile tile) {
        owned.removeValue(tile, true);
    }

    @Override
    public void write(Json json) {
        json.writeObjectStart("player");
        json.writeValue("id", getId());
        json.writeValue("color", getColor().toString());
        json.writeObjectEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        setId(jsonData.getInt("id"));
        setColor(Color.valueOf(jsonData.getString("color")));
    }
}
