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
    private Array<GraphPath<Tile>> paths;

    public Pathway(Array<Tile> tiles) {
        start = new Array<>();

        Map map = new Map(tiles.size);
        pathFinder = new IndexedAStarPathFinder<>(map);
        heuristic = new EuclidianHeuristic();

        graphPath = new DefaultGraphPath<>();
        paths = new Array<>();
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
        getPaths();

        for (Tile tile : start) {
            tile.transferTroops(end, tile.getTroops() - 1);
            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }

    public void send(int value) {
        getPaths();

        for (Tile tile : start) {
            tile.transferTroops(end, tile.getTroops() > value ? value : tile.getTroops() - 1);
            tile.updateLabel();
        }

        end.updateLabel();
        clearStart();
    }

    private void getPaths() {
        for (Tile tile : start) {
            pathFinder.searchNodePath(tile, end, heuristic, graphPath);
            paths.add(graphPath);
            graphPath.clear();
        }
    }
}
