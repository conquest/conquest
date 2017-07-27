package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SerialArray<T> extends Array<T> implements Json.Serializable {
    @Override
    public void write(Json json) {
        json.writeArrayStart("tiles");
        for (T t : this) {
            json.writeValue(t);
        }
        json.writeArrayEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
    }
}
