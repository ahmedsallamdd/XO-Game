
package model;

/**
 *
 * @author Sallam
 */
public class InGamePlayer {
    private String playerName;
    private int playerSymbol;
    private boolean isMyTurn;

    public boolean isIsMyTurn() {
        return isMyTurn;
    }

    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(int playerSymbol) {
        this.playerSymbol = playerSymbol;
    }
    
}
