package Arrow;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * An arrow shape.
 *
 * <p>
 * This is a {@link Node} subclass and can be added to the JavaFX scene graph in
 * the usual way. Styling can be achieved via the CSS classes
 * <em>arrow-line</em> and <em>arrow-head</em>.
 * <p>
 *
 * <p>
 * Example:
 *
 * <pre>
 * <code>Arrow arrow = new Arrow();
 * arrow.setStart(10, 20);
 * arrow.setEnd(100, 150);
 * arrow.draw();</code>
 * </pre>
 *
 * </p>
 *
 */
public class AArrow {

    private static final double HALF_A_PIXEL = 0.5;

    private static final String STYLE_CLASS_LINE = "arrow-line";
    private static final String STYLE_CLASS_HEAD = "arrow-head";
    Text t = new Text();
    private final Line line = new Line();
    private final ArrowHead head = new ArrowHead();

    private double startX;
    private double startY;

    private double endX;
    private double endY;

    /**
     * Creates a new {@link Arrow}.
     */
    public AArrow(char c, Group g) {

        line.getStyleClass().add(STYLE_CLASS_LINE);
        head.getStyleClass().add(STYLE_CLASS_HEAD);
        t.setText(c + "");
        
        t.setFont(new Font(20));
        t.setFill(Color.BLACK);
        g.getChildren().addAll(line, head,t);
    }

    /**
     * Sets the width of the arrow-head.
     *
     * @param width the width of the arrow-head
     */
    public void setHeadWidth(final double width) {
        head.setWidth(width);
    }

    /**
     * Sets the length of the arrow-head.
     *
     * @param length the length of the arrow-head
     */
    public void setHeadLength(final double length) {
        head.setLength(length);
    }

    /**
     * Sets the radius of curvature of the {@link ArcTo} at the base of the
     * arrow-head.
     *
     * <p>
     * If this value is less than or equal to zero, a straight line will be
     * drawn instead. The default is -1.
     * </p>
     *
     * @param radius the radius of curvature of the arc at the base of the
     * arrow-head
     */
    public void setHeadRadius(final double radius) {
        head.setRadiusOfCurvature(radius);
    }

    /**
     * Gets the start point of the arrow.
     *
     * @return the start {@link Point2D} of the arrow
     */
    public Point2D getStart() {
        return new Point2D(startX, startY);
    }

    /**
     * Sets the start position of the arrow.
     *
     * @param startX the x-coordinate of the start position of the arrow
     * @param startY the y-coordinate of the start position of the arrow
     */
    public void setStart(final double startX, final double startY) {
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * Gets the start point of the arrow.
     *
     * @return the start {@link Point2D} of the arrow
     */
    public Point2D getEnd() {
        return new Point2D(endX, endY);
    }

    /**
     * Sets the end position of the arrow.
     *
     * @param endX the x-coordinate of the end position of the arrow
     * @param endY the y-coordinate of the end position of the arrow
     */
    public void setEnd(final double endX, final double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * Draws the arrow for its current size and position values.
     */
    public void draw() {

        final double deltaX = endX - startX;
        final double deltaY = endY - startY;

        final double angle = Math.atan2(deltaX, deltaY);

        final double headX = endX - head.getLength() / 2 * Math.sin(angle);
        final double headY = endY - head.getLength() / 2 * Math.cos(angle);

        line.setStartX(moveOffPixel(startX));
        line.setStartY(moveOffPixel(startY));
        line.setEndX(moveOffPixel(headX));
        line.setEndY(moveOffPixel(headY));
        t.setX(headX+HALF_A_PIXEL);
        t.setY(headX+HALF_A_PIXEL);
        head.setCenter(headX, headY);
        head.setAngle(Math.toDegrees(-angle));
        head.draw();
    }

    public static double moveOffPixel(final double position) {
        return Math.ceil(position) - HALF_A_PIXEL;
    }
}
