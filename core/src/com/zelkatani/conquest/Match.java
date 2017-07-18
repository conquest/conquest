package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zelkatani.conquest.entities.Tile;

public class Match implements Disposable {
    private ConquestCamera cam;
    private Stage stage;
    private Player player;

    private Array<Tile> tiles;

    public Match(Array<Tile> tiles) {
        cam = new ConquestCamera(tiles);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));

        this.tiles = tiles;

        Group tileGroup = new Group();
        Group labelGroup = new Group();
        for (Tile t : this.tiles) {
            tileGroup.addActor(t);
            labelGroup.addActor(t.getLabel());
            t.setOwner(Owner.None);
        }

        Tile random = this.tiles.get(MathUtils.random(this.tiles.size - 1));
        player = new Player(Color.GREEN, random);
        cam.position.set(random.getX(Align.center), random.getY(Align.center), 0);

        stage.addActor(tileGroup);
        stage.addActor(labelGroup);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Tile t : tiles) {
                    t.update();
                }
            }
        }, 10.5f, 10);
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
