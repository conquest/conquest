package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.multiplayer.SerialArray;

public class Player extends Owner implements Json.Serializable {
    private SerialArray<Tile> owned;
    private Tile capital;

    public Player(Color color, Tile capital) {
        super(color);
        this.capital = capital;

        owned = new SerialArray<>();
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
        capital.updateLabel();
    }

    public boolean ownsCapital() {
        return capital.getOwner() == this;
    }

    public SerialArray<Tile> getOwned() {
        return owned;
    }

    public void add(Tile tile) {
        owned.add(tile);
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

    }
}
