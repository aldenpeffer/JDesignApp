/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jdesignapp.JDesignApp;

/**
 *
 * @author Alden
 */
public class JGrid extends Pane{
    
    ArrayList<Rectangle> grid;
    int boxSize = 20;
    
    public JGrid(){
        double heightBoxes = JDesignApp.leftPaneHeight/boxSize;
        double widthBoxes = JDesignApp.leftPaneWidth/boxSize;

        for(int i = 0; i < heightBoxes; i++){
            for(int j = 0; j < widthBoxes; j++){
                double x = j * boxSize;
                double y = i * boxSize;
                Rectangle rect = new Rectangle(x, y, boxSize, boxSize);
                rect.setStroke(Color.LIGHTGRAY);
                rect.setFill(Color.TRANSPARENT);
                //System.out.println("Rectangle at X: " + x + " and Y: " + y);
                this.getChildren().add(rect);
            }
        }

    }
}
