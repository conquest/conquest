package com.zelkatani.conquest.multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    public Client(Player player) {
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
                JsonValue tile = jsonData.get("tile");
                JsonValue player = jsonData.get("player");

                if (tile == null && jsonData.get("id") == null) {
                    throw new SerializationException();
                }

                if (tile == null || players.get(player.getInt("id")) == null) {
                    Player p = json.readValue(Player.class, tile == null ? jsonData : player);
                    players.put(p.getId(), p);
                    if (tile == null) return null;
                }

                Tile t = map.get(tile.getInt("index"));
                t.setTroops(tile.getInt("troops"));
                if (tile.getInt("owner") != 0) {
                    Player p = players.get(tile.getInt("owner"));
                    t.setTroops(tile.getInt("troops"));
                    t.setOwner(p);
                    p.add(t);
                }
                return null;
            }
        });
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = new DataOutputStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread receiveThread = new Thread(this::receive);
        receiveThread.start();
    }

    public void send(Tile tile) {
        try {
            Packet packet = new Packet(player, tile);
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
                    map = Level.load(str, players);
                    map.sort();
                    Gdx.app.postRunnable(() -> {
                        MatchScreen matchScreen = new MatchScreen(new Match(Client.this, player));
                        ((Game) Gdx.app.getApplicationListener()).setScreen(matchScreen);
                    });
                } catch (Exception e) {
                    try {
                        json.fromJson(Packet.class, str);
                    } catch (Exception e2) {
                        if (str.equals("refresh")) {
                            for (int i = 0; i < map.size; i++) {
                                map.get(i).update();
                            }
                        } else if (str.contains(":")) {
                            Hud.getTimer().setText(str);
                        } else {
                            player.setId(Integer.parseInt(str));
                            players.put(player.getId(), player);
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
