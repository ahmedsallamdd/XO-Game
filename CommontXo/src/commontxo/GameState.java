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
public class GameState implements Serializable{
    int[] positions ;
     int activePlayer ;

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public GameState(int[] positions, int activePlayer) {
        this.positions = positions;
        this.activePlayer = activePlayer;
    }
    
}
