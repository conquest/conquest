package com.zelkatani.conquest.entities;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.pathfinding.EuclidianHeuristic;
import com.zelkatani.conquest.pathfinding.Map;

public class Pathway {
    private Array<Tile> start;
    private Tile end;

    private IndexedAStarPathFinder<Tile> pathFinder;
    private EuclidianHeuristic heuristic;

    private GraphPath<Tile> graphPath;

    public Pathway(Array<Tile> tiles) {
        start = new Array<>();

        Map map = new Map(tiles.size);
        pathFinder = new IndexedAStarPathFinder<>(map);
        heuristic = new EuclidianHeuristic();

        graphPath = new DefaultGraphPath<>();
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
            int value = tile.getTroops() - 1;
            if (value == 0 || tile == end) continue;

            tile.removeTroops(value);
            sendTroop(tile, value);

            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }

    public void send(int value) {
        for (Tile tile : start) {
            int adjust = tile.getTroops() > value ? value : tile.getTroops() - 1;
            if (adjust == 0 || tile == end) continue;

            tile.removeTroops(adjust);
            sendTroop(tile, adjust);

            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }

    private void sendTroop(Tile current, int value) {
        pathFinder.searchNodePath(current, end, heuristic, graphPath);

        Troop troop = new Troop(value, graphPath);
        current.getStage().addActor(troop);
        graphPath.clear();
    }
}
