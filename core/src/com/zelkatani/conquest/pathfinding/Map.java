package com.zelkatani.conquest.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.Tile;

public class Map implements IndexedGraph<Tile> {
    private int count;

    public Map(int count) {
        this.count = count;
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {
        return fromNode.getContacts();
    }

    @Override
    public int getIndex(Tile node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return count;
    }
}
