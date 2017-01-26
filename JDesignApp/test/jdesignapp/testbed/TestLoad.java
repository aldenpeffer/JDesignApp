/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.testbed;

import jdesignapp.testbed.*;
import java.util.ArrayList;
import jdesignapp.data.DataManager;
import jdesignapp.data.JConnector;
import jdesignapp.data.JItem;
import jdesignapp.data.JMethod;
import jdesignapp.data.JVariable;
import jdesignapp.file.FileManager;

/**
 *
 * @author Alden
 */
public class TestLoad {
    
    public static void main(String[] args){
        DataManager dataManager = new DataManager();
        FileManager fileManager = new FileManager(dataManager);
        ArrayList<JItem> itemList;
        ArrayList<JConnector> connectorList;
        boolean success =  fileManager.load();
        if(success){
            String tab = "  ";
            itemList = dataManager.getItemList();
            connectorList = dataManager.getConnectorList();
            
            for(JItem item : itemList){
                System.out.println("Class Name: " + item.getClassName());
                System.out.println("Package Name: " + item.getPackageName());
                System.out.println("Parent Name: " + item.getParentName());
                System.out.println("IsInterface: " + item.isInterface());
                System.out.println("IsAbstract: " + item.isAbstract());
                System.out.println("X: " + item.getX());
                System.out.println("Y: " + item.getY());
                System.out.println("Methods: ");
                System.out.println(tab + "-----------------------");
                for(JMethod method : item.getMethods()){
                    System.out.println(tab + "Method Name: " + method.getMethodName());
                    System.out.println(tab + "Access Type: " + method.getAccessType());
                    System.out.println(tab + "IsStatic: " + method.getIsStatic());
                    System.out.println(tab + "IsAbstract: " + method.getIsAbstract());
                    System.out.println(tab + "Return Type: " + method.getReturnType());
                    System.out.println(tab + "Arguments: ");
                    for(String argType : method.getMethodArgTypes()){
                        int index = method.getMethodArgTypes().indexOf(argType);
                        System.out.println(tab + tab + "Arg Name: " + method.getMethodArgNames().get(index));
                        System.out.println(tab + tab + "Arg Type: " + argType);
                    }
                    System.out.println(tab + "-----------------------");
                }
                System.out.println("Variables: ");
                System.out.println(tab + "-----------------------");
                for(JVariable variable : item.getVariables()){
                    System.out.println(tab + "Variable Name: " + variable.getVariableName());
                    System.out.println(tab + "Variable Type: " + variable.getVariableType());
                    System.out.println(tab + "IsStatic: " + variable.getIsStatic());
                    System.out.println(tab + "Access Type: " + variable.getAccessType());
                    System.out.println(tab + "-----------------------");
                }  
                System.out.println("_________________________________");
            }
            
            for(JConnector connector : connectorList){
                System.out.println("\nParent1 Name: " + connector.getFirstParentName());
                System.out.println("Parent2 Name: " + connector.getSecondParentName());
                System.out.println("StartX: " + connector.getStartX());
                System.out.println("StartY: " + connector.getStartX());
                System.out.println("EndX: " + connector.getEndX());
                System.out.println("EndY: " + connector.getEndX());
                System.out.println("Thickness: " + connector.getStrokeWidth());
                System.out.println("Color: " + connector.getStroke().toString());
                System.out.println("_________________________________");
            }
        }
    }
    
}
