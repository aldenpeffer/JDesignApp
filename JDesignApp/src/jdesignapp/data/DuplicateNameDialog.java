/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Alden
 */
public class DuplicateNameDialog extends Dialog{
    
    TextField nameField;
    Label duplicateLabel;
    public DuplicateNameDialog(){
        duplicateLabel = new Label("The specified class/interface name is taken!"
                                + "\nPlease enter a unique name.");
        nameField = new TextField();
        nameField.setPromptText("Name");
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.add(duplicateLabel, 0, 0);
        grid.add(nameField, 1, 0);
        
        this.setTitle("Enter Unique Name");
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
    }
    
    public String getName(){
        return nameField.getText();
    }
}
