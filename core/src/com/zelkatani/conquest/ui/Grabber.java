package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.Pathway;
import com.zelkatani.conquest.entities.Tile;

public class Grabber extends InputAdapter {
    private enum Mode {
        FIRST, SECOND, NONE;

        private static Mode[] modes = values();
        public Mode next() {
            return modes[(this.ordinal() + 1) % modes.length];
        }
    }

    private Array<Tile> tiles;
    private Rectangle rect;
    private Camera cam;

    private Manager manager;
    private Pathway pathway;

    private float touchX, touchY, mouseX, mouseY;
    private Mode mode;

    public Grabber(Array<Tile> tiles, Camera cam, Manager manager, Pathway pathway) {
        this.tiles = tiles;
        rect = new Rectangle();
        this.cam = cam;

        this.manager = manager;
        this.pathway = pathway;

        mode = Mode.FIRST;
    }

    public void draw(ShapeRenderer renderer) {
        if (rect.width < 25 || rect.height < 25) return;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0x228b2255));
        renderer.rect(rect.x, rect.y, rect.width, rect.height);

        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
    }

    public void grab() {
        float width = touchX - mouseX, height = mouseY - touchY;
        float offX = width < 0 ? width : 0, offY = height < 0 ? height : 0;

        Vector3 vector3 = correct(mouseX, mouseY);

        rect.set(vector3.x + offX, vector3.y + offY, Math.abs(width), Math.abs(height));

        if (rect.width < 10 || rect.height < 10) return;
        for (Tile tile : tiles) {
            tile.setHovered(rect.contains(tile.getCenter()));
        }
    }

    private Vector3 correct(float pointX, float pointY) {
        Vector3 vector3 = new Vector3(pointX, pointY, 0);
        cam.unproject(vector3);

        return vector3;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        Vector3 vector3 = correct(screenX, screenY);
        for (Tile tile : tiles) {
            tile.setHovered(tile.getRectangle().contains(vector3.x, vector3.y));
        }

        return false;
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        touchX = mouseX = screenX;
        touchY = mouseY = screenY;

        Vector3 vector3 = correct(screenX, screenY);

        for (Tile tile : tiles) {
            tile.setHovered(false);
            tile.setSelected(tile.getRectangle().contains(vector3.x, vector3.y));
            if (tile.isSelected() && mode.equals(Mode.NONE)) {
                mode = Mode.FIRST;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        mouseX = touchX = screenX;
        mouseY = touchY = screenY;

        Array<Tile> selected = new Array<>();
        Vector3 vector3 = correct(screenX, screenY);

        for (Tile tile : tiles) {
            tile.setSelected(rect.contains(tile.getCenter()) || tile.getRectangle().contains(vector3.x, vector3.y));
            if (tile.isSelected()) {
                selected.add(tile);
            }
        }

        if (selected.size == 0) {
            mode = Mode.FIRST;
            return false;
        }

        switch (mode) {
            case FIRST: {
                pathway.setStart(selected);
                break;
            }
            case SECOND: {
                pathway.setEnd(selected.get(0));
                manager.setVisible(true);

                for (Tile t : pathway.getStart()) {
                    t.setSelected(true);
                }
            }
        }

        mode = mode.next();
        return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;

        if (Math.abs(touchX - mouseX) < 10 || Math.abs(touchY - mouseY) < 10) return false;

        pathway.clearStart();
        mode = Mode.FIRST;

        for (Tile tile : tiles) {
            tile.setHovered(rect.contains(tile.getCenter()));
        }
        return false;
    }
}
