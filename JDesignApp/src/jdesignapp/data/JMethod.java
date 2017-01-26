/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Alden
 */
public class JMethod {
    
    private String methodName;
    private String returnType;
    private String accessType;
    private String argsString;
    private ArrayList<String> methodArgNames;
    private ArrayList<String> methodArgTypes;  
    private ArrayList<String> methodArgs;  
    private boolean isStatic;
    private boolean isAbstract;

    public JMethod(){
        methodName = "";
        returnType = "";
        accessType = "";
        argsString = "{}";
        methodArgNames = new ArrayList();
        methodArgTypes = new ArrayList();
        methodArgs = new ArrayList();
        isStatic = false;
        isAbstract = false;
    }
    
    public JMethod(String accessType, String returnType, String methodName){
        this.accessType = accessType;
        this.returnType = returnType;
        this.methodName = methodName;
        argsString = "{}";
        methodArgNames = new ArrayList();
        methodArgTypes = new ArrayList();
        methodArgs = new ArrayList();
        isStatic = false;
        isAbstract = false;
    }
    
    public JMethod(String accessType, String constructorName){
        this.accessType = accessType;
        methodName = constructorName;
        returnType = "";
        argsString = "{}";
        methodArgNames = new ArrayList();
        methodArgTypes = new ArrayList();
        methodArgs = new ArrayList();
        isStatic = false;
        isAbstract = false;
    }
    
    public void clearArgs(){
        methodArgNames.clear();
        methodArgTypes.clear();
        methodArgs.clear();
        argsString = "{}";
    }
    
    public String getUMLAccess(){
        if(accessType.equals("public")) return "+";
        if(accessType.equals("private")) return "-";
        if(accessType.equals("protected")) return "#";
        return "~";
    }
    
    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the accessType
     */
    public String getAccessType() {
        return accessType;
    }

    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    /**
     * @return the methodArgNames
     */
    public ArrayList<String> getMethodArgNames() {
        return methodArgNames;
    }

    /**
     * @param methodArgNames the methodArgNames to set
     */
    public void setMethodArgNames(ArrayList<String> methodArgNames) {
        this.methodArgNames = methodArgNames;
    }

    /**
     * @return the methodArgTypes
     */
    public ArrayList<String> getMethodArgTypes() {
        return methodArgTypes;
    }

    /**
     * @param methodArgTypes the methodArgTypes to set
     */
    public void setMethodArgTypes(ArrayList<String> methodArgTypes) {
        this.methodArgTypes = methodArgTypes;
    }

    /**
     * @return the isStatic
     */
    public boolean getIsStatic() {
        return isStatic;
    }

    /**
     * @param isStatic the isStatic to set
     */
    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    /**
     * @return the isAbstract
     */
    public boolean getIsAbstract() {
        return isAbstract;
    }

    /**
     * @param isAbstract the isAbstract to set
     */
    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }
    
    public void addArgument(String type, String name){
        methodArgTypes.add(type);
        methodArgNames.add(name);
        methodArgs.add(type + " " + name);
        argsString = "{";
        for(String s : methodArgs){
            argsString += s;
            if(!(methodArgs.indexOf(s) == methodArgs.size() - 1)) argsString += ", ";
        }
        argsString += "}";

    }
    
    public ArrayList<String> getArgs(){
        return methodArgs;
    }
    
    public String getArgsString(){
        return argsString;
    }
    
    public String getArgsList(){
        String argsList = argsString;
        argsList = argsList.substring(1, argsList.length() - 1);
        argsList = argsList.trim();
        return argsList;
    }
    
    public String getArgsTextComparitor(){
        String text = "";
        int z = getMethodArgNames().size();
        for(int i = 0; i < z; i++){
            text+= getMethodArgNames().get(i) + ": ";
            text+= getMethodArgTypes().get(i);
            if(!(i == z - 1)) text += ", ";
        }
        return text;
    }
}
