
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.control.Button;
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
public class MethodDataDialog extends Dialog{
    
    TextField nameField, returnField, argsListField, argNameField, argTypeField;
    Label nameLabel, returnLabel, accessLabel, staticLabel, abstractLabel, argsLabel;
    ArrayList<String> argNames, argTypes;
    CheckBox staticCheck, abstractCheck;
    ComboBox accessType;
    Button addArgButton, removeArgButton;
    
    public MethodDataDialog(){
        argNames = new ArrayList();
        argTypes = new ArrayList();
        nameField = new TextField();
        returnField = new TextField();
        argsListField = new TextField();
        argNameField = new TextField();
        argTypeField = new TextField();
        nameField.setPromptText("Method Name");
        returnField.setPromptText("Method Return Type");
        argNameField.setPromptText("Argument Name");
        argTypeField.setPromptText("Argument Type");
        argsListField.setEditable(false);
        argsListField.setPromptText("Argument List");
        
        nameLabel = new Label("Name: ");
        returnLabel = new Label("Return Type: ");
        accessLabel = new Label("Access: ");
        staticLabel = new Label("Static: ");
        abstractLabel = new Label("Abstract: ");
        staticCheck = new CheckBox();
        abstractCheck = new CheckBox();
        addArgButton = new Button("Add");
        removeArgButton = new Button("Remove");
        accessType = new ComboBox();
        accessType.getItems().addAll("public", "private", "protected", "");
        accessType.setValue("public");
        
        //Event handling
        addArgButton.setOnAction(e -> {
            String name = argNameField.getText();
            String type = argTypeField.getText();
            
            if(name.trim().length() > 0 && type.trim().length() > 0){
                argNames.add(name);
                argTypes.add(type);
                refreshArgsList();
                argNameField.clear();
                argTypeField.clear();
            }
        });
        
        removeArgButton.setOnAction(e -> {
            String name = argNameField.getText();
            String type = argTypeField.getText();
            int z = argNames.size();
            boolean match = false;
            for(int i = 0; i < z; i++){
                boolean nameMatch = false;
                boolean typeMatch = false;
                if(argNames.get(i).equals(name)) nameMatch = true;
                if(argTypes.get(i).equals(type)) typeMatch = true;
                if(nameMatch && typeMatch){
                    match = true;
                }
            }
            if(match){
                argNames.remove(name);
                argTypes.remove(type);
                argNameField.clear();
                argTypeField.clear();
            }
            refreshArgsList();
        });
        
        staticCheck.setOnAction(e -> {
            abstractCheck.setDisable(staticCheck.isSelected());
            abstractLabel.setDisable(staticCheck.isSelected());
        });
        
        abstractCheck.setOnAction(e ->{
            staticCheck.setDisable(abstractCheck.isSelected());
            staticLabel.setDisable(abstractCheck.isSelected());
        });
        
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        
        grid.add(returnLabel, 0, 1);
        grid.add(returnField, 1, 1);
        
        grid.add(accessLabel, 0, 2);
        grid.add(accessType, 1, 2);
        
        grid.add(argTypeField, 0, 3);
        grid.add(argNameField, 1, 3);
        grid.add(addArgButton, 2, 3);
        grid.add(removeArgButton, 3, 3);
        
        GridPane.setColumnSpan(argsListField, 4);
        grid.add(argsListField, 0, 4);
        
        grid.add(staticLabel, 0, 5);
        grid.add(staticCheck, 1, 5);
        
        grid.add(abstractLabel, 0, 6);
        grid.add(abstractCheck, 1, 6);
        
        this.setTitle("Enter Method Data");
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
    
    public void refreshArgsList(){
        String text = "";
        argsListField.clear();
        int z = argNames.size();
        for(int i = 0; i < z; i++){
            if(i != 0) text += ", "; 
            text+= argTypes.get(i) + " " + argNames.get(i);
        }
        argsListField.setText(text);
    }
    
    public void parseArgNameAndTypes(String toParse){
        String text = toParse.replace(",", "");
        String[] array = text.split(" ");
        
        if(toParse.trim().length() > 0){
            for(int i = 0; i < array.length; i++){
                if(i%2 == 0) argTypes.add(array[i]);
                if(i%2 == 1) argNames.add(array[i]);
            }
        }
    }
    
    public ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList();
        fields.add(nameField.getText());
        //fields.add(typeField.getText());
        fields.add(accessType.getValue().toString());
        fields.add(String.valueOf(staticCheck.isSelected()));
        return fields;
    }

    public String getName(){
        return nameField.getText().trim();
    }
    
    public String getReturnType(){
        return returnField.getText().trim();
    }
    
    public String getAccess(){
        return accessType.getValue().toString().trim();
    }
    
    public boolean getStatic(){
        return staticCheck.isSelected();
    }   
    
    public boolean getAbstract(){
        return abstractCheck.isSelected();
    } 
    
    public ArrayList<String> getArgNames(){
        return argNames;
    }
    
    public ArrayList<String> getArgTypes(){
        return argTypes;
    }
    
    public String getArgsList(){
        return argsListField.getText();
    }
    
    public void setName(String s){
        nameField.setText(s);
    }
    
    public void setReturnType(String s){
        returnField.setText(s);
    }
    
    public void setAccess(String s){
        accessType.setValue(s);
    }
    
    public void setStatic(Boolean b){
        staticCheck.setSelected(b);
    }
    
    public void setAbstract(Boolean b){
        abstractCheck.setSelected(b);
    }
    
    public void setArgsList(String s){
        argsListField.setText(s);
    }
}