package com.zelkatani.conquest.entities;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.zelkatani.conquest.Assets;

public class Troop extends Actor {
    private int troops;
    private GraphPath<Tile> path;

    private TextureRegion texture;
    private Label label;

    public Troop(int troops, GraphPath<Tile> path) {
        this.troops = troops;
        this.path = new DefaultGraphPath<>();

        for (Tile tile : path) {
            this.path.add(tile);
        }

        texture = Assets.TROOP;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());

        label = new Assets.ConquestLabel("" + this.troops, getX(), getY(), getWidth(), getHeight());
        label.setAlignment(Align.center, Align.center);

        setOrigin(Align.center);
        createActions();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), 1, 1, getRotation());
        label.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        label.act(delta);
    }

    private Vector2 centered(Tile tile) {
        return new Vector2(tile.getX(Align.center) - getWidth() / 2, tile.getY(Align.center) - getHeight() / 2);
    }

    private float speed(Tile now, Tile next) {
        float time = centered(now).dst(centered(next)) * MathUtils.log(5, troops) / 30;
        return MathUtils.floor(MathUtils.clamp(time, 1, 5));
    }

    private void createActions() {
        SequenceAction troopSequence = new SequenceAction();
        SequenceAction labelSequence = new SequenceAction();

        Vector2 centered = centered(path.get(0));
        setPosition(centered.x, centered.y);
        label.setPosition(centered.x, centered.y);

        for (int i = 0; i < path.getCount() - 1; i++) {
            Tile next = path.get(i + 1);
            centered = centered(next);

            troopSequence.addAction(Actions.rotateTo(angleToNext(i)));
            troopSequence.addAction(Actions.moveTo(centered.x, centered.y, speed(path.get(i), next), Interpolation.linear));
            labelSequence.addAction(Actions.moveTo(centered.x, centered.y, speed(path.get(i), next), Interpolation.linear));
        }

        troopSequence.addAction(Actions.run(() -> {
            Tile last = path.get(path.getCount() - 1);
            if (last.getOwner() == path.get(0).getOwner()) {
                last.adjustTroops(troops);
                last.updateLabel();
            } else {
                new Battle(last, path.get(path.getCount() - 2), this);
            }
        }));
        troopSequence.addAction(Actions.removeActor());
        labelSequence.addAction(Actions.removeActor());

        addAction(troopSequence);
        label.addAction(labelSequence);
    }

    private float angleToNext(int index) {
        Tile current = path.get(index);
        Tile next = path.get(index + 1);
        float dX = current.getX(Align.center) - next.getX(Align.center);
        float dY = current.getY(Align.center) - next.getY(Align.center);
        return MathUtils.atan2(dY, dX) * MathUtils.radDeg;
    }

    public int getTroops() {
        return troops;
    }

    public void adjustTroops(int troops) {
        this.troops += troops;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        label.setColor(color);
    }
}
