//import main.java.view.gui.DrawGui;
//import main.java.view.gui.GuiShape;
//
//import javax.swing.*;
//import javax.swing.border.Border;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;
//
//
///* TODO
//     1. gui has a list of shapes
//     3. gui adds/ updates shapes in list
//     4. gui makes an instance of draw with the list as a parameter
//     5. draw is added to gui's renderAll method
//     6. The page renders
//     */
//
//public class Gui extends JFrame {
//
//    /*******************************************************************************************************************
//     * Instance Variables
//     */
//    private JFrame jFrame;
//    private JPanel jPanel;
//    private DrawGui drawGui;
//
//    private List<GuiShape> allShapesToDraw;
//
//    /*******************************************************************************************************************
//     * Constructor
//     */
//
//    private JLabel positionLabel;
//
//    public Gui () {
//        jFrame = new JFrame();
//        jPanel = new JPanel();
//        drawGui = new DrawGui(new ArrayList<>()); // TODO fix this later
//
//        Border border = BorderFactory.createEmptyBorder(30, 30, 10, 30);
//        jPanel.setBorder(border);
//        jPanel.setLayout(new GridLayout(0, 1));
//
//        jFrame.add(jPanel, BorderLayout.CENTER);
//
//
//        //TODO: Check if this get mouse position:
//
//        //super("Mouse Position Tracker");
//
//        positionLabel = new JLabel("Mouse Position: ");
//        positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        positionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
//
//        add(positionLabel, BorderLayout.NORTH);
//
//        addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                int x = e.getX();
//                int y = e.getY();
//                positionLabel.setText("Mouse Position: (" + x + ", " + y + ")");
//            }
//        });
//
//
//
//        windowSettings();
//        jFrame.pack();
//        jFrame.setVisible(true);
//    }
//
//

//
//    /*******************************************************************************************************************
//     * Methods
//     */
//    public void renderAll () {
//        //drawRect();
//        jFrame.getContentPane().add(drawGui);
//    }
//
//
//
//
//}
//
//}
