package main.java.view.gui;


import main.java.model.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);
    private final static String HOMEPAGE = "homepage";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShape> shapesToDraw;
    private BufferedImage bufferedImage;
    private int mouseX = 0;
    private int mouseY = 0;


    /*******************************************************************************************************************
     * Gui Elements
     */
    private GuiShape flashcard;
    private GuiShape homepageTitle;


    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        shapesToDraw = new ArrayList<>();
        windowSettings();
        addListeners();
        homepage();
    }


    /*******************************************************************************************************************
     * Paint Method - Redraws the Frame
     */
    @Override
    public void paint(Graphics graphics) {
        DrawGraphics draw = new DrawGraphics(bufferedImage, this, shapesToDraw);
        draw.prepareBufferedGraphic();
        graphics.drawImage(draw.getBufferedImage(), 0, 0, null);
    }

    /*******************************************************************************************************************
     * Gui Window Settings
     */
    private void windowSettings() {
        // JFrame is extended, so it's implied
        //TODO: Set icon image
        setSize(800, 450);
        setTitle("Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buffer
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        setVisible(true);

    }


    /*******************************************************************************************************************
     * Listener Events
     */
    private void addListeners() {
        // When mouse is moved
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                guiUpdate();
            }
        });

        // When Window is Resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                guiUpdate();
            }
        });

        // When window first opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                guiUpdate();
                repaint();
            }
        });
    }


    /*******************************************************************************************************************
     * Menu Displays
     */

    private void homepage() {
        homepageTitle();
        //shapesToDraw.add(pageTitle);
    }

    private void homepageTitle() {
        int leftSpacing = 10;
        int rightSpacing = getWidth() - leftSpacing - 7;

        if (shapesToDraw.contains(homepageTitle)) {
            // Update
            int indexOfObject = shapesToDraw.indexOf(homepageTitle);
            shapesToDraw.get(indexOfObject).setBounds(leftSpacing, 50, rightSpacing, 100);
        } else {
            // Instantiate
            String longString = "Homepage Homepage Homepage Homepage Homepage Homepage Homepage ";
            homepageTitle = GuiShape.makeTextBox(longString, leftSpacing, 50, rightSpacing, 100);
            homepageTitle.setTextFillColor(Color.blue);
            shapesToDraw.add(homepageTitle);
        }
    }

    /*******************************************************************************************************************
     * Gui Update Function
     */

    public void guiUpdate () {
        //if (pageTitle != null ) {
        System.out.println("LALALALALA " + shapesToDraw.size());
        homepage();

        repaint();

        /**
        int indexOfFlashcard = shapesToDraw.indexOf(flashcard);
        shapesToDraw.get(indexOfFlashcard).setBounds(mouseX, mouseY, 300, 300);*/
        //}
    }


}
