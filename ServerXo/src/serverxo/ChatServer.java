/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverxo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdo Amin
 */
public class ChatServer {

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        try {
            ServerMessageImp obj = null;
            try {
                obj = new ServerMessageImp();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("GameService", obj);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
