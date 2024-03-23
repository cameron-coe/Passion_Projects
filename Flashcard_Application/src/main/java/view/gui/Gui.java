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
    private int mouseDownX = 0;
    private int mouseDownY = 0;




    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        setTitle("The Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 450);  // Size needs to be set before the imageRenderer can render an image
        imageRenderer = new ImageRenderer(this);
        setIconImage(imageRenderer.loadImageFromImagesDirectory("AppIcon.png"));

        flashcardApplication.runtimeStartEvent(this);
        addListeners();

        setVisible(true);
    }


    /*******************************************************************************************************************
     * Getters
     */
    public List<GuiShapeDataObject> getShapesToDraw() {
        return imageRenderer.getShapesToDraw();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getMouseDownX() {
        return mouseDownX;
    }

    public int getMouseDownY() {
        return mouseDownY;
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

        // When mouse is pressed down
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO
                mouseDownX = e.getX();
                mouseDownY = e.getY();
                flashcardApplication.mousePressedEvent(currentJframe);
            }
        });

        // When Mouse is Clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO
                flashcardApplication.mouseClickEvent(currentJframe);
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