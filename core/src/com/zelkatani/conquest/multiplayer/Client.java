package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SerializationException;
import com.zelkatani.conquest.Level;
import com.zelkatani.conquest.Match;
import com.zelkatani.conquest.Player;
import com.zelkatani.conquest.entities.Tile;
import com.zelkatani.conquest.screens.MatchScreen;
import com.zelkatani.conquest.ui.Hud;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private Socket socket;
    private DataOutputStream outputStream;
    private BufferedReader reader;

    private Json json;
    private SerialArray<Tile> map;

    private HashMap<Integer, Player> players;
    private Player player;

    private Game game;

    // TODO pvp syncing
    public Client(Game game, Player player) {
        this.game = game;
        players = new HashMap<>();
        this.player = player;

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
                JsonValue tiles = jsonData.get("tiles");
                JsonValue player = jsonData.get("player");

                if (tiles == null) {
                    Client.this.player.setId(jsonData.getInt("id"));
                    return null;
                } else {
                    if (players.get(player.getInt("id")) == null) {
                        Tile cap = map.get(player.get("capital").getInt("index"));
                        String color = player.getString("color");

                        Player opponent = new Player(Color.valueOf(color), cap);
                        opponent.setId(player.getInt("id"));
                        players.put(opponent.getId(), opponent);
                    }
                }

                for (JsonValue tile : tiles) {
                    Tile t = map.get(tile.getInt("index"));
                    if (tile.getInt("owner") == 0) {
                        t.setTroops(tile.getInt("troops"));
                        continue;
                    }

                    Player p = players.get(tile.getInt("owner"));
                    if (t.getOwner() == Client.this.player && t.isAttacked()) {
                        continue;
                    } else if (p != null) {
                        t.setOwner(p);
                        p.add(t);
                    }
                    t.setTroops(tile.getInt("troops"));
                }
                return null;
            }
        });

        try {
            socket = new Socket("", 8080);
            outputStream = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException io) {
            io.printStackTrace();
        }

        Thread receiveThread = new Thread(this::receive);
        receiveThread.start();
    }

    public void send() {
        try {
            Packet packet = new Packet(player, map);
            outputStream.writeBytes(json.toJson(packet));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void receive() {
        while (true) {
            try {
                String str = reader.readLine();
                try {
                    json.fromJson(Packet.class, str);
                } catch (SerializationException | IllegalArgumentException e) {
                    try {
                        map = Level.load(str);
                        map.sort();
                        Gdx.app.postRunnable(() ->
                                game.setScreen(new MatchScreen(new Match(Client.this, player)))
                        );
                    } catch (Exception e2) {
                        if (str.equals("refresh")) {
                            for (int i = 0; i < map.size; i++) {
                                map.get(i).update();
                            }
                        } else {
                            Hud.getTimer().setText(str);
                        }
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    public SerialArray<Tile> getMap() {
        return map;
    }
}
