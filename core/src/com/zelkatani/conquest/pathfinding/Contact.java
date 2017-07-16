package com.zelkatani.conquest.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.zelkatani.conquest.entities.Tile;

public class Contact implements Connection<Tile> {
    private Tile fromNode;
    private Tile toNode;

    public Contact(Tile fromNode, Tile toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public float getCost() {
        return fromNode.getCenter().dst(toNode.getCenter()) / 10;
    }

    @Override
    public Tile getFromNode() {
        return fromNode;
    }

    @Override
    public Tile getToNode() {
        return toNode;
    }
}
