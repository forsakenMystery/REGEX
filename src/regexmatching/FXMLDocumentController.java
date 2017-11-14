/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regexmatching;

import Automata.FiniteAutomata;
import Pattern.Pattern;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class FXMLDocumentController implements Initializable {

    Pattern pattern;
    @FXML
    private Button nfa;
    @FXML
    private Button dfa;
    @FXML
    private Button match;
    @FXML
    private Button reset;
    @FXML
    private Button exit;
    @FXML
    private TextField regexNfa;
    @FXML
    private TextField regexDfa;
    @FXML
    private TextField regexMatch;
    @FXML
    private TextField sequence;
    @FXML
    private TextArea answer;
    @FXML
    private Button NF;
    @FXML
    private Button DF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pattern = new Pattern();
        answer.setEditable(false);
        regexDfa.setText(null);
        regexNfa.setText(null);
        regexMatch.setText(null);
        sequence.setText(null);
    }

    @FXML
    private void NFACLICKED(MouseEvent event) {
        String regex = regexNfa.getText();
        if (regex == null) {
            new Alert(Alert.AlertType.ERROR, "you choosed nothing as your regular expression ", ButtonType.CLOSE).showAndWait();
        } else {
            FiniteAutomata nfa = pattern.getNfa(regex);
            nfa.niceDraw(new Stage(), "nfa");
        }
    }

    @FXML
    private void DFACLICKED(MouseEvent event) {
        String regex = regexDfa.getText();
        if (regex == null) {
            new Alert(Alert.AlertType.ERROR, "you choosed nothing as your regular expression ", ButtonType.CLOSE).showAndWait();
        } else {
            FiniteAutomata dfa = pattern.getDfa(regex);
            dfa.niceDraw(new Stage(), "dfa");
        }
    }

    @FXML
    private void matchClicked(MouseEvent event) {
        String regex = regexMatch.getText();
        String sequ = sequence.getText();
        if (regex == null || sequ == null) {
            new Alert(Alert.AlertType.ERROR, "you choosed nothing as your regular expression/sequence ", ButtonType.CLOSE).showAndWait();
            regex = "";
        } else {
            if (pattern.match(regex, sequ)) {
                answer.setText(sequ + (char) 8_712 + regex);
            } else {
                answer.setText(sequ + (char) 8_713 + regex);
            }
        }
    }

    @FXML
    private void resetClicked(MouseEvent event) {
        regexDfa.setText(null);
        regexNfa.setText(null);
        regexMatch.setText(null);
        sequence.setText(null);
    }

    @FXML
    private void exitClicked(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void NFAnCLICKED(MouseEvent event) {
        String regex = regexNfa.getText();
        if (regex == null) {
            new Alert(Alert.AlertType.ERROR, "you choosed nothing as your regular expression ", ButtonType.CLOSE).showAndWait();
        } else {
            FiniteAutomata nfa = pattern.getNfa(regex);
            nfa.draw(new Stage(), "nfa");
        }
    }

    @FXML
    private void DFAnCLICKED(MouseEvent event) {
        String regex = regexDfa.getText();
        if (regex == null) {
            new Alert(Alert.AlertType.ERROR, "you choosed nothing as your regular expression ", ButtonType.CLOSE).showAndWait();
        } else {
            FiniteAutomata dfa = pattern.getDfa(regex);
            dfa.draw(new Stage(), "dfa");
        }
    }

}
