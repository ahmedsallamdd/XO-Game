package commontxo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerCallBack extends Remote {

    //init //
    public ArrayList<PlayerList> initOnlineList() throws RemoteException;//if room!=null player:Busy

    void register(ClientCallBack clientRef, String playerUserName) throws RemoteException;

    //function inside signOut related to server not at this interfce //deprecated :D
//    void unRegister(String playerUserName) throws RemoteException;
    //game
    void sendGameRequest(String myUserName, String oppesiteUserName) throws RemoteException;

    public void refuseGameRequest(String myUserName, String oppesiteUserName) throws RemoteException;

    void startGameRoom(String myUserName, String oppesiteUserName) throws RemoteException;

    public void spectateGame(String myUserName, String oppesiteUserName) throws RemoteException;

    boolean notifiyGameResult(String roomName, String WinnerUserName) throws RemoteException;

    // Chat 
    void joinChatRoom(String myUserName, String playerUserName) throws RemoteException;

    public void leftChatRoom(String myUserName, String playerUserName) throws RemoteException;

    public void leaveServer(String myUserName) throws RemoteException;
    //control

    //Call RealTime change //any change
    public void signOut(Player player) throws RemoteException;

    public Player signIn(String userName, String password) throws RemoteException;

    public boolean signUp(String userName, String Name, String password, String Email) throws RemoteException;

    public boolean checkUserName(String userName) throws RemoteException;

    public void removeClientMapGameRoom(String userName) throws RemoteException;

    public void removePlayerFromGameRoom(String userName, String gameRoom) throws RemoteException;

}
