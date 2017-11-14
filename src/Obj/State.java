/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obj;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class State {

    private boolean isFinal;
    private int name;

    public State(boolean isFinal, int name) {
        this.isFinal = isFinal;
        this.name = name;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public int getName() {
        return name;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "(name = " + this.name + " , is Final = " + isFinal + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof State) {
            State state = (State) o;
            if (this.name == state.name && this.isFinal == state.isFinal) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.isFinal ? 1 : 0);
        hash = 37 * hash + this.name;
        return hash;
    }

}
