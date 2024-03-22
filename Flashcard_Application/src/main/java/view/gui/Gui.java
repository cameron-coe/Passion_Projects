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
    private ImageRenderer imageRenderer;

    private int mouseX = 0;
    private int mouseY = 0;




    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        addListeners();
        setTitle("The Flashcard App");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        imageRenderer = new ImageRenderer(this);
        flashcardApplication.runtimeStartEvent(this);

        setIconImage(imageRenderer.loadImageFromImagesDirectory("AppIcon.png"));

        setVisible(true);
    }


    /*******************************************************************************************************************
     * Getters
     */
    /*public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }*/

    public List<GuiShapeDataObject> getShapesToDraw() {
        return imageRenderer.getShapesToDraw();
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
        imageRenderer.setShapesToDraw(shapes);
    }


    /*******************************************************************************************************************
     * Paint Method - Redraws the Frame
     */
    @Override
    public void paint(Graphics graphics) {
        BufferedImage image = imageRenderer.prepareBufferedGraphic(this);
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
                flashcardApplication.mouseMoveEvent(currentJframe);
            }
        });

        // When Window is Resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                flashcardApplication.windowSizeChangeEvent(currentJframe);
            }
        });
    }







}