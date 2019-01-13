/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.io.Serializable;

/**
 *
 * @author Mohamed
 */
public class Player implements Serializable {

    private int PlayerID;
    private String PlayerUserName;
    private String PlayerName;
    private transient String PlayerPassword;
    private int PlayerScore;
    private String PlayerEmail;
    private String PlayerState;

    public int getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(int PlayerID) {
        this.PlayerID = PlayerID;
    }

    public String getPlayerUserName() {
        return PlayerUserName;
    }

    public void setPlayerUserName(String PlayerUserName) {
        this.PlayerUserName = PlayerUserName;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public String getPlayerPassword() {
        return PlayerPassword;
    }

    public void setPlayerPassword(String PlayerPassword) {
        this.PlayerPassword = PlayerPassword;
    }

    public int getPlayerScore() {
        return PlayerScore;
    }

    public void setPlayerScore(int PlayerScore) {
        this.PlayerScore = PlayerScore;
    }

    public String getPlayerEmail() {
        return PlayerEmail;
    }

    public void setPlayerEmail(String PlayerEmail) {
        this.PlayerEmail = PlayerEmail;
    }

    public String getPlayerState() {
        return PlayerState;
    }

    public void setPlayerState(String PlayerState) {
        this.PlayerState = PlayerState;
    }
}
