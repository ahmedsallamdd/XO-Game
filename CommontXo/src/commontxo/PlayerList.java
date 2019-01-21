/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.io.Serializable;

/**
 *
 * @author Abdo Amin
 */
public class PlayerList implements Serializable {

    private String name;
    private int score;
    private String roomName;

    public PlayerList(String name, int score, String roomName) {
        this.name = name;
        this.score = score;
        this.roomName = roomName;
    }

    public PlayerList(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return name.equals(((PlayerList) obj).name);
        }
        return false;
    }
}
