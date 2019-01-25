/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commontxo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author Abdo Amin
 */
public class Utilites {

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Iterator<Entry<T, E>> it = map.entrySet().iterator(); it.hasNext();) {
            Entry<T, E> entry = it.next();
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
