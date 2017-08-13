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

    public static Client client;
    private Player player;

    public Match(Client client, Player player) {
        Match.client = client;
        this.tiles = Match.client.getMap();
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
        while (random.getOwner() != Owner.None) {
            random = this.tiles.get(MathUtils.random(this.tiles.size - 1));
        }
        player.setCapital(random);
        client.send(random);

        cam.position.set(random.getX(Align.center), random.getY(Align.center), 0);

        stage.addActor(tileGroup);
        stage.addActor(labelGroup);
    }

    public void draw() {
        if (player.getOwned().size != 0) {
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
        } else {
            // Player owns nothing, so they've lost the match.
            for (Tile tile : tiles) {
                tile.setHidden(false);
            }
        }

        stage.act();
        stage.draw();
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

    @Override
    public void dispose() {
        for (Tile t : tiles) {
            t.dispose();
        }
        stage.dispose();
    }
}
