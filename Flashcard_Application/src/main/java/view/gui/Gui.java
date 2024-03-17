package main.java.view.gui;


import main.java.model.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);

    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShape> shapesToDraw;
    private BufferedImage bufferedImage;
    private int mouseX = 0;
    private int mouseY = 0;


    /*******************************************************************************************************************
     * Constructor
     */
    public Gui() {
        shapesToDraw = new ArrayList<>();
        windowSettings();
        addListeners();
        makeHomepageTitle();
    }


    /*******************************************************************************************************************
     * GuiShape List Methods
     */



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
     * Gui Settings
     */
    private void windowSettings() {
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
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                guiUpdate();

                repaint();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
        });
    }


    /*******************************************************************************************************************
     * Menu Displays
     */

    public void guiHomepage(List<Subject> subjects) {
        String longString = "The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.";
        GuiShape testTextBox = GuiShape.makeTextBox(longString, mouseX, mouseY, 200, 200);
        shapesToDraw.add(testTextBox);
        testTextBox.setTextFillColor( Color.blue );
        System.out.println(shapesToDraw.size());

    }


    /*******************************************************************************************************************
     * Gui Elements
     */
    private GuiShape box;
    private GuiShape pageTitle;
    private void makeHomepageTitle() {

        box = GuiShape.makeRoundedRectangle(mouseX, mouseY, 400, 200, 25);
        box.setFillColor(Color.WHITE);
        shapesToDraw.add(box);

        String longString = "The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.";
        GuiShape testTextBox = GuiShape.makeTextBox(longString, mouseX, mouseY, 400, 200);
        testTextBox.setTextFillColor( Color.blue );
        pageTitle = testTextBox;
        shapesToDraw.add(pageTitle);
        //return homepageTitle;
    }

    /*******************************************************************************************************************
     * Gui Element Actions
     */

    public void guiUpdate () {
        if (pageTitle != null ) {
            int indexOfPageTitle = shapesToDraw.indexOf(pageTitle);
            System.out.println("LALALALALA " + shapesToDraw.size());
            shapesToDraw.get(indexOfPageTitle).setBounds(mouseX, mouseY, 300, 300);

            int indexOfBox = shapesToDraw.indexOf(box);
            shapesToDraw.get(indexOfBox).setBounds(mouseX, mouseY, 300, 300);
        }
    }


}
