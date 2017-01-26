/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author Alden
 */
public class JConnector extends Line{
    
    String firstParentName;
    String secondParentName;
    final static String CONNECTOR_TYPE = "connector";
    
    public JConnector(){
        this.setStrokeWidth(2);
        this.setStroke(Color.BLACK);
    }
    
    public void setParents(String name1, String name2){
        firstParentName = name1;
        secondParentName = name2;
    }
    
    public String getFirstParentName(){
        return firstParentName;
    }
    
    public String getSecondParentName(){
        return secondParentName;
    }
    
    public void setFirstParentName(String firstParentName){
        this.firstParentName = firstParentName;
    }
    
    public void setSecondParentName(String secondParentName){
        this.secondParentName = secondParentName;
    }
    
}
