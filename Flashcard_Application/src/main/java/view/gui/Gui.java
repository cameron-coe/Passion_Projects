package main.java.view.gui;


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
     *Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);


    private JLabel positionLabel;
    private BufferedImage bufferedImage;
    private int x = 0;
    private int y = 0;

    public Gui() {
        super("Mouse Position Tracker");

        positionLabel = new JLabel("Mouse Position: ");
        positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        positionLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        add(positionLabel, BorderLayout.NORTH);

        windowSettings();

        addListeners();
    }

    @Override
    public void paint(Graphics graphics) {
        List<GuiShape> testList = new ArrayList<>();
        GuiShape testRect = GuiShape.makeRoundedRectangle(x, y, 200, 200, 30);
        testRect.setFillColor(Color.white);
        testRect.setOutlineColor(new Color(0,0,0,0));
        testRect.setOutlineWidth(5);
        testList.add(testRect);

        GuiShape textLines = GuiShape.makeTextBoxLines(x, y, 200, 200);
        textLines.setOutlineWidth(1);
        textLines.setOutlineColor(new Color(0,0,0, 50));
        testList.add(textLines);

        String longString = "The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.";
        GuiShape testTextBox = GuiShape.makeTextBox(longString, x, y, 200, 200);
        testTextBox.setTextFillColor( Color.black );
        testList.add(testTextBox);
        testTextBox.setTextFillColor( Color.blue );



        DrawGraphics draw = new DrawGraphics(bufferedImage, this, testList);
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
        //setLocationRelativeTo(null); // Center the frame

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
                x = e.getX();
                y = e.getY();
                positionLabel.setText("Mouse Position: (" + x + ", " + y + ")");
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


}
