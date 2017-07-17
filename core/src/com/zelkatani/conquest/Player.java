package com.zelkatani.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.Tile;

public class Player extends Owner {
    private Array<Tile> owned;
    private Tile capital;

    public Player(Color color, Tile capital) {
        super(color);
        this.capital = capital;

        owned = new Array<>();
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

    public Array<Tile> getOwned() {
        return owned;
    }

    public void add(Tile tile) {
        owned.add(tile);
    }
}
