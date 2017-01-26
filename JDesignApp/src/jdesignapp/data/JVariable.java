/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

/**
 *
 * @author Alden
 */
public class JVariable {
    
    private String variableName;
    private String variableType;
    private String accessType;
    private boolean isStatic;

    public JVariable(){
        variableName = "";
        variableType = "";
        accessType = "";
        isStatic = false;
    }
    
    public JVariable(String accessType, String variableType, String variableName){
        this.accessType = accessType;
        this.variableName = variableName;
        this.variableType = variableType;
        isStatic = false;
    }
    
    public String getUMLAccess(){
        if(accessType.equals("public")) return "+";
        if(accessType.equals("private")) return "-";
        if(accessType.equals("protected")) return "#";
        return "~";
    }
    /**
     * @return the variableName
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * @param variableName the variableName to set
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * @return the variableType
     */
    public String getVariableType() {
        return variableType;
    }

    /**
     * @param variableType the variableType to set
     */
    public void setVariableType(String variableType) {
        this.variableType = variableType;
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
    
    
}
