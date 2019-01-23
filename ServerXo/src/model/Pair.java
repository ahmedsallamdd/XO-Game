/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Abdo Amin
 */
public class Pair<K> {

    K first;
    K secoend;

    public Pair(K first, K secoend) {
        this.first = first;
        this.secoend = secoend;
    }

    public K getFirst() {
        return first;
    }

    public K getSecoend() {
        return secoend;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecoend(K secoend) {
        this.secoend = secoend;
    }

    public boolean equals(K one, K two) {
        return (one.equals(first) || one.equals(secoend)) && (two.equals(first) || two.equals(secoend));
    }

    @Override
    public boolean equals(Object obj) {
        return (((Pair) obj).first.equals(first) || ((Pair) obj).first.equals(secoend))
                && (((Pair) obj).secoend.equals(first) || ((Pair) obj).secoend.equals(secoend));
    }
}
