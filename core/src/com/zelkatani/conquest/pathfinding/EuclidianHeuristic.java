package com.zelkatani.conquest.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import com.zelkatani.conquest.entities.Tile;

public class EuclidianHeuristic implements Heuristic<Tile> {
    @Override
    public float estimate(Tile node, Tile endNode) {
        Vector2 from = node.getCenter();
        Vector2 goal = endNode.getCenter();

        float dX = Math.abs(from.x - goal.x);
        float dY = Math.abs(from.y - goal.y);

        float heuristic = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
        return heuristic * (1 + 1 / 500f);
    }
}
