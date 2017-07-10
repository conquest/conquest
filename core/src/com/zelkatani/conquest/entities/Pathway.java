package com.zelkatani.conquest.entities;

import com.badlogic.gdx.utils.Array;

public class Pathway {
    private Array<Tile> tiles;

    private Array<Tile> start;
    private Tile end;

    public Pathway(Array<Tile> tiles) {
        this.tiles = tiles;
        start = new Array<>();
    }

    public Array<Tile> getStart() {
        return start;
    }

    public void setStart(Array<Tile> start) {
        this.start.addAll(start);
    }

    public void clearStart() {
        start.clear();
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    public void sendMax() {
        for (Tile tile : start) {
            tile.transferTroops(end, tile.getTroops() - 1);
            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }

    public void send(int value) {
        for (Tile tile : start) {
            tile.transferTroops(end, tile.getTroops() > value ? value : tile.getTroops() - 1);
            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }
}
