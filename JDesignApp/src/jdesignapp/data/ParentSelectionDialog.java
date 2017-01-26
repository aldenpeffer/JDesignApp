/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Alden
 */
public class ParentSelectionDialog extends Dialog{
    
    ArrayList<String> parentChoices;
    ArrayList<String> selectedParents;
    ArrayList<String> oldParents;
    ArrayList<CheckBox> boxes;
    ArrayList<Label> labels;
    ArrayList<Boolean> isInterface;
    
    public ParentSelectionDialog(ArrayList<String> parentChoices, ArrayList<Boolean> isInterface, ArrayList<String> oldParents){
        this.parentChoices = parentChoices;
        this.oldParents = oldParents;
        this.isInterface = isInterface;
        selectedParents = new ArrayList();
        labels = new ArrayList();
        boxes = new ArrayList();
        boolean hasClassParent = false;
        
        int options = parentChoices.size();       
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.add(new Label("Parents: "), 0, 0);
        
        for(int i = 0; i < options; i++){
            Label parent = new Label(parentChoices.get(i));
            CheckBox check = new CheckBox();
            boolean isInt = isInterface.get(i);
            check.setOnAction(e -> {
                for(int j = 0; j < options; j++){
                    if(!isInt){
                        if(check.isSelected()){
                            disableOtherClasses(check);
                        }else{
                            enableOtherClasses(check);
                        }
                    }         
                }
            });
            if(oldParents.contains(parentChoices.get(i))) check.setSelected(true);
            grid.add(parent, 1, i);
            grid.add(check, 2, i);
            labels.add(parent);
            boxes.add(check);
        }         
        
        //Determines if it has a selected class as a parent
        for(int i = 0; i < options; i++){
            CheckBox tempCheck = boxes.get(i);
            if(tempCheck.isSelected() && !isInterface.get(i)) hasClassParent = true;           
        }
        //If it has a class as parent, it can only have one, thus it disables all other class options
        if(hasClassParent){
            for(int i = 0; i < options; i++){
                CheckBox tempCheck = boxes.get(i);
                if(!tempCheck.isSelected() && !isInterface.get(i)) tempCheck.setDisable(true);
            }
        }
        
        ScrollPane pane = new ScrollPane();
        pane.setContent(grid);
        pane.setPrefSize(250, 200);
        this.setTitle("Select Parents");
        this.getDialogPane().setContent(pane);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
    
    public void disableOtherClasses(CheckBox c){
        int index = boxes.indexOf(c);
        int options = parentChoices.size(); 
        for(int i = 0; i < options; i++){
            if(!isInterface.get(i)){
                if(i != index){
                    boxes.get(i).setDisable(true);
                }
            }         
        }
    }
    
    public void enableOtherClasses(CheckBox c){
        int index = boxes.indexOf(c);
        int options = parentChoices.size(); 
        for(int i = 0; i < options; i++){
            if(!isInterface.get(i)){
                if(i != index){
                    boxes.get(i).setDisable(false);
                }
            }         
        }
    }
    
    public ArrayList<String> getSelectedParents(){
        int options = parentChoices.size();       
        for(int i = 0; i < options; i++){
            if(boxes.get(i).isSelected()){
                selectedParents.add(labels.get(i).getText());
            }
        }        
        return selectedParents;
    }
    
}
