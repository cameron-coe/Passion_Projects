package main.java.view.gui;

import main.java.view.gui.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Gui extends JFrame {

    /*******************************************************************************************************************
     *Constants
     */


    /*******************************************************************************************************************
     * Instance Variables
     */
    private JFrame currentJframe = this;
    private FlashcardApplication flashcardApplication = new FlashcardApplication(this);
    private GraphicsRenderer graphicsRenderer;

    private int mouseX = 0;
    private int mouseY = 0;


    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        setTitle("The Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 450);  // Size needs to be set before the graphicsRenderer can render an image
        graphicsRenderer = new GraphicsRenderer(this);
        setIconImage(graphicsRenderer.loadImageFromImagesDirectory("AppIcon.png"));

        flashcardApplication.runtimeStartEvent(this);
        addListeners();

        setVisible(true);
    }


    /*******************************************************************************************************************
     * Getters
     */
    public List<GuiShape> getShapesToDraw() {
        return graphicsRenderer.getShapesToDraw();
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
    public void setShapesToDraw (List<GuiShape> shapes) {
        graphicsRenderer.setShapesToDraw(shapes);
    }


    /*******************************************************************************************************************
     * Paint Method - Redraws the Frame
     */
    @Override
    public void paint(Graphics graphics) {
        BufferedImage image = graphicsRenderer.prepareBufferedGraphic(this);
        graphics.drawImage(image, 0, 0, null);
        System.out.println("Repaint!");
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
                flashcardApplication.mouseMoveEvent();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                flashcardApplication.mouseMoveEvent();
            }
        });

        // When mouse is pressed down or released
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO
                flashcardApplication.mousePressedEvent(currentJframe);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO
                mouseX = e.getX();
                mouseY = e.getY();
                flashcardApplication.mouseReleasedEvent(currentJframe);
            }
        });

        // When Window is Resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                flashcardApplication.windowSizeChangeEvent(currentJframe);
            }
        });
    }







}