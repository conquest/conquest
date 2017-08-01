package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.multiplayer.Client;
import com.zelkatani.conquest.multiplayer.SerialArray;

public class Match implements Disposable {
    private ConquestCamera cam;
    private Stage stage;

    private SerialArray<Tile> tiles;

    private Client client;
    private Player player;

    public Match(Client client, Player player) {
        this.client = client;
        this.tiles = this.client.getMap();
        this.player = player;

        cam = new ConquestCamera(this.tiles);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));

        Group tileGroup = new Group();
        Group labelGroup = new Group();
        for (Tile t : this.tiles) {
            t.setTexture();
            tileGroup.addActor(t);
            labelGroup.addActor(t.getLabel());
        }

        Tile random = this.tiles.get(MathUtils.random(this.tiles.size - 1));
        player.setCapital(random);

        cam.position.set(random.getX(Align.center), random.getY(Align.center), 0);

        stage.addActor(tileGroup);
        stage.addActor(labelGroup);
    }

    public void draw() {
        for (Tile tile : tiles) {
            if (tile.getOwner() != player) {
                tile.setHidden(true);
            }
        }

        for (Tile tile : player.getOwned()) {
            for (Tile neighbor : tile.getNeighbors()) {
                if (neighbor.getOwner() == player) continue;
                neighbor.setHidden(false);
            }
        }

        client.send();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        for (Tile t : tiles) {
            t.dispose();
        }
    }

    public Array<Tile> getTiles() {
        return tiles;
    }

    public Stage getStage() {
        return stage;
    }

    public ConquestCamera getCam() {
        return cam;
    }

    public Player getPlayer() {
        return player;
    }
}
