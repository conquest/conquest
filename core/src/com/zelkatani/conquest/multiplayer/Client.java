package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.entities.Tile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataOutputStream outputStream;
    private BufferedReader reader;

    private Json json;
    private SerialArray<Tile> map;
    private Player player;

    public Client(Player player, SerialArray<Tile> map) {
        json = new Json(JsonWriter.OutputType.json);
        json.setSerializer(Packet.class, new Json.Serializer<Packet>() {
            @Override
            public void write(Json json, Packet object, Class knownType) {
                json.writeObjectStart();
                object.write(json);
                json.writeObjectEnd();
            }

            @Override
            public Packet read(Json json, JsonValue jsonData, Class type) {
                return null;
            }
        });

        this.player = player;
        this.map = map;

        try {
            socket = new Socket("", 8080);
            outputStream = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    // TODO receive messages from server
    public void update() {
        send();
    }

    private void send() {
        try {
            Packet packet = new Packet(player, map);
            outputStream.writeBytes(json.toJson(packet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
