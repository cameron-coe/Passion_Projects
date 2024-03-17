package main.java.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawGraphics {

    /*******************************************************************************************************************
     *Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);


    /*******************************************************************************************************************
     * Instance Variables
     */
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics2D;
    private JFrame jFrame;
    private List<GuiShape> shapesToDraw;


    /*******************************************************************************************************************
     * Constructor
     */
    public DrawGraphics(BufferedImage bufferedImage, JFrame jFrame, List<GuiShape> shapesToDraw) {
        this.bufferedImage = bufferedImage;
        this.bufferedGraphics2D = (Graphics2D) bufferedImage.getGraphics();
        this.jFrame = jFrame;
        this.shapesToDraw = shapesToDraw;
    }


    /*******************************************************************************************************************
     * Getter
     */
    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }


    /*******************************************************************************************************************
     * Render - applies list of shapes to the Graphics
     */
    public void prepareBufferedGraphic() {
        // Enable antialiasing - makes images smoother and less jagged
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferedGraphics2D.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        drawBackground();
        drawAllShapes();
    }


    /*******************************************************************************************************************
     * Draw Shapes
     */
    private void drawAllShapes() {
        for (GuiShape shape : shapesToDraw) {
            if (shape.getShapeId() == GuiShape.RECTANGLE) {
                drawRectangle(shape);
            }
            else if (shape.getShapeId() == GuiShape.ELLIPSE) {
                drawEllipse(shape);
            }
            else if (shape.getShapeId() == GuiShape.ROUNDED_RECTANGLE) {
                drawRoundedRectangle(shape);
            }
            else if (shape.getShapeId() == GuiShape.LINE) {
                drawLine(shape);
            }
            else if (shape.getShapeId() == GuiShape.TEXT_BOX) {
                drawTextBox(shape);
            }
            else if (shape.getShapeId() == GuiShape.TEXT_BOX_LINES) {
                drawTextBoxLines(shape);
            }

        }
    }

    private void drawBackground() {
        bufferedGraphics2D.setColor(BACKGROUND_COLOR);

        int width = jFrame.getWidth();
        int height = jFrame.getHeight();
        bufferedGraphics2D.fillRect(0, 0, width, height);
    }


    private void drawRectangle(GuiShape shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int width = shape.getPoint2X() - shape.getPoint1X();
        int height = shape.getPoint2Y() - shape.getPoint1Y();

        // Creates the shape Fill
        bufferedGraphics2D.setColor( shape.getFillColor() );
        bufferedGraphics2D.fillRect(startX, startY, width, height);

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawRect(startX, startY, width, height);
    }

    private void drawEllipse(GuiShape shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int width = shape.getPoint2X() - shape.getPoint1X();
        int height = shape.getPoint2Y() - shape.getPoint1Y();

        // Creates the shape Fill
        bufferedGraphics2D.setColor( shape.getFillColor() );
        bufferedGraphics2D.fillOval(startX, startY, width, height);

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawOval(startX, startY, width, height);
    }

    private void drawRoundedRectangle(GuiShape shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int width = shape.getPoint2X() - shape.getPoint1X();
        int height = shape.getPoint2Y() - shape.getPoint1Y();
        int arc = shape.getArc();

        // Creates the shape Fill
        bufferedGraphics2D.setColor( shape.getFillColor() );
        bufferedGraphics2D.fillRoundRect(startX, startY, width, height, arc, arc);

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawRoundRect(startX, startY, width, height, arc, arc);
    }

    private void drawLine(GuiShape shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int endX = shape.getPoint2X();
        int endY = shape.getPoint2Y();

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawLine(startX, startY, endX, endY);
    }

    private void drawTextBox (GuiShape shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int width = shape.getPoint2X() - shape.getPoint1X();
        String text = shape.getText();

        // Set scaling factors
        double scaleX = shape.getTextScaleX(); // Change this value as needed
        double scaleY = 1; // Keep the scale factor for y-axis as 1

        // Get metrics about the font
        FontMetrics fontMetrics = bufferedGraphics2D.getFontMetrics();

        // Get the height of the text
        int textHeight = fontMetrics.getAscent() - fontMetrics.getDescent();

        // Lowers text to be inside the box
        startY += textHeight;

        // Set the text Fill Color
        bufferedGraphics2D.setColor( shape.getTextFillColor() );

        // Wrap Text
        String[] words = text.split(" ");
        String currentLine = "";

        for (String word : words) {
            // Check if adding this word makes the current line exceed the maximum width
            // I'm adding two spaces because it checks for a line slightly longer than the actual line,
            // that prevents flickering text
            if (fontMetrics.stringWidth(currentLine + " " + " " + word) > width) {

                // If the line without the added word is smaller than the box width, draw it
                if (fontMetrics.stringWidth(currentLine) <= width) {

                    //Center the lines (And adjusts for scaling)
                    double lineStartX = ((double) startX) / scaleX;
                    lineStartX -= ((double) fontMetrics.stringWidth(currentLine)) / 2;
                    lineStartX += (width/scaleX) / 2;

                    // Apply scaling to the graphics object
                    bufferedGraphics2D.scale(scaleX, scaleY);

                    // Draw the current line
                    bufferedGraphics2D.drawString(currentLine, (int) lineStartX, startY);

                    // Undo Scaling
                    bufferedGraphics2D.scale(1/scaleX, 1/scaleY);

                    // Increase startY to move down the next currentLine
                    // only if the line drawn had text
                    if (!currentLine.trim().isEmpty()) {
                        startY += fontMetrics.getHeight();
                    }

                    // Clear the current line
                    currentLine = "";

                    // get bottom of the last line
                    int textBottom = startY - fontMetrics.getAscent();
                    textBottom += fontMetrics.getHeight();

                    // Don't show lines that go past the bottom bound
                    if (textBottom > shape.getPoint2Y()) {
                        break;
                    }

                }
            }

            // Add the word to the line
            currentLine += " " + word;
        }

        // Draws the last line if there's enough room in the box for it
        if (fontMetrics.stringWidth(currentLine) < width) {
            //Center the lines (And adjusts for scaling)
            double lineStartX = ((double) startX) / scaleX;
            lineStartX -= (fontMetrics.stringWidth(currentLine)) / 2;
            lineStartX += (width/scaleX) / 2;

            // Apply scaling to the graphics object
            bufferedGraphics2D.scale(scaleX, scaleY);

            // Draw the final Line
            bufferedGraphics2D.drawString(currentLine, (int) lineStartX, startY);

            // Undo Scaling
            bufferedGraphics2D.scale(1/scaleX, 1/scaleY);
        }
    }

    private void drawTextBoxLines(GuiShape shape) {
        int startY = shape.getPoint1Y();
        int endY = shape.getPoint2Y();

        // Get metrics about the font
        FontMetrics fontMetrics = bufferedGraphics2D.getFontMetrics();
        int fontHeight = fontMetrics.getHeight();

        // Make the lines start at the bottom of the text
        startY += fontMetrics.getAscent();

        for (int i = startY; i < endY; i += fontHeight) {
            shape.setPoint1Y(i);
            shape.setPoint2Y(i);
            drawLine(shape);
        }
    }

}
