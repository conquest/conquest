package com.zelkatani.conquest.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.Owner;
import com.zelkatani.conquest.entities.Tile;

public class Map implements IndexedGraph<Tile> {
    private int count;
    private Owner owner;

    public Map(int count, Owner owner) {
        this.count = count;
        this.owner = owner;
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode) {
        return fromNode.getOwner() == owner ? fromNode.getContacts() : new Array<>();
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
