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
public interface NotificationGameResult extends Serializable {
    public void onReturn(boolean accept);
}
