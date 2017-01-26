/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Alden
 */
public class JItem extends Rectangle{
    
    private String packageName;
    private ArrayList<JVariable> variables;
    private ArrayList<JMethod> methods;
    private ArrayList<String> parents;
    private ArrayList<Rectangle> lines;
    private ArrayList<Rectangle> resizeMarkers;
    private Rectangle nameVarLine;
    private Rectangle varMethLine;
    private ArrayList<Text> itemText;
    private ArrayList<Text> varText;
    private ArrayList<Text> methText;
    private boolean isInterface;
    private boolean isAbstract;
    private double maxWidth;
    private double resizeWidth = -1;
    private double resizeHeight = -1;
    //Text height is 16px
    
    public JItem(boolean isInterface){
        this.isInterface = isInterface;
        methods = new ArrayList();
        variables = new ArrayList(); 
        varText = new ArrayList();
        methText = new ArrayList();
        itemText = new ArrayList();
        parents = new ArrayList();
        lines = new ArrayList();
        resizeMarkers = new ArrayList();
        itemText.add(new Text(""));    
        if(isInterface) itemText.add(new Text("<<interface>>"));
        isAbstract = false;
        packageName = "";
        maxWidth = 80;
        
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        this.setX(330);
        this.setY(320);
        this.setWidth(maxWidth);
        this.setHeight(154);
        nameVarLine = new Rectangle(getX(), getY() + 60.0, getWidth(), 1);
        nameVarLine.setFill(Color.BLACK);
        varMethLine = new Rectangle(getX(), getY() + varText.size() * 18 +  106, getWidth(), 1);
        varMethLine.setFill(Color.BLACK);        
        lines.add(nameVarLine);
        lines.add(varMethLine);
        
        updateWidth();
        updateFields();
    }

    public void showResizeMarkers(boolean show){
        if(show){
            Rectangle topLeft = new Rectangle(getX() - 5, getY() - 5, 10, 10);
            Rectangle topCenter = new Rectangle(getX() + (getWidth()/2) - 5, getY() - 5, 10, 10);
            Rectangle topRight = new Rectangle(getX() + getWidth() - 5, getY() - 5, 10, 10);
            Rectangle leftCenter = new Rectangle(getX() - 5, getY() + (getHeight()/2) - 5, 10, 10);
            Rectangle rightCenter = new Rectangle(getX() + getWidth() - 5, getY() + (getHeight()/2) - 5, 10, 10);
            Rectangle bottomLeft = new Rectangle(getX() - 5, getY() + getHeight() - 5, 10, 10);
            Rectangle bottomCenter = new Rectangle(getX() + (getWidth()/2) - 5, getY() + getHeight() - 5, 10, 10);
            Rectangle bottomRight = new Rectangle(getX() + getWidth() - 5, getY() + getHeight() - 5, 10, 10);
            resizeMarkers.add(topLeft);
            resizeMarkers.add(topCenter);
            resizeMarkers.add(topRight);
            resizeMarkers.add(leftCenter);
            resizeMarkers.add(rightCenter);
            resizeMarkers.add(bottomLeft);
            resizeMarkers.add(bottomCenter);
            resizeMarkers.add(bottomRight);
            for(Rectangle r : resizeMarkers){
                r.setFill(Color.PURPLE);
            }
        }else{
            resizeMarkers.clear();
        }
    }
    
    public void updateResizeMarkers(){
        //topleft
        resizeMarkers.get(0).setX(getX() - 5);
        resizeMarkers.get(0).setY(getY() - 5);
        //topcenter
        resizeMarkers.get(1).setX(getX() + (getWidth()/2) - 5);
        resizeMarkers.get(1).setY(getY() - 5);
        //topright
        resizeMarkers.get(2).setX(getX() + getWidth() - 5);
        resizeMarkers.get(2).setY(getY() - 5);
        //leftcenter
        resizeMarkers.get(3).setX(getX() - 5);
        resizeMarkers.get(3).setY(getY() + (getHeight()/2) - 5);
        //rightcenter
        resizeMarkers.get(4).setX(getX() + getWidth() - 5);
        resizeMarkers.get(4).setY(getY() + (getHeight()/2) - 5);
        //bottomleft
        resizeMarkers.get(5).setX(getX() - 5);
        resizeMarkers.get(5).setY(getY() + getHeight() - 5);
        //bottomcenter
        resizeMarkers.get(6).setX(getX() + (getWidth()/2) - 5);
        resizeMarkers.get(6).setY(getY() + getHeight() - 5);
        //bottomright
        resizeMarkers.get(7).setX(getX() + getWidth() - 5);
        resizeMarkers.get(7).setY(getY() + getHeight() - 5);
        
    }
    
    public void updateWidth(){     
        //Offset for classtext is 20 on each side, totaling 40
        //Offset for var and meth text is 15 on each side, totaling 30
        double greatest = 0;
        for(Text t: itemText){
            if(t.getLayoutBounds().getWidth() + 40 > greatest) greatest = t.getLayoutBounds().getWidth() + 40;
        }
        
        for(Text t : varText){
            if(t.getLayoutBounds().getWidth() + 30 > greatest) greatest = t.getLayoutBounds().getWidth() + 30;
        }
        for(Text t : methText){
            if(t.getLayoutBounds().getWidth() + 30 > greatest) greatest = t.getLayoutBounds().getWidth() + 30;
        }
        
        if(greatest < 80){
            this.setWidth(80);
        }else if(this.getWidth() < greatest){
            this.setWidth(greatest);
        }else if(this.getWidth() > greatest){
            this.setWidth(greatest);
        }        
        
        if(resizeWidth > greatest){
            this.setWidth(resizeWidth);
        }
        
        if(resizeMarkers.size() > 0){
            updateResizeMarkers();
        }
    }
    
    public void updateHeight(){
        //Might be buggy
        double varOffset = (varText.size() - 1) * 18;
        double methOffset = (methText.size() - 1) * 18;
        if(varOffset < 0) varOffset = 0;
        if(methOffset < 0) methOffset = 0;
        
        setHeight(154 + methOffset + varOffset);
        if(resizeHeight > 154 + methOffset + varOffset) this.setHeight(resizeHeight);
    }

    public void updateText(String val){
        itemText.get(0).setText(val);
        updateWidth();
        updateFields();
    }
    
    public void updateFields(){
        nameVarLine.setX(getX());
        nameVarLine.setY(getY() + 60.0);
        nameVarLine.setWidth(getWidth());
        double offset = varText.size() - 1;
        if(offset < 0) offset = 0;
        varMethLine.setX(getX());
        varMethLine.setY(getY() + (offset) * 18 +  106);
        varMethLine.setWidth(getWidth());
        
        int z = itemText.size(); 
        for(int i = 0; i < z; i++){
            Text temp = itemText.get(i);
            if(i == 0){
                temp.setX(getX() + 20);
            }else{
                double interfaceWidth = temp.getLayoutBounds().getWidth();
                temp.setX(getX() + (this.getWidth() - interfaceWidth)/2);
            }
            temp.setY(getY() + 30 + (i * 18));
        }
        
        z = varText.size();        
        for(int i = 0; i < z; i++){
            Text temp = varText.get(i);
            temp.setX(getX() + 15);
            temp.setY(getY() + (nameVarLine.getY() - this.getY()) + 24 + (i * 18));
        }
        
        z = methText.size();        
        for(int i = 0; i < z; i++){
            Text temp = methText.get(i);
            temp.setX(getX() + 15);
            temp.setY(getY() + (varMethLine.getY() - this.getY()) + 24 + (i * 18));
        }
        
        updateHeight();
    }
    
    public void addVariableText(JVariable variable){
        String text = "";
        text += variable.getUMLAccess();
        text += " " + variable.getVariableName();
        text += " : " + variable.getVariableType();
        Text vtext = new Text(text);
        if(variable.getIsStatic()) vtext.setUnderline(true);
        vtext.setX(getX() + 15);
        vtext.setY(getY() + nameVarLine.getY() + 24 + (varText.size() * 18));
        varText.add(vtext);
        
        updateHeight();
        updateWidth();    
        updateFields();
    }
    
    public void editVariable(JVariable variable, String previousName){
        for(JVariable v : variables){
            if(v.getVariableName().equals(previousName)){
                v.setVariableName(variable.getVariableName());
                v.setAccessType(variable.getAccessType());
                v.setIsStatic(variable.getIsStatic());
                v.setVariableType(variable.getVariableType());
            }
        }       
        
        for(Text t : varText){
            if(t.getText().substring(2, t.getText().indexOf(":") - 1).equals(previousName)){
                String text = "";
                text += variable.getUMLAccess();
                text += " " + variable.getVariableName();
                text += " : " + variable.getVariableType();
                t.setText(text);
                t.setUnderline(variable.getIsStatic());
            }
        }
        updateWidth();    
        updateFields();       
    }
    
    public void editMethod(JMethod method, String previousName, String previousReturnType, String previousArgsList){
        for(JMethod m : methods){
            if(m.getMethodName().equals(previousName)){
                if(m.getReturnType().equals(previousReturnType)){
                    if(m.getArgsList().equals(previousArgsList)){
                        m.setMethodName(method.getMethodName());
                        m.setReturnType(method.getReturnType());
                        m.setAccessType(method.getAccessType());
                        m.setIsStatic(method.getIsStatic());
                        m.setIsAbstract(method.getIsAbstract());

                        int z = method.getMethodArgNames().size(); 
                        m.clearArgs();
                        for(int i = 0; i < z; i++){
                            m.addArgument(method.getMethodArgTypes().get(i), method.getMethodArgNames().get(i));
                        }  
                    }
                }
            }
        }
        boolean nameMatch = false;
        boolean argsMatch = false;
        boolean returnMatch = false;
        for(Text t : methText){
            nameMatch = false;
            argsMatch = false;
            returnMatch = false;
            String textContent = t.getText();
            
            //System.out.println(textContent.substring(2, textContent.indexOf("(")) + "vs" + previousName);
            if(textContent.substring(2, textContent.indexOf("(")).equals(previousName)){
                nameMatch = true;
            }
            //System.out.println(textContent.substring(textContent.indexOf("(") + 1, textContent.indexOf(")")) + "vs" + getUMLMethodArgFormat(previousArgsList));
            if(nameMatch){
                if(textContent.substring(textContent.indexOf("(") + 1, textContent.indexOf(")")).equals(getUMLMethodArgFormat(previousArgsList))){
                    argsMatch = true;
                }
            }          
            //System.out.println(textContent.substring(textContent.indexOf(")") + 3) + "vs" + previousReturnType);
            if(nameMatch && argsMatch){
                if(textContent.substring(textContent.indexOf(")") + 3, textContent.length()).equals(previousReturnType)){
                    returnMatch = true;
                }
            }
            
            if(nameMatch && argsMatch && returnMatch){
                String text = "";
                text += method.getUMLAccess();
                text += " " + method.getMethodName();
                text += "(";
                int z = method.getMethodArgNames().size();
                for(int i = 0; i < z; i++){
                    text+= method.getMethodArgNames().get(i) + ": ";
                    text+= method.getMethodArgTypes().get(i);
                    if(!(i == z - 1)) text += ", ";
                }
                text += ")";
                if(method.getReturnType().trim().length() > 0) text += ": " + method.getReturnType();
                t.setText(text);
                t.setUnderline(method.getIsStatic());
            }       
        }
        updateWidth();    
        updateFields();
    }
    
    public void addMethodText(JMethod method){
        String text = "";
        text += method.getUMLAccess();
        text += " " + method.getMethodName();
        text += "(";
        int z = method.getMethodArgNames().size();
        for(int i = 0; i < z; i++){
            text+= method.getMethodArgNames().get(i) + ": ";
            text+= method.getMethodArgTypes().get(i);
            if(!(i == z - 1)) text += ", ";
        }
        text += ")";
        if(method.getReturnType().trim().length() > 0) text += ": " + method.getReturnType();
        Text mtext = new Text(text);       
        if(method.getIsStatic()) mtext.setUnderline(true);
        mtext.setX(getX() + 15);
        mtext.setY(getY() + varMethLine.getY() + 24 + (methText.size() * 18));
        methText.add(mtext);
        
        updateHeight();
        updateWidth();    
        updateFields();
    }
    
    public void removeVariable(JVariable variable){
        JVariable toRemove = null;
        Text textToRemove = null;
        for(JVariable v : variables){
            if(v.getVariableName().equals(variable.getVariableName())) toRemove = v;
        }
        variables.remove(toRemove);
        for(Text t : varText){
            if(t.getText().substring(2, t.getText().indexOf(":") - 1).equals(variable.getVariableName()))  textToRemove = t;
        }
        varText.remove(textToRemove);
        
        updateHeight();
        updateWidth();    
        updateFields();
    }
    
    public void removeMethod(JMethod method){
        JMethod toRemove = null;
        Text textToRemove = null;  
        boolean nameMatch = false;
        boolean argsMatch = false;
        boolean returnMatch = false;
        
        for(JMethod m : methods){
            nameMatch = false;
            argsMatch = false;
            if(m.getMethodName().equals(method.getMethodName())) nameMatch = true;
            if(nameMatch){
                if(m.getArgsString().equals(method.getArgsString())) argsMatch = true;
                    if(m.getReturnType().equals(method.getReturnType())) returnMatch = true;
            }
            
            if(nameMatch && argsMatch && returnMatch) toRemove = m;
        }
        methods.remove(toRemove);     
        
        for(Text t : methText){
            nameMatch = false;
            argsMatch = false;
            returnMatch = false;
            String textContent = t.getText();

            if(textContent.substring(2, textContent.indexOf("(")).equals(method.getMethodName())) nameMatch = true;
            if(nameMatch){
                if(textContent.substring(textContent.indexOf("(") + 1, textContent.indexOf(")")).equals(method.getArgsTextComparitor())) argsMatch = true;
            }            
            if(nameMatch && argsMatch){
                if(textContent.substring(textContent.indexOf(")") + 3, textContent.length()).equals(method.getReturnType())) returnMatch = true;
            }
            
            if(nameMatch && argsMatch && returnMatch) textToRemove = t;       
        }        
        methText.remove(textToRemove);
        
        updateHeight();
        updateWidth();    
        updateFields();
    }
    
    public void setResizeWidth(double wid){
        resizeWidth = wid;
    }
    
    public void setResizeHeight(double hei){
        resizeHeight = hei;
    }
    
    public double getResizeWidth(){
        return resizeWidth;
    }
    
    public double getResizeHeight(){
        return resizeHeight;
    }
    
    public void setIsAbstract(boolean isAbstract){
        this.isAbstract = isAbstract;
        if(isInterface) isAbstract = false;
    }
    
    public boolean isAbstract(){
        return isAbstract;
    }
    
    public JVariable getVariableByName(String name){
        for(JVariable variable : variables){
            if(variable.getVariableName().equals(name)){
                return variable;
            }
        }
        return null;
    }
    
    public JMethod getMethodByName(String name){
        for(JMethod method : methods){
            if(method.getMethodName().equals(name)){
                return method;
            }
        }
        return null;
    }
    
    public String getUMLMethodArgFormat(String toParse){
        if(toParse.trim().length() > 0){
            String text = toParse.replace(",", "");
            String[] array = text.split(" ");
            ArrayList<String> names = new ArrayList();
            ArrayList<String> types = new ArrayList();

            for(int i = 0; i < array.length; i++){
                if(i%2 == 0) names.add(array[i]);
                if(i%2 == 1) types.add(array[i]);
            }

            String formatted = "";
            int z = names.size();
            for(int i = 0; i < z; i++){
                formatted += types.get(i) + ": " + names.get(i);
                if(i != z - 1) formatted += ", ";
            }
            return formatted;
        }
        return "";
    }
    
    /**
     * @return the className
     */
    public String getClassName() {
        return itemText.get(0).getText();
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        itemText.get(0).setText(className);
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ArrayList<Rectangle> getLines(){
        return lines;
    }
    
    public ArrayList<Rectangle> getResizeMarkers(){
        return resizeMarkers;
    }
    
    public ArrayList<Text> getText(){
        ArrayList<Text> allText = new ArrayList();
        allText.addAll(itemText);
        allText.addAll(varText);
        allText.addAll(methText);
        return allText;
    }
    
    public ArrayList<JVariable> getVariables(){
        return variables;
    }
    
    public ArrayList<JMethod> getMethods(){
        return methods;
    }
    
    public void setVariables(ArrayList<JVariable> varList){
        variables = varList;
    }
    
    public void setMethods(ArrayList<JMethod> methodList){
        methods = methodList;
    }
     
    public boolean isInterface(){
        return isInterface;
    }
    
    public void addVariable(JVariable v){
        variables.add(v);
    }
    
    public void addMethod(JMethod m){
        methods.add(m);
    }
    
    public void addParent(String s){
        parents.add(s);
    }
    
    public void removeParent(String s){
        if(parents.contains(s)) parents.remove(s);
    }
    
    public void setParents(ArrayList<String> parentList){
        parents = parentList;
    }
    
    public ArrayList<String> getParents(){
        return parents;
    }
    
    public void setParentName(String s){
        return;
    }
    
    public String getParentName(){
        return "Parent_Name";
    }
}
