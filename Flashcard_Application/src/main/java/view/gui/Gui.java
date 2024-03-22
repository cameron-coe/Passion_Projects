package main.java.view.gui;

import main.java.view.gui.shapes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {

    /*******************************************************************************************************************
     *Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);
    private final GuiManager guiManager = new GuiManager(this);
    private final JFrame currentJFrame = this;


    /*******************************************************************************************************************
     * Instance Variables
     */
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics2D;
    private List<GuiShapeDataObject> shapesToDraw;

    private int mouseX = 0;
    private int mouseY = 0;


    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        this.shapesToDraw = new ArrayList<>();
        windowSettings();
        addListeners();

        guiManager.runtimeStartEvent(this);
    }

    /*******************************************************************************************************************
     * JFrame Window Settings
     */
    private void windowSettings() {
        // JFrame is extended, so it's implied for these commands
        setTitle("The Flashcard App");
        setIconImage(loadImageFromImagesDirectory("AppIcon.png"));
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create image buffer for jFrame
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        setVisible(true);

    }

    private Image loadImageFromImagesDirectory(String fileName) {
        Image image = null;
        String filePath = "src/main/java/images/" + fileName;
        File imageSource = new File(filePath);

        try {
            // ImageIO.read() handles opening and closing the datastream.
            image = ImageIO.read(imageSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


    /*******************************************************************************************************************
     * Getters
     */
    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public List<GuiShapeDataObject> getShapesToDraw() {
        return shapesToDraw;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }


    /*******************************************************************************************************************
     * Setters
     */
    public void setShapesToDraw (List<GuiShapeDataObject> shapes) {
        this.shapesToDraw = shapes;
    }


    /*******************************************************************************************************************
     * Paint Method - Redraws the Frame
     */
    @Override
    public void paint(Graphics graphics) {
        prepareBufferedGraphic();
        graphics.drawImage(this.getBufferedImage(), 0, 0, null);
        System.out.println("Repaint!");
    }


    /*******************************************************************************************************************
     * Render - applies list of shapes to the Buffered Graphic
     */
    public void prepareBufferedGraphic() {
        this.bufferedImage = this.getBufferedImage();
        this.bufferedGraphics2D = (Graphics2D) this.bufferedImage.getGraphics();

        // Rendering hints to produce smoother rendering
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        bufferedGraphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        bufferedGraphics2D.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        drawBackground();
        drawAllShapes();
    }


    /*******************************************************************************************************************
     * Adds Event Listeners
     */
    private void addListeners() {
        // When mouse is moved
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                isMouseOverButton = false;
                drawAllShapes();  // TODO: Make a method to check if mouse is over a button
                if (isMouseOverButton) {
                    repaint();
                }
            }
        });

        // When Window is Resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                guiManager.windowSizeChangeEvent(currentJFrame);
            }
        });
    }


    /*******************************************************************************************************************
     * Draw Shapes
     */
    private boolean isMouseOverButton = false;
    private void drawAllShapes() {
        for (GuiShapeDataObject shape : shapesToDraw) {
            if (shape instanceof GuiRectangleDataObject) {
                drawRectangle(shape);
            }
            else if (shape instanceof GuiEllipseDataObject) {
                drawEllipse(shape);
            }
            else if (shape instanceof GuiRoundedRectangleDataObject) {
                drawRoundedRectangle(shape);
            }
            else if (shape instanceof GuiLineDataObject) {
                drawLine(shape);
            }
            else if (shape instanceof GuiTextBoxDataObject) {
                drawTextBox(shape);
                drawTextBox(shape); // Drawn twice to make the text more clear
            }
            else if (shape instanceof GuiTextBoxLinesDataObject) {
                drawTextBoxLines(shape);
            }
            else if (shape instanceof GuiImageDataObject) {
                drawImage(shape);
            }
            else if (shape instanceof GuiButtonDataObject) {
                drawButton(shape);
            }
        }
    }

    private void drawBackground() {
        bufferedGraphics2D.setColor(BACKGROUND_COLOR);
        int width = getWidth();
        int height = getHeight();
        bufferedGraphics2D.fillRect(0, 0, width, height);
    }

    private void drawRectangle(GuiShapeDataObject shape) {
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

    private void drawEllipse(GuiShapeDataObject shape) {
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

    private void drawRoundedRectangle(GuiShapeDataObject shape) {
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

    private void drawLine(GuiShapeDataObject shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int endX = shape.getPoint2X();
        int endY = shape.getPoint2Y();

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawLine(startX, startY, endX, endY);
    }

    private void drawTextBox (GuiShapeDataObject shape) {
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

    private void drawTextBoxLines(GuiShapeDataObject shape) {
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

    private void drawImage(GuiShapeDataObject shape) {
        BufferedImage image = (BufferedImage) loadImageFromImagesDirectory(shape.getImageFileName() );
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int imageStartX = shape.getPoint1X();
        int imageStartY = shape.getPoint1Y();

        // Manually set width in Pixels
        if (shape.getImageWidthInPixels() > 0) {
            imageWidth = shape.getImageWidthInPixels();
        }

        // Manually set height in Pixels
        if (shape.getImageHeightInPixels() > 0) {
            imageHeight = shape.getImageHeightInPixels();
        }




        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        //g.drawImage(image, 0, 0, null);
        g.drawImage(image, 0, 0, imageWidth, imageHeight, null); // Draw the scaled image

        // Gets rid of Graphics2D
        g.dispose();

        // Color filters for the image
        float[] scales = { 1f, 1f, 1f, 1f };
        float[] offsets = new float[10];
        RescaleOp rescaleOp = new RescaleOp(scales, offsets, null);

        /* Draw the image, applying the filter */
        bufferedGraphics2D.drawImage(bi, rescaleOp, imageStartX, imageStartY);
    }

    private void drawButton(GuiShapeDataObject shape) {
        int startX = shape.getPoint1X();
        int startY = shape.getPoint1Y();
        int endX = shape.getPoint2X();
        int endY = shape.getPoint2Y();
        int width = shape.getPoint2X() - shape.getPoint1X();
        int height = shape.getPoint2Y() - shape.getPoint1Y();
        int arc = shape.getArc();

        // Creates the default shape Fill
        bufferedGraphics2D.setColor( shape.getFillColor() );
        // Fill When the mouse hovers over the button
        if (mouseX > startX && mouseX < endX && mouseY > startY && mouseY < endY) {
            bufferedGraphics2D.setColor( Color.RED );
        }
        // Draws the Shape Fill
        bufferedGraphics2D.fillRoundRect(startX, startY, width, height, arc, arc);

        // Creates the shape Outline
        bufferedGraphics2D.setColor( shape.getOutlineColor() );
        bufferedGraphics2D.setStroke(new BasicStroke( shape.getOutlineWidth() ));
        bufferedGraphics2D.drawRoundRect(startX, startY, width, height, arc, arc);
    }


    /*******************************************************************************************************************
     * Checks if mouse if over a button
     */
    private boolean isMouseOverButton() {
        for (GuiShapeDataObject shape : shapesToDraw) {
            if (shape instanceof GuiButtonDataObject) {
                drawButton(shape);
            }
        }
    }

}