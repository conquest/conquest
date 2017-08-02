package com.zelkatani.conquest.entities;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.*;
import com.zelkatani.conquest.Assets;
import com.zelkatani.conquest.Assets.ConquestLabel;
import com.zelkatani.conquest.Owner;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.pathfinding.Contact;

public class Tile extends Actor implements Disposable, Json.Serializable, Comparable<Tile> {
    private int troops;
    private ConquestLabel label;
    private Texture texture;
    private City city;
    private Owner owner;

    private Array<Connection<Tile>> contacts;
    private Array<Tile> neighbors;
    private int index;

    private Rectangle rectangle;

    private boolean hovered = false;
    private boolean selected = false;
    private boolean hidden = false;
    private boolean attacked = false;

    private String region;

    public Tile(float x, float y, int width, int height, Color color) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setColor(color);

        contacts = new Array<>();
        neighbors = new Array<>();

        rectangle = new Rectangle(x, y, width, height);
        troops = 0;

        label = new ConquestLabel("" + troops, x, y, width, height);
        label.setAlignment(Align.center, Align.center);

        owner = Owner.None;
        region = "";
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        if (hidden) {
            color = Color.DARK_GRAY;
        } else if (selected) {
            color = Assets.select(color);
        } else if (hovered) {
            color = Assets.highlight(color);
        }

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (hidden) {
            batch.draw(Assets.FOGGY, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }

        if (city != null) {
            city.draw(batch, parentAlpha);
        }
    }

    public void update() {
        if (owner instanceof Player && !((Player) owner).ownsCapital()) return;
        troops += owner != Owner.None ? 3 : 2;

        if (city != null) {
            troops += city.isMajor() ? 5 : 2;
        }

        updateLabel();
    }

    private void updateLabel() {
        label.setText(troops);
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getCenter() {
        return rectangle.getCenter(new Vector2());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public int getTroops() {
        return troops;
    }

    public void adjustTroops(int troops) {
        this.troops += troops;
        updateLabel();
    }

    public void setTroops(int troops) {
        this.troops = troops;
        updateLabel();
    }

    public void setContacts(Array<Tile> tiles) {
        Rectangle bounds = new Rectangle(rectangle);
        bounds.setSize(rectangle.width + 2, rectangle.height + 2);
        bounds.setPosition(rectangle.x - 1, rectangle.y - 1);

        for (Tile t : tiles) {
            if (t == this) continue;
            if (bounds.overlaps(t.getRectangle())) {
                contacts.add(new Contact(this, t));
                neighbors.add(t);
            }
        }
    }

    public Array<Connection<Tile>> getContacts() {
        return contacts;
    }

    public Array<Tile> getNeighbors() {
        return neighbors;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ConquestLabel getLabel() {
        return label;
    }

    public void setOwner(Player player) {
        if (owner instanceof Player) {
            ((Player) owner).remove(this);
        }

        owner = player;
        label.setColor(owner.getColor());
        if (owner != Owner.None) {
            setColor(owner.getColor());
        }
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        label.setVisible(visible);
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        label.setVisible(!hidden);
        if (city != null) {
            city.setVisible(!hidden);
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void write(Json json) {
        json.writeValue("region", region);
        json.writeValue("index", index);
        json.writeValue("owner", owner.getId());
        json.writeValue("troops", troops);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
    }

    public void setTexture() {
        texture = new Assets.TileTexture((int) getWidth(), (int) getHeight()).getTexture();
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public boolean isAttacked() {
        return attacked;
    }

    @Override
    public int compareTo(Tile t) {
        return Integer.compare(index, t.getIndex());
    }
}
