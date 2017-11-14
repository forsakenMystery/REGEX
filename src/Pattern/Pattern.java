/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pattern;

import Automata.FiniteAutomata;
import Automata.NfaToDfa;
import CompileAndMatch.Compile;
import CompileAndMatch.Match;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class Pattern {

    private String still = null;
    private FiniteAutomata nfa;
    private FiniteAutomata dfa;
    private Compile compile;
    private NfaToDfa ntod;
    private Match match;

    public boolean match(String regex, String sequence) {
        if (!regex.equals(still)) {
            compile = new Compile(regex);
            nfa = compile.getAns();
            ntod = new NfaToDfa(nfa);
            dfa = ntod.getDfa();
        }
        match = new Match(dfa, sequence);
        return match.matching();
    }

    public FiniteAutomata getDfa(String regex) {
        compile = new Compile(regex);
        nfa = compile.getAns();
        ntod = new NfaToDfa(nfa);
        dfa = ntod.getDfa();
        return dfa;
    }

    public FiniteAutomata getNfa(String regex) {
        compile = new Compile(regex);
        nfa = compile.getAns();
        return nfa;
    }

}
