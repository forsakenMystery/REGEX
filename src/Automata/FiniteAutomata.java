/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automata;

import Obj.Pair;
import Obj.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Arrow.AArrow;

/**
 *
 * @author FORSAKEN MYSTERY
 */
public class FiniteAutomata {

    private ArrayList< Character> Alphabet;
    private State start;
    private ArrayList< State> end;
    private HashMap< Character, ArrayList< Pair< State, State>>> map;

    public FiniteAutomata(State start, ArrayList<State> end, char... Alphabet) {
        setAlphabet(Alphabet);
        setEnd(end);
        setStart(start);
        newMap();
    }

    public FiniteAutomata(State start, ArrayList<State> end, ArrayList<Character> p) {
        setAlphabet(p);
        setEnd(end);
        setStart(start);
        newMap();
    }

    public FiniteAutomata(State start, ArrayList<Character> p) {
        setAlphabet(p);
        end = new ArrayList<>();
        setStart(start);
        newMap();
    }

    public HashMap<Character, ArrayList<Pair<State, State>>> getMap() {
        return map;
    }

    protected void newMap() {
        map = new HashMap<>();
    }

    public void setMap(Character c, Pair<State, State>... ar) {
        ArrayList<Pair<State, State>> a = new ArrayList<>();
        for (Pair<State, State> item : ar) {
            a.add(item);
        }
        map.put(c, a);
    }

    public ArrayList<Character> getAlphabet() {
        return Alphabet;
    }

    public void setAlphabet(char... Alphabet) {
        this.Alphabet = new ArrayList<>();
        for (char item : Alphabet) {
            this.Alphabet.add(item);
        }
    }

    public ArrayList<State> getEnd() {
        return end;
    }

    public State getStart() {
        return start;
    }

    public void setEnd(ArrayList<State> end) {
        this.end = new ArrayList<>(end);
    }

    public void setStart(State start) {
        this.start = start;
    }

    ArrayList<State> move(State a, char c) {
        State k = null;
        ArrayList<State> kk = new ArrayList<>();
        ArrayList<Pair<State, State>> m = this.map.get(c);
        if (m != null) {
            for (int i = 0; i < m.size(); i++) {
                if (m.get(i).getKey().equals(a)) {
                    k = new State(m.get(i).getValue().isIsFinal(), m.get(i).getValue().getName());
                    kk.add(k);
                }
            }
        } else {
            kk = null;
        }
        return kk;
    }

    @Override
    public String toString() {
        return "\nFinite Automata = \n alphabet = " + Alphabet + "\n start at = " + start + "\n final states are = " + end + "\n and map is = " + map;
    }

    void setAlphabet(ArrayList<Character> p) {
        this.Alphabet = new ArrayList<>();
        for (char item : p) {
            this.Alphabet.add(item);
        }
    }

    public void niceDraw(Stage stage, String value) {
        stage.setTitle(value);
        stage.setWidth(1000);
        stage.setHeight(500);
        Group g = new Group();
        System.out.println("this = " + this);
        HashSet<State> st = new HashSet<>();
        for (Character c : Alphabet) {
            ArrayList<Pair<State, State>> get = map.get(c);
            if (get != null) {
                for (Pair<State, State> pair : get) {
                    st.add(pair.getKey());
                    st.add(pair.getValue());
                }
            }
        }
        ArrayList<State> stat = new ArrayList<>(st);
        int n = st.size();
        State[] states = new State[n];
        for (State s : stat) {
            states[s.getName()] = s;
        }
        ArrayList<Circle> circles = new ArrayList<>();
        int d = 1000 / n;
        int r = d / 4;
        for (int i = 0; i < n; i++) {
            Circle c = new Circle(i * d + r, stage.getHeight() - 2 * r, r);
            Text t = new Text(c.getCenterX(), c.getCenterY(), Integer.toString(i));
            t.setFill(Paint.valueOf("black"));
            t.setFont(new Font(20));
            if (states[i].isIsFinal()) {
                c.setFill(Paint.valueOf("red"));
                Circle m = new Circle(c.getCenterX(), c.getCenterY(), c.getRadius() - 5, Paint.valueOf("yellow"));
                g.getChildren().addAll(c, m);
                g.getChildren().add(t);
            } else {
                c.setFill(Paint.valueOf("blue"));
                g.getChildren().add(c);
                g.getChildren().add(t);
            }
            circles.add(c);
        }
        //curve between states(maped to circ and stat)
        for (Character c : Alphabet) {
            ArrayList<Pair<State, State>> get = map.get(c);
            Color color = new Color(Math.random(), Math.random(), Math.random(), Math.random());
            if (get != null) {
                for (Pair<State, State> go : get) {
                    State start = go.getKey();
                    State end = go.getValue();
                    //Labeled label = new Labeled(c, circles.get(start.getName()).getCenterX(), circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY(), g, color.darker().darker(), r);
                    //g.getChildren().add(new Circle(label.xx, label.yy, 10, Paint.valueOf("black")));
                    
                    Arrow label = new Arrow(c, circles.get(start.getName()).getCenterX(), circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY(), g, color.darker().darker(), r);
                    
//                    AArrow arrow=new AArrow(c, g);
//                    arrow.setStart(circles.get(start.getName()).getCenterX(), circles.get(start.getName()).getCenterY());
//                    arrow.setEnd(circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY());
//                    arrow.setHeadLength(5);
//                    arrow.setHeadRadius(5);
//                    arrow.setHeadWidth(5);
//                    arrow.draw();
                }
            }
        }
        Scene scene = new Scene(g);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void draw(Stage stage, String value) {
        stage.setTitle(value);
        stage.setWidth(1000);
        stage.setHeight(500);
        Group g = new Group();
        System.out.println("this = " + this);
        HashSet<State> st = new HashSet<>();
        for (Character c : Alphabet) {
            ArrayList<Pair<State, State>> get = map.get(c);
            if (get != null) {
                for (Pair<State, State> pair : get) {
                    st.add(pair.getKey());
                    st.add(pair.getValue());
                }
            }
        }
        ArrayList<State> stat = new ArrayList<>(st);
        int n = st.size();
        State[] states = new State[n];
        for (State s : stat) {
            states[s.getName()] = s;
        }
        ArrayList<Circle> circles = new ArrayList<>();
        int d = 1000 / n;
        int r = d / 4;
        for (int i = 0; i < n; i++) {
            Circle c = new Circle(i * d + r, stage.getHeight() - 2 * r, r);
            Text t = new Text(c.getCenterX(), c.getCenterY(), Integer.toString(i));
            t.setFill(Paint.valueOf("black"));
            t.setFont(new Font(20));
            if (states[i].isIsFinal()) {
                c.setFill(Paint.valueOf("red"));
                Circle m = new Circle(c.getCenterX(), c.getCenterY(), c.getRadius() - 5, Paint.valueOf("yellow"));
                g.getChildren().addAll(c, m);
                g.getChildren().add(t);
            } else {
                c.setFill(Paint.valueOf("blue"));
                g.getChildren().add(c);
                g.getChildren().add(t);
            }
            circles.add(c);
        }
        //curve between states(maped to circ and stat)
        for (Character c : Alphabet) {
            ArrayList<Pair<State, State>> get = map.get(c);
            Color color = new Color(Math.random(), Math.random(), Math.random(), Math.random());
            if (get != null) {
                for (Pair<State, State> go : get) {
                    State start = go.getKey();
                    State end = go.getValue();
                    Labeled label = new Labeled(c, circles.get(start.getName()).getCenterX(), circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY(), g, color.darker().darker(), r);
                    g.getChildren().add(new Circle(label.xx, label.yy, 10, Paint.valueOf("black")));
                    
                ////    Arrow label = new Arrow(c, circles.get(start.getName()).getCenterX(), circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY(), g, color.darker().darker(), r);
                    
//                    AArrow arrow=new AArrow(c, g);
//                    arrow.setStart(circles.get(start.getName()).getCenterX(), circles.get(start.getName()).getCenterY());
//                    arrow.setEnd(circles.get(end.getName()).getCenterX(), circles.get(end.getName()).getCenterY());
//                    arrow.setHeadLength(5);
//                    arrow.setHeadRadius(5);
//                    arrow.setHeadWidth(5);
//                    arrow.draw();
                }
            }
        }
        Scene scene = new Scene(g);
        stage.setScene(scene);
        stage.showAndWait();
    }
    

    private static class Labeled {

        String nn;
        double xx, yy;

        public Labeled(char n, double x1, double x2, double y, Group g, Paint f, double r) {
            this.nn = Character.toString(n);
            Path path;
            Text t;
            double j = Math.abs((x2 - x1) / 2);
            double x = (x1 + x2) / 2;
            x += Math.floor(Math.random() * r) - 3;
            y += Math.floor(Math.random() * r) - 3;
            if (n == 'λ') {
                path = drawSemiRing(x, y, j, j - 4, (Color) f, (Color) f);
                t = new Text(x, y - Math.abs((x2 - x1) / 2) + 22, nn);
                this.xx = x + j;
            } else {
                path = drawSemiRing(x, y, -j, -j + 4, (Color) f, (Color) f);
                t = new Text(x, y + Math.abs((x2 - x1) / 2) - 22, nn);
                this.xx = x + j;
            }
            t.setFont(new Font(20));
            t.setFill(f);
            g.getChildren().addAll(path, t);
            this.yy = y;
        }

        private Path drawSemiRing(double centerX, double centerY, double radius, double innerRadius, Color bgColor, Color strkColor) {
            Path path = new Path();
            path.setFill(bgColor);
            path.setStroke(strkColor);
            path.setFillRule(FillRule.EVEN_ODD);

            MoveTo moveTo = new MoveTo();
            moveTo.setX(centerX + innerRadius);
            moveTo.setY(centerY);

            ArcTo arcToInner = new ArcTo();
            arcToInner.setX(centerX - innerRadius);
            arcToInner.setY(centerY);
            arcToInner.setRadiusX(innerRadius);
            arcToInner.setRadiusY(innerRadius);

            MoveTo moveTo2 = new MoveTo();
            moveTo2.setX(centerX + innerRadius);
            moveTo2.setY(centerY);

            HLineTo hLineToRightLeg = new HLineTo();
            hLineToRightLeg.setX(centerX + radius);

            ArcTo arcTo = new ArcTo();
            arcTo.setX(centerX - radius);
            arcTo.setY(centerY);
            arcTo.setRadiusX(radius);
            arcTo.setRadiusY(radius);

            HLineTo hLineToLeftLeg = new HLineTo();
            hLineToLeftLeg.setX(centerX - innerRadius);

            path.getElements().add(moveTo);
            path.getElements().add(arcToInner);
            path.getElements().add(moveTo2);
            path.getElements().add(hLineToRightLeg);
            path.getElements().add(arcTo);
            path.getElements().add(hLineToLeftLeg);

            return path;
        }

    }

    private static class Arrow {

        private final String nn;

        Arrow(char n, double x1, double x2, double y, Group g, Paint f, double r) {
            this.nn = Character.toString(n);
            Text t;
            double j = Math.abs((x2 - x1) / 2);
            double x = (x1 + x2) / 2;
            // bending curve
            QuadCurve curve1 = null;
            if (n == 'λ') {
                curve1 = new QuadCurve(x1 - r, y, x, y+j, x2 + r, y);
                t = new Text(x, y + j-r, nn);
            } else {
                curve1 = new QuadCurve(x1 - r, y, x, y-j, x2 + r, y);
                t = new Text(x, y - j+r, nn);
            }
            curve1.setStroke(Color.BLACK);
            curve1.setStrokeWidth(3);
            curve1.setFill(null);

            double size = Math.max(curve1.getBoundsInLocal().getWidth(),
                    curve1.getBoundsInLocal().getHeight());
            double scale = size / 4d;

            Point2D ori = eval(curve1, 0);
            Point2D tan = evalDt(curve1, 0).normalize().multiply(scale);
            Path arrowIni = new Path();
            arrowIni.getElements().add(new MoveTo(ori.getX() + 0.2 * tan.getX() - 0.2 * tan.getY(),
                    ori.getY() + 0.2 * tan.getY() + 0.2 * tan.getX()));
            arrowIni.getElements().add(new LineTo(ori.getX(), ori.getY()));
            arrowIni.getElements().add(new LineTo(ori.getX() + 0.2 * tan.getX() + 0.2 * tan.getY(),
                    ori.getY() + 0.2 * tan.getY() - 0.2 * tan.getX()));

            ori = eval(curve1, 1);
            tan = evalDt(curve1, 1).normalize().multiply(scale);
            Path arrowEnd = new Path();
            arrowEnd.getElements().add(new MoveTo(ori.getX() - 0.2 * tan.getX() - 0.2 * tan.getY(),
                    ori.getY() - 0.2 * tan.getY() + 0.2 * tan.getX()));
            arrowEnd.getElements().add(new LineTo(ori.getX(), ori.getY()));
            arrowEnd.getElements().add(new LineTo(ori.getX() - 0.2 * tan.getX() + 0.2 * tan.getY(),
                    ori.getY() - 0.2 * tan.getY() - 0.2 * tan.getX()));

            t.setFont(new Font(20));
            t.setFill(f);
            g.getChildren().addAll(curve1, arrowEnd, t);
        }

        /**
         * Evaluate the cubic curve at a parameter 0<=t<=1, returns a Point2D
         * @param c t
         *
         *
         * he Cubic
         *
         * Curve
         * @param t param between 0 and 1
         * @return a Point2D
         */
        private Point2D eval(QuadCurve c, float t) {
            Point2D p = new Point2D(Math.pow(1 - t, 3) * c.getStartX()
                    + 3 * t * Math.pow(1 - t, 2) * c.getControlX()
                    + 3 * (1 - t) * t * t * c.getControlX()
                    + Math.pow(t, 3) * c.getEndX(),
                    Math.pow(1 - t, 3) * c.getStartY()
                    + 3 * t * Math.pow(1 - t, 2) * c.getControlY()
                    + 3 * (1 - t) * t * t * c.getControlY()
                    + Math.pow(t, 3) * c.getEndY());
            return p;
        }

        /**
         * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1,
         * returns a Point2D @param c the Cubic
         *
         * Curve @param t param between 0 and 1 @return a Point2D
         *
         */
        private Point2D evalDt(QuadCurve c, float t) {
            Point2D p = new Point2D(-3 * Math.pow(1 - t, 2) * c.getStartX()
                    + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlX()
                    + 3 * ((1 - t) * 2 * t - t * t) * c.getControlX()
                    + 3 * Math.pow(t, 2) * c.getEndX(),
                    -3 * Math.pow(1 - t, 2) * c.getStartY()
                    + 3 * (Math.pow(1 - t, 2) - 2 * t * (1 - t)) * c.getControlY()
                    + 3 * ((1 - t) * 2 * t - t * t) * c.getControlY()
                    + 3 * Math.pow(t, 2) * c.getEndY());
            return p;
        }

    }

}
