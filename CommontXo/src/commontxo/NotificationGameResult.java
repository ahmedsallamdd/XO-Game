/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Abdo Amin
 */
public interface NotificationGameResult extends Remote, Serializable {

    public void onReturn(boolean accept) throws RemoteException;
}
