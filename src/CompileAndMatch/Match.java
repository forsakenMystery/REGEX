/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompileAndMatch;

import Automata.FiniteAutomata;
import Obj.Pair;
import Obj.State;
import java.util.ArrayList;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class Match {

    private FiniteAutomata dfa;
    private String str;

    public Match(FiniteAutomata dfa, String s) {
        this.dfa = dfa;
        this.str = s;
    }

    public boolean matching() {
        boolean t = true;
        char c;
        int j = 0;
        ArrayList<Pair<State, State>> m = null;
        State s = new State(dfa.getStart().isIsFinal(), dfa.getStart().getName());
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            try {
                m = (ArrayList<Pair<State, State>>) dfa.getMap().get(c).clone();
            } catch (Exception e) {

            }
            if (m==null||m.size() == 0) {
                return false;
            }
            for (j = 0; j < m.size(); j++) {
                if (m.get(j).getKey().equals(s)) {
                    s.setIsFinal(m.get(j).getValue().isIsFinal());
                    s.setName(m.get(j).getValue().getName());
                    m.clear();
                    break;
                } else if (j == m.size() - 1) {
                    t = false;
                }
            }
        }
        if (!s.isIsFinal()) {
            t = false;
        }
        return t;
    }
}
