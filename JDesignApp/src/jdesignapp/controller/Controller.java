/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.controller;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jdesignapp.JDesignApp;
import jdesignapp.data.DataManager;
import jdesignapp.data.DuplicateNameDialog;
import jdesignapp.data.JConnector;
import jdesignapp.data.JItem;
import jdesignapp.data.JMethod;
import jdesignapp.data.JVariable;
import jdesignapp.data.MethodDataDialog;
import jdesignapp.data.ParentSelectionDialog;
import jdesignapp.data.VariableDataDialog;
import jdesignapp.file.FileManager;

/**
 *
 * @author Alden
 */
public class Controller {
    
    TextField packageField;
    TextField classField;
    TableView varTable;
    TableView methTable;
    DataManager dataManager;
    FileManager fileManager;
    JDesignApp app;
    ArrayList<JItem> itemList;
    ArrayList<JConnector> connectorList;
    JItem selectedItem;
    int index;
    Button removeButton, resizeButton;
    Button addVarButton, removeVarButton;
    Button addMethButton, removeMethButton;
    JDesignState state;
    double xdist, ydist;
    double origWidth, origHeight, origX, origY;
    String pressedVal;
    
    public Controller(JDesignApp app, DataManager dataManager, FileManager fileManager){
        this.dataManager = dataManager;
        this.fileManager = fileManager;
        this.app = app;
        packageField = app.getPackageField();
        classField = app.getClassField();
        removeButton = app.getRemoveButton();
        resizeButton = app.getResizeButton();
        varTable = app.getVariableTable();
        methTable = app.getMethodTable();
        pressedVal = "";
    }
    
    public void handleNewRequest(){
        dataManager.clearItemList();
        itemList = dataManager.getItemList();
        connectorList = dataManager.getConnectorList();
        app.loadWorkspace();
        app.clearWorkspace();
    }
    
    public void handleAddClassRequest(){
        JItem classItem = new JItem(false);
        itemList = dataManager.getItemList();
        
        String name = "TempClassName";
        boolean validName = false;
        int counter = 1;
        while(!validName){
            boolean nameTaken = false;
            for(JItem item : itemList){
                if(item.getClassName().equals(name)) nameTaken = true;
            }
            if(nameTaken){
                counter++;
                name = "TempClassName" + counter;               
            }else{
                validName = true;
            }          
        }
        
        classItem.updateText(name);
        dataManager.addItem(classItem);
        itemList = dataManager.getItemList();
        connectorList = dataManager.getConnectorList();
        app.updateWorkspace();
    }
    
    public void handleAddInterfaceRequest(){
        JItem classItem = new JItem(true);
        itemList = dataManager.getItemList();
        
        String name = "TempInterfaceName";
        boolean validName = false;
        int counter = 1;
        while(!validName){
            boolean nameTaken = false;
            for(JItem item : itemList){
                if(item.getClassName().equals(name)) nameTaken = true;
            }
            if(nameTaken){
                counter++;
                name = "TempInterfaceName" + counter;               
            }else{
                validName = true;
            }          
        }
        classItem.updateText(name);
        dataManager.addItem(classItem);
        itemList = dataManager.getItemList();
        connectorList = dataManager.getConnectorList();
        app.updateWorkspace();
    }
    
    public void handleRemove(){
        if(selectedItem != null){
            dataManager.removeItem(selectedItem);
            itemList = dataManager.getItemList();
            app.updateWorkspace();
            selectedItem = null;
            app.itemSelectedUpdate(false, null);
            emptyEditPane();
        }
    }
    
    public void handleParentsModified(){
        if(selectedItem != null){
            ArrayList<String> parentOptions = new ArrayList();
            ArrayList<Boolean> isInterface = new ArrayList();
            itemList = dataManager.getItemList(); 
            for(JItem item : itemList){
                if(!item.getClassName().equals(selectedItem.getClassName())){
                    parentOptions.add(item.getClassName());
                    isInterface.add(item.isInterface());
                }
            }
            ParentSelectionDialog dialog = new ParentSelectionDialog(parentOptions, isInterface, selectedItem.getParents());
            Optional<ButtonType> result = dialog.showAndWait();
            
            if(result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) return;
            
            selectedItem.setParents(dialog.getSelectedParents());
        }
    }
    
    public void handleResizeRequest(){
        state = JDesignState.resizeState;
        selectedItem.showResizeMarkers(true);
        app.updateWorkspace();
    }
    
    public void handleWorkspaceMousePressed(MouseEvent e){
        if(state == JDesignState.selectState){
            if(selectedItem != null){
                String name = checkName();
                selectedItem.setClassName(name);
                classField.setText(name);
            }
            if(selectedItem != null) selectedItem.setStroke(Color.BLACK);
            double mX = e.getX();
            double mY = e.getY();
            itemList = dataManager.getItemList();  
            connectorList = dataManager.getConnectorList();
            index = -1;
            
            for(int i = 0; i < itemList.size(); i++){
                if(itemList.get(i).intersects(mX, mY, 1, 1)){
                    index = i;
                }
            }
            
            if(index >= 0){
                selectedItem = itemList.get(index);
            }else{
                selectedItem = null;
                emptyEditPane();
            }
            
            if(selectedItem != null){
                app.itemSelectedUpdate(true, selectedItem);
                populateEditPane();
                selectedItem.setStroke(Color.PURPLE);
                xdist = mX - selectedItem.getX();
                ydist = mY - selectedItem.getY();
            }else{
                app.itemSelectedUpdate(false, null);
            }
        }else if(state == JDesignState.resizeState){
            double mX = e.getX();
            double mY = e.getY();
            xdist = mX;
            ydist = mY;
            origWidth = selectedItem.getWidth();
            origHeight = selectedItem.getHeight();
            origX = selectedItem.getX();
            origY = selectedItem.getY();
            ArrayList<Rectangle> resizeMarkers = selectedItem.getResizeMarkers();
            Rectangle collidedRect = null;
            for(Rectangle r : resizeMarkers){
                if(r.intersects(mX, mY, 1, 1)){
                    collidedRect = r;
                }
            } 
            if(collidedRect != null){
                pressedVal = "" + resizeMarkers.indexOf(collidedRect);
            }else{
                pressedVal = "";
            }
        }
    }
    
    public void handleWorkspaceMouseDragged(MouseEvent e){
        if(state == JDesignState.selectState){
            double mX = e.getX();
            double mY = e.getY();
            if(selectedItem != null){
                selectedItem.setX(mX - xdist);
                selectedItem.setY(mY - ydist);
                selectedItem.updateFields();
            }
        }else if(state == JDesignState.resizeState){
            double mX = e.getX();
            double mY = e.getY();
            
            if(pressedVal.equals("4")){
                selectedItem.setResizeWidth(origWidth + mX - xdist);
                selectedItem.updateWidth();
                selectedItem.updateFields();
                selectedItem.updateResizeMarkers();
            }else if(pressedVal.equals("3")){
                if(origX - (xdist - mX) > origX){
                    selectedItem.setX(origX);
                }else{
                    selectedItem.setX(origX - (xdist - mX));
                }
                if(xdist - mX + origWidth < origWidth + origX){
                    selectedItem.setResizeWidth(mY);
                }
                selectedItem.setResizeWidth(origWidth + (xdist - mX));
                
                selectedItem.updateWidth();
                selectedItem.updateFields();
                selectedItem.updateResizeMarkers();
            }else if(pressedVal.equals("6")){
                selectedItem.setResizeHeight(origHeight + mY - ydist);
                selectedItem.updateHeight();
                selectedItem.updateFields();
                selectedItem.updateResizeMarkers();
            }else if(pressedVal.equals("7")){
                selectedItem.setResizeWidth(origWidth + mX - xdist);
                selectedItem.setResizeHeight(origHeight + mY - ydist);
                selectedItem.updateWidth();
                selectedItem.updateHeight();
                selectedItem.updateFields();
                selectedItem.updateResizeMarkers();
            }
        }
    }
    
    public void handleWorkspaceMouseReleased(MouseEvent e){
        if(state == JDesignState.resizeState){
            pressedVal = "";
        }
    }
    
    public void handleWorkspaceMouseMoved(MouseEvent e){
        if(state == JDesignState.resizeState){
            //Resize rects: 0 is top left, 1 is top center, 2 is top right
            //              3 is left center, 4 is right center
            //              5 is bottom left, 6 is bottom center, 7 is bottom right
            if(selectedItem != null){
                ArrayList<Rectangle> resizeMarkers = selectedItem.getResizeMarkers();
                double x = e.getX();
                double y = e.getY();
                int num;
                Rectangle collidedRect = null;
                for(Rectangle r : resizeMarkers){
                    if(r.intersects(x, y, 1, 1)){
                        collidedRect = r;
                    }
                }
                if(collidedRect != null){
                    num = resizeMarkers.indexOf(collidedRect);
                }else{
                    num = -1;
                }
                if(num == 0 || num == 7){
                    app.getStage().getScene().setCursor(Cursor.NW_RESIZE);
                }else if(num == 1 || num == 6){
                    app.getStage().getScene().setCursor(Cursor.V_RESIZE);
                }else if(num == 2 || num == 5){
                    app.getStage().getScene().setCursor(Cursor.SW_RESIZE);
                }else if(num == 3 || num == 4){
                    app.getStage().getScene().setCursor(Cursor.H_RESIZE);
                }else{
                    app.getStage().getScene().setCursor(Cursor.DEFAULT);
                }
            }
        }
    }
    
    
    public void handleSelectRequest(){
        state = JDesignState.selectState;
        if(selectedItem != null) selectedItem.showResizeMarkers(false);
        app.getStage().getScene().setCursor(Cursor.DEFAULT);
        app.updateWorkspace();
    }
    
    public void populateEditPane(){
        classField.setText(selectedItem.getClassName());
        classField.setDisable(false);
        packageField.setText(selectedItem.getPackageName());
        packageField.setDisable(false);
    }
    
    public void emptyEditPane(){
        classField.setText("");
        classField.setDisable(true);
        packageField.setText("");
        packageField.setDisable(true);
        
    }

    public void handleClassFieldChanged(String newValue) {
        if(selectedItem != null){
            selectedItem.setClassName(newValue);
            selectedItem.updateText(newValue);
        }
    }

    public void handlePackageFieldChanged(String newValue) {
        if(selectedItem != null){
            selectedItem.setPackageName(newValue);
        }
    }
    
    public void handleAddVariable(){
        if(selectedItem != null){
            ArrayList<JVariable> varList = selectedItem.getVariables();
            boolean validName = false;
            VariableDataDialog varData = null;
            while(!validName){
                boolean duplicate = false;
                boolean empty = false;
                varData = new VariableDataDialog();
                Optional<ButtonType> result = varData.showAndWait();
                //Leave method if canceled (closing the dialog counts as cancel)
                if(result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) return;
                
                //Check if empty
                if(varData.getName().trim().length() == 0) empty = true;                
                if(empty){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Variable Name");
                    alert.setContentText("Variable name must have a value.");
                    alert.showAndWait();
                }
                //Check is duplicate
                for(JVariable v : varList){
                    if(v.getVariableName().equals(varData.getName().trim())) duplicate = true;
                }               
                if(duplicate){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Variable Name");
                    alert.setContentText("Variable name must have a unique value.");
                    alert.showAndWait();
                }
                if(!duplicate && !empty) validName = true;
            }
            
                
            //here down is good
            JVariable var = new JVariable();
            
            var.setVariableName(varData.getName());
            var.setVariableType(varData.getType());
            var.setAccessType(varData.getAccess());
            var.setIsStatic(varData.getStatic());
            
            varList.add(var);
            ObservableList list = FXCollections.observableList(varList);
            varTable.setItems(list);
            
            selectedItem.addVariableText(var);
            app.updateWorkspace();
        }
    }
    
    public void handleAddMethod(){       
        if(selectedItem != null){
            ArrayList<JMethod> methList = selectedItem.getMethods();
            boolean validName = false;
            MethodDataDialog methData = null;
            
            while(!validName){
                boolean duplicate = false;
                boolean empty = false;
                methData = new MethodDataDialog();
                Optional<ButtonType> result = methData.showAndWait();
                //Leave method if canceled (closing the dialog counts as cancel)
                if(result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) return;
                
                //Check if empty
                if(methData.getName().trim().length() == 0) empty = true;                
                if(empty){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Method Name");
                    alert.setContentText("Method name must have a value.");
                    alert.showAndWait();
                }
                //Check is duplicate
                boolean nameDuplicate = false;
                boolean argsDuplicate = false;
                boolean returnDuplicate = false;
                for(JMethod m : methList){
                    if(m.getMethodName().equals(methData.getName().trim())) nameDuplicate = true;                   
                    if(nameDuplicate){
                        String dialogArgsString = methData.getArgsList();
                        String currentMethArgString = m.getArgsString().substring(1, m.getArgsString().length() - 1);
                        if(currentMethArgString.equals(dialogArgsString)) argsDuplicate = true;
                    }                   
                    if(nameDuplicate && argsDuplicate){
                        String dialogReturnString = methData.getReturnType().trim();
                        String currentMethReturnString = m.getReturnType().trim();
                        if(dialogReturnString.equals(currentMethReturnString)) returnDuplicate = true;
                    }
                        
                    if(nameDuplicate && argsDuplicate && returnDuplicate) duplicate = true;
                }                     
                
                if(duplicate){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Method");
                    alert.setContentText("Method name, return type, and arguments must be unique from other methods.");
                    alert.showAndWait();
                }
                if(!duplicate && !empty) validName = true;
            }            
          
            JMethod meth = new JMethod();
            
            meth.setMethodName(methData.getName());
            meth.setReturnType(methData.getReturnType());
            meth.setAccessType(methData.getAccess());
            meth.setIsStatic(methData.getStatic());
            meth.setIsAbstract(methData.getAbstract());
            
            int z = methData.getArgNames().size(); 
            for(int i = 0; i < z; i++){
                meth.addArgument(methData.getArgTypes().get(i), methData.getArgNames().get(i));
            }

            methList.add(meth);
            ObservableList list = FXCollections.observableList(methList);
            methTable.setItems(list);
            
            selectedItem.addMethodText(meth);
            app.updateWorkspace();
        }
    }
    
    public void handleRemoveVariable(){
        JVariable variable = (JVariable)varTable.getSelectionModel().getSelectedItem();
        if(variable != null){   
            varTable.getItems().remove(variable);
            selectedItem.removeVariable(variable);
            app.updateWorkspace();
        }
    }
    
    public void handleRemoveMethod(){
        JMethod method = (JMethod)methTable.getSelectionModel().getSelectedItem();
        if(method != null){   
            methTable.getItems().remove(method);
            selectedItem.removeMethod(method);
            app.updateWorkspace();
        }
    }
    
    public void handleEditVariable(){
        JVariable variable = (JVariable)varTable.getSelectionModel().getSelectedItem();
        if(variable != null){
            ArrayList<JVariable> varList = selectedItem.getVariables();
            boolean validName = false;
            String previousName = "";
            VariableDataDialog varData = null;
            
            while(!validName){
                boolean duplicate = false;
                boolean empty = false;
                varData = new VariableDataDialog();
                previousName = variable.getVariableName();
                varData.setName(variable.getVariableName());
                varData.setType(variable.getVariableType());
                varData.setAccess(variable.getAccessType());
                varData.setStatic(variable.getIsStatic());
                Optional<ButtonType> result = varData.showAndWait();
                //Leave method if canceled (closing the dialog counts as cancel)
                if(result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) return;
                
                //Check if empty
                if(varData.getName().trim().length() == 0) empty = true;                
                if(empty){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Variable Name");
                    alert.setContentText("Variable name must have a value.");
                    alert.showAndWait();
                }
                //Check is duplicate
                if(!varData.getName().trim().equals(previousName)){
                    for(JVariable v : varList){
                        if(v.getVariableName().equals(varData.getName().trim())) duplicate = true;
                    }               
                }
                if(duplicate){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Variable Name");
                    alert.setContentText("Variable name must have a unique value.");
                    alert.showAndWait();
                }
                if(!duplicate && !empty) validName = true;
            }
            
            JVariable editedVariable = new JVariable();
            editedVariable.setVariableName(varData.getName());
            editedVariable.setVariableType(varData.getType());
            editedVariable.setAccessType(varData.getAccess());
            editedVariable.setIsStatic(varData.getStatic());
            
            //Update text list & variable list
            selectedItem.editVariable(editedVariable, previousName);
            
            //Update table
            ArrayList<JVariable> vList = selectedItem.getVariables();
            ObservableList list = FXCollections.observableList(vList);
            varTable.setItems(list);
            varTable.refresh();
            app.updateWorkspace();
        }
    }
    
    public void handleEditMethod(){
        JMethod method = (JMethod)methTable.getSelectionModel().getSelectedItem();
        if(method != null){
            ArrayList<JMethod> methList = selectedItem.getMethods();
            boolean validName = false;
            String previousName = "";
            String previousReturnType = "";
            String previousArgsList = "";
            MethodDataDialog methData = null;
            
            while(!validName){
                boolean duplicate = false;
                boolean empty = false;
                previousName = method.getMethodName();
                previousReturnType = method.getReturnType();
                previousArgsList = method.getArgsList();
                methData = new MethodDataDialog();
                methData.setName(method.getMethodName());
                methData.setReturnType(method.getReturnType());
                methData.setAccess(method.getAccessType());
                methData.setStatic(method.getIsStatic());
                methData.setAbstract(method.getIsAbstract());
                methData.parseArgNameAndTypes(previousArgsList);
                methData.setArgsList(method.getArgsList());
                Optional<ButtonType> result = methData.showAndWait();
                //Leave method if canceled (closing the dialog counts as cancel)
                if(result.get() == ButtonType.CANCEL || result.get() == ButtonType.CLOSE) return;
                
                //Check if empty
                if(methData.getName().trim().length() == 0) empty = true;                
                if(empty){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Method Name");
                    alert.setContentText("Method name must have a value.");
                    alert.showAndWait();
                }
                //Check is duplicate
                boolean nameDuplicate = false;
                boolean argsDuplicate = false;
                boolean returnDuplicate = false;
                if(!(methData.getName().trim().equals(previousName) && methData.getReturnType().trim().equals(previousReturnType) 
                        && methData.getArgsList().trim().equals(previousArgsList))){
                    for(JMethod m : methList){
                        if(m.getMethodName().equals(methData.getName().trim())) nameDuplicate = true;                   
                        if(nameDuplicate){
                            String dialogArgsString = methData.getArgsList();
                            String currentMethArgString = m.getArgsString().substring(1, m.getArgsString().length() - 1);
                            if(currentMethArgString.equals(dialogArgsString)) argsDuplicate = true;
                        }                   
                        if(nameDuplicate && argsDuplicate){
                            String dialogReturnString = methData.getReturnType().trim();
                            String currentMethReturnString = m.getReturnType().trim();
                            if(dialogReturnString.equals(currentMethReturnString)) returnDuplicate = true;
                        }

                        if(nameDuplicate && argsDuplicate && returnDuplicate) duplicate = true;
                    }                     
                }
                if(duplicate){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Method");
                    alert.setContentText("Method name, return type, and arguments must be unique from other methods.");
                    alert.showAndWait();
                }
                if(!duplicate && !empty) validName = true;
            }
            
            //If its not a duplicate and has a valid name, continue
            JMethod editedMethod = new JMethod();
            
            editedMethod.setMethodName(methData.getName());
            editedMethod.setReturnType(methData.getReturnType());
            editedMethod.setAccessType(methData.getAccess());
            editedMethod.setIsStatic(methData.getStatic());
            editedMethod.setIsAbstract(methData.getAbstract());
            
            int z = methData.getArgNames().size(); 
            for(int i = 0; i < z; i++){
                editedMethod.addArgument(methData.getArgTypes().get(i), methData.getArgNames().get(i));
            }                                  
            //Update text list & variable list
            selectedItem.editMethod(editedMethod, previousName, previousReturnType, previousArgsList);
            
            //Update table
            ArrayList<JMethod> mList = selectedItem.getMethods();
            ObservableList list = FXCollections.observableList(mList);
            methTable.setItems(list);
            methTable.refresh();
            app.updateWorkspace();
        }
    }
    
    public void handleSaveAs(){
        fileManager.saveAs();
    }
    
    public void handleSave(){
        fileManager.save();
    }
    
    public void handleLoad(){
        boolean success = fileManager.load();
        if(success){
            app.loadWorkspace();
            itemList = dataManager.getItemList();
            connectorList = dataManager.getConnectorList();
            for(JItem item : itemList){
                item.updateText(item.getClassName());
                item.updateFields();
            }
            app.clearWorkspace();
            app.updateWorkspace();
        }
    }
    
    public void handleExportCode(){
        fileManager.exportCode();
    }
    
    public void handleShowGrid(){
        app.getGrid().setVisible(app.getShowGridCheck().isSelected());
    }
    
    public String checkName(){
        String name = selectedItem.getClassName();
        itemList = dataManager.getItemList();
        
        for(JItem item : itemList){
            if(!item.equals(selectedItem)){
                if(item.getClassName().equals(name)){
                    DuplicateNameDialog dialog = null;
                    boolean validName = false;
                    while(!validName){
                        dialog = new DuplicateNameDialog();
                        dialog.showAndWait();
                        
                        name = dialog.getName();
                        if(!item.getClassName().equals(name)) validName = true;
                        if(name.trim().length() == 0) validName = false;
                    }
                }
            }
        }       
        return name;
    }
}
