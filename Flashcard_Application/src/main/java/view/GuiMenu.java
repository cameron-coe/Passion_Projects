package main.java.view;

import main.java.view.gui.GuiDraw;
import main.java.view.gui.GuiShape;

import java.util.List;

public class GuiMenu {
    // This is where we'll call GuiDraw to add shapes to its list
    // draw and gui will not talk to each other, that will be done through Menu
    // Take gui out of app
    // app will only talk to gui menu

    /*
     1. menu has a list of shapes
     2. menu has an instance of gui
     3. menu adds/ updates shapes in list
     4. menu makes an instance of draw with the list as a parameter
     5. menu adds draw to gui's renderAll method
     6. The page renders
     */

    private List<GuiShape> allShapesToDraw;
    private GuiDraw guiDraw;

}
