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
public class GameState implements Serializable {

    String inGamePlayer0;
    String inGamePlayer1;
    int[] positions;
    int activePlayer;

    public GameState(String inGamePlayer0, String inGamePlayer1, int[] positions, int activePlayer) {
        this.inGamePlayer0 = inGamePlayer0;
        this.inGamePlayer1 = inGamePlayer1;
        this.positions = positions;
        this.activePlayer = activePlayer;
    }

    public String getInGamePlayer0() {
        return inGamePlayer0;
    }

    public String getInGamePlayer1() {
        return inGamePlayer1;
    }

    public int[] getPositions() {
        return positions;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setInGamePlayer0(String inGamePlayer0) {
        this.inGamePlayer0 = inGamePlayer0;
    }

    public void setInGamePlayer1(String inGamePlayer1) {
        this.inGamePlayer1 = inGamePlayer1;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

}
