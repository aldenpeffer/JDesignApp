/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Alden
 */
public class VariableDataDialog extends Dialog{
    
    TextField nameField, typeField;
    Label nameLabel, typeLabel, accessLabel, staticLabel;
    CheckBox staticCheck;
    ComboBox accessType;
    
    public VariableDataDialog(){
        nameField = new TextField();
        typeField = new TextField();
        nameField.setPromptText("Variable Name");
        typeField.setPromptText("Variable Type");
        nameLabel = new Label("Name: ");
        typeLabel = new Label("Type: ");
        accessLabel = new Label("Access: ");
        staticLabel = new Label("Static: ");
        staticCheck = new CheckBox();
        accessType = new ComboBox();
        accessType.getItems().addAll("public", "private", "protected", "");
        accessType.setValue("public");
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        
        grid.add(typeLabel, 0, 1);
        grid.add(typeField, 1, 1);
        
        grid.add(accessLabel, 0, 2);
        grid.add(accessType, 1, 2);
        
        grid.add(staticLabel, 0, 3);
        grid.add(staticCheck, 1, 3);
        
        this.setTitle("Enter Variable Data");
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
    
    public String getName(){
        return nameField.getText().trim();
    }
    
    public String getType(){
        return typeField.getText().trim();
    }
    
    public String getAccess(){
        return accessType.getValue().toString().trim();
    }
    
    public boolean getStatic(){
        return staticCheck.isSelected();
    }
    
    public void setName(String s){
        nameField.setText(s);
    }
    
    public void setType(String s){
        typeField.setText(s);
    }
    
    public void setAccess(String s){
        accessType.setValue(s);
    }
    
    public void setStatic(Boolean b){
        staticCheck.setSelected(b);
    }
    
}
