/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Obj.Pair;
import Obj.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class NfaToDfa {

    int kkk = 0;
    private FiniteAutomata dfa;
    private FiniteAutomata nfa;
    HashMap<HashSet<State>, State> map;
    HashMap<State, HashSet<State>> reverceMap;

    public NfaToDfa(FiniteAutomata nfa) {
        this.nfa = nfa;
        map = new HashMap<>();
        reverceMap = new HashMap<>();
    }

    private State landaCloser(ArrayList<State> state) {//get him clone λ
//        System.out.println("LANDA CLOSURE :\n");
        State s = null, k = null;
        boolean isAccept = false;
        Stack<State> stack = new Stack<>();
        HashSet<State> temp = new HashSet<>(state);
        for (int i = 0; i < state.size(); i++) {
            stack.push(state.get(i));
        }
        while (!stack.empty()) {
            k = stack.pop();
            isAccept = isAccept || k.isIsFinal();
            ArrayList<State> m = nfa.move(k, 'λ');
            if (m != null) {
                for (int i = 0; i < m.size(); i++) {
                    State f = m.get(i);
                    if (!temp.contains(f)) {
                        temp.add(f);
                        stack.push(f);
                    }
                }
            } else {
                temp.add(new State(false, Integer.MAX_VALUE));
            }
        }
        if (map.containsKey(temp)) {
            s = new State(isAccept, map.get(temp).getName());
        } else {
            map.put(temp, new State(isAccept, kkk++));
            reverceMap.put(new State(isAccept, kkk - 1), temp);
            s = new State(isAccept, map.get(temp).getName());
            if (isAccept) {
                dfa.getEnd().add(s);
            }
        }
//        System.out.println("state = " + state);
//        System.out.println("s = " + s);
//        System.out.println("temp = " + temp);
//        System.out.println("============================================");
        return s;
    }

    protected void makeDfa() {
//        System.out.println("make DFA :\n");
        ArrayList<State> a = new ArrayList<>();
        a.add(nfa.getStart());
        ArrayList<Character> p = new ArrayList<>();
        for (int i = 0; i < nfa.getAlphabet().size(); i++) {
            if (!nfa.getAlphabet().get(i).equals('λ')) {
                p.add(nfa.getAlphabet().get(i));
            }
        }
        dfa = new FiniteAutomata(null, p);
        State start = landaCloser(a);
        dfa.setStart(start);
        ArrayList<HashSet<State>> DState = new ArrayList<>();
        HashSet<State> ko = reverceMap.get(start);
        DState.add(ko);
        Stack<HashSet<State>> forCheck = new Stack<>();
        forCheck.push(ko);
        while (!forCheck.empty()) {
            HashSet<State> initial = forCheck.pop();
            for (int j = 0; j < dfa.getAlphabet().size(); j++) {
                HashSet<State> temp = new HashSet<>();
//                System.out.println("forCheck = " + forCheck);
//                System.out.println("initial = " + initial);
                State[] set = new State[initial.size()];
                initial.toArray(set);
//                System.out.println("set = " + Arrays.toString(set));
//                System.out.println("dfa.getAlphabet().get(j) = " + dfa.getAlphabet().get(j));
//                System.out.println("--------------------------------\n");
                for (int i = 0; i < set.length; i++) {
                    temp.addAll(nfa.move(set[i], dfa.getAlphabet().get(j)));
                }
                ArrayList<State> lan = new ArrayList<>();
                for (int i = 0; i < temp.toArray().length; i++) {
                    lan.add((State) temp.toArray()[i]);
                }
//                System.out.println("lan = " + lan);
                State destination = landaCloser(lan);
                temp = this.reverceMap.get(destination);
//                System.out.println("destination = " + destination);
//                System.out.println("temp = " + temp);
                if (!DState.contains(temp)) {
                    DState.add(temp);
                    forCheck.push(temp);
                }
                ArrayList<Pair<State, State>> f = dfa.getMap().get(dfa.getAlphabet().get(j));
                if (f != null) {
                    f.add(new Pair<State, State>(this.map.get(initial), this.map.get(temp)));
                } else {
                    f = new ArrayList<>();
                    f.add(new Pair<State, State>(this.map.get(initial), this.map.get(temp)));
                }
                dfa.getMap().put(dfa.getAlphabet().get(j), f);
//                System.out.println("DState = " + DState);
//                System.out.println("forCheck = " + forCheck);
//                System.out.println("temp = " + temp);
//                System.out.println("dfa.getMap() = " + dfa.getMap());
//                System.out.println("===========================================");
            }
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++\n+++++++++++++++++++++++++++++++++++++++++\n");
        }
    }

    public FiniteAutomata getDfa() {
        makeDfa();
        return dfa;
    }

    @Override
    public String toString() {
        return "nfa = " + nfa + "\ndfa = " + dfa; //To change body of generated methods, choose Tools | Templates.
    }

}
