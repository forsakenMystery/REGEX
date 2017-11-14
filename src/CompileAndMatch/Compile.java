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
import java.util.HashSet;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class Compile {

    protected FiniteAutomata ans;
    private String kek;
    private int lastState, startState;
    private State[] help, temp;

    public FiniteAutomata getAns() {
        return ans;
    }

    public Compile(String kek) {
        this.kek = kek;
        lastState = 0;
        startState = 0;
        HashSet<Character> a = new HashSet<>();
        for (int i = 0; i < kek.length(); i++) {
            if (notOp(kek.charAt(i))) {
                a.add(kek.charAt(i));
            }
        }
        ArrayList<Character> alphabet = new ArrayList<>(a);
        alphabet.add('λ');
        ans = new FiniteAutomata(new State(false, lastState), alphabet);
        compile(kek);
    }

    private void compile(String s) {
        help = new State[lastState + 1];
        for (int i = 0; i < help.length; i++) {
            help[i] = new State(false, i);
        }
        procedure(s, 0);
    }

    private void procedure(String s, int flag) {
        int start = lastState;
        int end = lastState;
        boolean or = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                break;
            }
            if (s.charAt(i) == '|') {
                or = true;
            }
        }
        if (or) {
            lastState++;
            end = lastState;
            temp = new State[lastState + 1];
            System.arraycopy(help, 0, temp, 0, help.length);
            temp[lastState] = new State(false, lastState);
            help = new State[lastState + 1];
            System.arraycopy(temp, 0, help, 0, temp.length);
            ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
            if (k == null) {
                k = new ArrayList<>();
            }
            k.add(new Pair<>(help[start], help[help.length - 1]));
            or = true;
            ans.getMap().put('λ', k);
        }
        ArrayList<Integer> orSrc = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (notOp(s.charAt(i))) {
                lastState++;
                end = lastState;
                temp = new State[lastState + 1];
                System.arraycopy(help, 0, temp, 0, help.length);
                temp[lastState] = new State(false, lastState);
                help = new State[lastState + 1];
                System.arraycopy(temp, 0, help, 0, temp.length);
                ArrayList<Pair<State, State>> k = ans.getMap().get(s.charAt(i));
                if (k == null) {
                    k = new ArrayList<>();
                }
                k.add(new Pair<>(help[help.length - 2], help[help.length - 1]));
                ans.getMap().put(s.charAt(i), k);
            } else if (s.charAt(i) == '|') {
                lastState++;
                end = lastState;
                temp = new State[lastState + 1];
                System.arraycopy(help, 0, temp, 0, help.length);
                temp[lastState] = new State(false, lastState);
                help = new State[lastState + 1];
                System.arraycopy(temp, 0, help, 0, temp.length);
                ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
                if (k == null) {
                    k = new ArrayList<>();
                }
                k.add(new Pair<>(help[start], help[help.length - 1]));
                orSrc.add(help.length - 2);
                ans.getMap().put('λ', k);
            } else if (s.charAt(i) == '*') {
                lastState++;
                end = lastState;
                temp = new State[lastState + 1];
                System.arraycopy(help, 0, temp, 0, help.length);
                temp[lastState] = new State(false, lastState);
                help = new State[lastState + 1];
                System.arraycopy(temp, 0, help, 0, temp.length);
                ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
                if (k == null) {
                    k = new ArrayList<>();
                }
                k.add(new Pair<>(help[help.length - 3], help[help.length - 1]));
                k.add(new Pair<>(help[help.length - 2], help[help.length - 3]));
                ans.getMap().put('λ', k);
            } else if (s.charAt(i) == '+') {
                ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
                if (k == null) {
                    k = new ArrayList<>();
                }
                k.add(new Pair<>(help[help.length - 1], help[help.length - 2]));
                ans.getMap().put('λ', k);
            } else if (s.charAt(i) == '(') {
                String temp = "";
                int count = 1;
                int j;
                for (j = i + 1; j < s.length(); j++) {
                    if (s.charAt(j) == '(') {
                        count++;
                    }
                    if (s.charAt(j) == ')') {
                        count--;
                    }
                    if (count == 0) {
                        break;
                    }
                    temp += s.charAt(j);
                }
                if (j + 1 < s.length()) {
                    if (s.charAt(j + 1) == '+') {
                        procedure(temp, 2);
                    } else if (s.charAt(j + 1) == '*') {
                        procedure(temp, 1);
                    } else {
                        procedure(temp, 0);
                    }
                    ans.getEnd().clear();
                    help[lastState].setIsFinal(false);
                    i = j + 1;
                    i--;
                }
            }
        }
        if (or) {
            lastState++;
            end = lastState;
            temp = new State[lastState + 1];
            System.arraycopy(help, 0, temp, 0, help.length);
            temp[lastState] = new State(false, lastState);
            help = new State[lastState + 1];
            System.arraycopy(temp, 0, help, 0, temp.length);
            ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
            if (k == null) {
                k = new ArrayList<>();
            }
            k.add(new Pair<State, State>(help[help.length - 2], help[help.length - 1]));
            while (!orSrc.isEmpty()) {
                k.add(new Pair<State, State>(help[orSrc.remove(0)], help[help.length - 1]));
            }
            ans.getMap().put('λ', k);
        }
        if (flag == 1) {
            lastState++;
            end = lastState;
            temp = new State[lastState + 1];
            System.arraycopy(help, 0, temp, 0, help.length);
            temp[lastState] = new State(false, lastState);
            help = new State[lastState + 1];
            System.arraycopy(temp, 0, help, 0, temp.length);
            ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
            if (k == null) {
                k = new ArrayList<>();
            }
            k.add(new Pair<>(help[start], help[help.length - 1]));
            k.add(new Pair<>(help[help.length - 2], help[start]));
            ans.getMap().put('λ', k);
        } else if (flag == 2) {
            ArrayList<Pair<State, State>> k = ans.getMap().get('λ');
            if (k == null) {
                k = new ArrayList<>();
            }
            k.add(new Pair<>(help[end], help[start]));
            ans.getMap().put('λ', k);
        }
        help[help.length - 1].setIsFinal(true);
        ArrayList<State> f = ans.getEnd();
        if (f == null) {
            f = new ArrayList<State>();
        }
        f.add(help[help.length - 1]);
        ans.setEnd(f);
    }

    private boolean notOp(char charAt) {
        return charAt != '*' && charAt != '+' && charAt != '|' && charAt != '.' && charAt != '(' && charAt != ')';
    }

}
