/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Abdo Amin
 */
public class GameRoom implements Serializable{

    private String roomName;
    private HashMap<String, ClientCallBack> players;

    public GameRoom(String roomName, HashMap<String, ClientCallBack> players) {
        this.roomName = roomName;
        this.players = players;
    }

    public String getRoomName() {
        return roomName;
    }

    public HashMap<String, ClientCallBack> getPlayers() {
        return players;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setPlayers(HashMap<String, ClientCallBack> players) {
        this.players = players;
    }

    
    public void addPlayer(String playerUserName,ClientCallBack player) {
        this.players.put(playerUserName,player);
    }
    
    public void removePlayer(String playerUserName) {
        this.players.remove(playerUserName);
    }

}
