package fr.ufrst.m1info.pvm.group5.driver;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 */
public class IndentationGuideCanvas extends Canvas {

    private static final Color GUIDE_COLOR = Color.web("#4a4a4a");
    private String text = "";

    /**
     * Redraws when the canvas change size
     */
    public IndentationGuideCanvas() {
        // Redraw when canvas size changes
        widthProperty().addListener(obs -> redraw());
        heightProperty().addListener(obs -> redraw());
    }

    /**
     * Updates the text and redraws the indentation guides
     *
     * @param text the text to analyze for indentation levels
     */
    public void setText(String text) {
        this.text = text != null ? text : "";
        redraw();
    }

    /**
     * Counts the number of tab characters in the text
     *
     * @param text the text to count tabs
     * @return the number of consecutive tab characters at the start of the text
     */
    private int countLeadingTabs(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == '\t') {
                count++;
            } else {
                break; // First char that is not a tab we stop
            }
        }
        return count;
    }

    /**
     * Redraws the canvas and draws one ine for each indentation level
     */
    private void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        int indentLevel = countLeadingTabs(text);
        if (indentLevel == 0) {
            return; // No indentation, no guides to draw
        }

        gc.setStroke(GUIDE_COLOR);
        gc.setLineWidth(1.0);

        // Draw one vertical line per indentation level
        double x = 37;
        for(int i = 1; i < indentLevel; i++) {
            gc.strokeLine(x, 0, x, getHeight());
            x = x + 27;
        }
    }
}