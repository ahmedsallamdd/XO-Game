/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Abdo Amin
 */
public interface ClientCallBack extends Remote {

    //game init
    public void sendGameNotification(String oppesiteUserName) throws RemoteException;

    public void acceptGameRequest(String oppesiteUserName) throws RemoteException;

//    public void refuseGameRequest(String oppesiteUserName) throws RemoteException;
    public void joinGameRoom(String roomName, ClientCallBack creatorClient) throws RemoteException;

    public void addPlayerToGameRoom(String playerUserName, ClientCallBack player) throws RemoteException;

    public void setGameState(GameState gameState) throws RemoteException;

    public GameState getGameState() throws RemoteException;

    public void startGame(String mode) throws RemoteException;

    public void leaveGameRoom(String winner) throws RemoteException;

    //chat
    public void joinChatRoom(String targetUserName, ClientCallBack targetClient) throws RemoteException;

    public void leftChatRoom(String userNameWhoLeft) throws RemoteException;

    public void leftGameRoom(String userNameWhoLeft) throws RemoteException;

    //control game
    public void play(int position) throws RemoteException;

    //control chat
    public void receiveMessage(String senderUserName, String message) throws RemoteException;//filter yourseelf and send to yourself as friend room

    public void sendMessage(String senderName, String message) throws RemoteException;

    //realTime Response // just a bouns feature
    void notifiyOnlineList(String playerUserName,String state) throws RemoteException;

    public void showAlert(String title, String headerText, String message) throws RemoteException;

    public void closeAllAlert() throws RemoteException;

    //server
    public void serverUnavilable() throws RemoteException;

}
