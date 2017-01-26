/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jdesignapp.JDesignApp;
import jdesignapp.data.DataManager;
import jdesignapp.data.JConnector;
import jdesignapp.data.JItem;
import jdesignapp.data.JMethod;
import jdesignapp.data.JVariable;

/**
 *
 * @author Alden
 */
public class FileManager {
    
    File saveFile;
    boolean saved;
    JDesignApp app;
    DataManager dataManager;
    ArrayList<JItem> itemList;
    
    
    public FileManager(JDesignApp app){
        this.app = app;
        dataManager = app.getDataManager();
        saved = false;
        saveFile = null;
    }
    
    //For testing
    public FileManager(DataManager dataManager){
        this.dataManager = dataManager;
        app = null;
        saved = false;
        saveFile = null;
    }
    
    public void save(){     
        if(saved){
            writeProject();
        }else{
            saveAs();
        }
    }
    
    public void saveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Project");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JDesign Projects", "*.json"));

        if(app != null){
            saveFile = fileChooser.showSaveDialog(app.getStage());
        }else{
            saveFile = new File("./json/DesignSaveTest.json");
        }

        if (saveFile != null) {
            writeProject();
            saved = true;
        }else{
            saved = false;
        }  
    }
    
    public void writeProject(){
        //Save stuff here
            OutputStream os = null;    
            try {
                
                StringWriter sw = new StringWriter();
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                
                //Save all items (classes/interfaces)
                itemList = dataManager.getItemList();
                for(JItem item : itemList){
                    ArrayList<JMethod> methodList = item.getMethods();     
                    ArrayList<JVariable> variableList = item.getVariables();
                    JsonArrayBuilder methodArray = Json.createArrayBuilder();
                    JsonArrayBuilder variableArray = Json.createArrayBuilder();
                    
                    //Methods
                    for(JMethod method : methodList){
                        JsonArrayBuilder methodArgs = Json.createArrayBuilder();
                        for(String argName : method.getMethodArgNames()){
                            int check = method.getMethodArgNames().indexOf(argName);
                            JsonObject methodArgObject = Json.createObjectBuilder()
                                    .add("ArgType", method.getMethodArgTypes().get(check))
                                    .add("ArgName", argName)
                                    .build();
                            methodArgs.add(methodArgObject);
                        }
                        
                        JsonObject methodsObject = Json.createObjectBuilder()                          
                            .add("AccessType", method.getAccessType())
                            .add("IsStatic", method.getIsStatic())
                            .add("IsAbstract", method.getIsAbstract())    
                            .add("ReturnType", method.getReturnType())
                            .add("MethodName", method.getMethodName())
                            .add("MethodArgs", methodArgs)    
                            .build();
                        methodArray.add(methodsObject);                              
                    }
                    //Variables
                    for(JVariable variable : variableList){                        
                        JsonObject variablesObject = Json.createObjectBuilder()                          
                            .add("AccessType", variable.getAccessType())
                            .add("IsStatic", variable.getIsStatic())
                            .add("VariableType", variable.getVariableType())
                            .add("VariableName", variable.getVariableName())    
                            .build();
                        variableArray.add(variablesObject);                              
                    }
                    
                    JsonObject jitemObject = Json.createObjectBuilder()
                            .add("ClassName", item.getClassName())
                            .add("PackageName", item.getPackageName())
                            .add("ParentName", item.getParentName())
                            .add("IsInterface", item.isInterface())
                            .add("IsAbstract", item.isAbstract())
                            .add("X", item.getX())
                            .add("Y", item.getY())
                            .add("Methods", methodArray)
                            .add("Variables", variableArray)
                            .build();
                    arrayBuilder.add(jitemObject);
                }
                //Array storing all classes/interfaces
                JsonArray jItemsArray = arrayBuilder.build();
                
                //Save all connectors
                JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                ArrayList<JConnector> connectorList = dataManager.getConnectorList();
                for(JConnector connector : connectorList){
                    JsonObject jConnectorObject = Json.createObjectBuilder()
                            .add("Parent1Name", connector.getFirstParentName())
                            .add("Parent2Name", connector.getSecondParentName())
                            .add("StartX", connector.getStartX())
                            .add("StartY", connector.getStartY())
                            .add("EndX", connector.getEndX())  
                            .add("EndY", connector.getEndY())
                            .add("Thickness", connector.getStrokeWidth())
                            .add("Color", connector.getStroke().toString())
                            .build();
                    arrayBuilder2.add(jConnectorObject);
                }
                //Array storing all connectors
                JsonArray jConnectorArray = arrayBuilder2.build();
                
                JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add("jItems", jItemsArray)
                    .add("jConnectors", jConnectorArray)
                    .build();
                Map<String, Object> properties = new HashMap<>(1);
                properties.put(JsonGenerator.PRETTY_PRINTING, true);
                JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
                JsonWriter jsonWriter = writerFactory.createWriter(sw);
                jsonWriter.writeObject(dataManagerJSO);
                jsonWriter.close();
                os = new FileOutputStream(saveFile);
                JsonWriter jsonFileWriter = Json.createWriter(os);
                jsonFileWriter.writeObject(dataManagerJSO);
                String prettyPrinted = sw.toString();
                PrintWriter pw = new PrintWriter(saveFile);
                pw.write(prettyPrinted);
                pw.close();
                
                //For testing
                if(app != null){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Saved");
                    alert.setContentText("Project saved successfully.");                  
                    alert.show();
                }               
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
    }
    
    public boolean load(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Project");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("JDesign Projects", "*.json"));

        File loadFile = null;
        if(app != null){
            loadFile = fileChooser.showOpenDialog(app.getStage());
        }else{
            loadFile = new File("./json/DesignSaveTest.json");
        }
        
        if(loadFile != null){
            try{
                ArrayList<JItem> itemList = new ArrayList();
                ArrayList<JConnector> connectorList = new ArrayList();
                
                //json: object holding everything
                JsonObject json = loadJSONFile(loadFile);
                //jItemsArray: object holding array of JItems
                JsonArray jItemsArray = json.getJsonArray("jItems");
                for(int i = 0; i < jItemsArray.size(); i++){
                    //itemJso : current JItem being handled
                    JsonObject itemJso = jItemsArray.getJsonObject(i);
                    JItem item = new JItem(itemJso.getBoolean("IsInterface"));
                    item.setClassName(itemJso.getString("ClassName"));
                    item.setPackageName(itemJso.getString("PackageName"));
                    item.setParentName(itemJso.getString("ParentName"));
                    item.setIsAbstract(itemJso.getBoolean("IsAbstract"));
                    item.setX(itemJso.getInt("X"));
                    item.setY(itemJso.getInt("Y"));
                    
                    //methodArray : object holding array of methods within this JItem
                    JsonArray methodArray = itemJso.getJsonArray("Methods");
                    for(int j = 0; j < methodArray.size(); j++){
                        JsonObject methodJso = methodArray.getJsonObject(j);
                        JMethod method = new JMethod();
                        method.setAccessType(methodJso.getString("AccessType"));
                        method.setIsStatic(methodJso.getBoolean("IsStatic"));
                        method.setIsAbstract(methodJso.getBoolean("IsAbstract"));
                        method.setReturnType(methodJso.getString("ReturnType"));
                        method.setMethodName(methodJso.getString("MethodName"));
                        
                        //methodArgsArray: object holding array of method arguments within this JMethod
                        JsonArray argsArray = methodJso.getJsonArray("MethodArgs");
                        for(int z = 0; z < argsArray.size(); z++){
                            JsonObject argsJso = argsArray.getJsonObject(z);
                            method.addArgument(argsJso.getString("ArgType"), argsJso.getString("ArgName"));
                        }
                        //Add the method to the item, which has had its data fields set to the loaded data
                        item.addMethod(method);
                    }
                    
                    //variableArray : object holding array of variables within this JItem
                    JsonArray variableArray = itemJso.getJsonArray("Variables");
                    for(int j = 0; j < variableArray.size(); j++){
                        JsonObject variableJso = variableArray.getJsonObject(j);
                        JVariable variable = new JVariable();
                        variable.setAccessType(variableJso.getString("AccessType"));
                        variable.setIsStatic(variableJso.getBoolean("IsStatic"));
                        variable.setVariableType(variableJso.getString("VariableType"));
                        variable.setVariableName(variableJso.getString("VariableName"));
                        
                        //Add the variable to the item, which has had its data fields set to the loaded data
                        item.addVariable(variable);
                    }
                    //Add the item to the item list
                    itemList.add(item);
                }
                
                //jConnectorsArray: object holding array of JItems
                JsonArray jConnectorsArray = json.getJsonArray("jConnectors");
                for(int i = 0; i < jConnectorsArray.size(); i++){
                    //connectorJso : current JItem being handled
                    JsonObject connectorJso = jConnectorsArray.getJsonObject(i);
                    JConnector connector = new JConnector();
                    connector.setFirstParentName(connectorJso.getString("Parent1Name"));
                    connector.setSecondParentName(connectorJso.getString("Parent2Name"));
                    connector.setStartX(connectorJso.getInt("StartX"));
                    connector.setStartY(connectorJso.getInt("StartY"));
                    connector.setEndX(connectorJso.getInt("EndX"));
                    connector.setEndY(connectorJso.getInt("EndY"));
                    connector.setStroke(Color.web(connectorJso.getString("Color")));
                    connector.setStrokeWidth(connectorJso.getInt("Thickness"));
                    
                    //Add the connector to the connector list 
                    connectorList.add(connector);
                }
                
                dataManager.setItemList(itemList);
                dataManager.setConnectorList(connectorList);
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    
    private static JsonObject loadJSONFile(File file) throws IOException {
	InputStream is = new FileInputStream(file);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
   /* public void exportCode(){
        File file = new File("./src/work");
        itemList = dataManager.getItemList();
       
        //Make all the packages
        for(JItem item : itemList){                
            createPackage(item.getPackageName(), "./src/work/");
        }    
        //Make all the classes
        for(JItem item : itemList){
            createClass(item, "./src/work/");
        }
    }
    
    public void createPackage(String currentPackage, String fileLocation){
        File file = new File(fileLocation + currentPackage);
        
        if(currentPackage.length() >= 3 && 
                currentPackage.substring(0, 3).equals("org")){
            return;
        }
        if(currentPackage.length() >= 4 &&
                currentPackage.substring(0, 4).equals("java")){
            return;
        }
        
        if(currentPackage.contains(".")){
            String[] parts = currentPackage.split("\\.");
            String partCombiner = "";
            for(String s : parts){
                partCombiner += s;
                File tempFile = new File(fileLocation + partCombiner);
                System.out.println(fileLocation + partCombiner);
                if (!tempFile.exists()){
                    tempFile.mkdir();
                }
                partCombiner += "/";
            }
        }else{
            File tempFile = new File(fileLocation + currentPackage);
            if (!tempFile.exists()) {
                tempFile.mkdir();
            }
        }
    }
    
    public void createClass(JItem item, String fileLocation){
        FileWriter fileWriter = null; 
        String tab = "    ";
        String endl = "\n";
        
        
        if(item.getPackageName().length() >= 3 && 
                item.getPackageName().substring(0, 3).equals("org")){
            return;
        }
        if(item.getPackageName().length() >= 4 &&
                item.getPackageName().substring(0, 4).equals("java")){
            return;
        }
        
        try {       
            String packageLocation = "";
            if(item.getPackageName().trim().length() > 0){
                packageLocation = item.getPackageName().replace(".", "/");
            }
            
            //Makes the file if and only if it doesn't exist
            File file = new File(fileLocation + packageLocation + "/" + item.getClassName() + ".java");        
            
            
            fileWriter = new FileWriter(fileLocation + packageLocation + "/" + item.getClassName() + ".java");
                      
            //SAVE EVERYTHING HERE
            if(item.getPackageName().trim().length() > 0){
                fileWriter.write("package " + item.getPackageName() + ";" + endl + endl);
            }
            
            itemList = dataManager.getItemList();
            ArrayList<String> imports = new ArrayList();
            for(JMethod method : item.getMethods()){
                for(JItem compareItem : itemList){
                    if(method.getReturnType().equals(compareItem.getClassName())){
                        if(compareItem.getPackageName().trim().length() > 0){
                           if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                           }
                        }
                    }         
                    for(String argType : method.getMethodArgTypes()){
                        if(argType.equals(compareItem.getClassName())){
                            if(compareItem.getPackageName().trim().length() > 0){
                                if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                    imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                                }
                            }
                        }
                    }
                }
            }
            for(JVariable variable : item.getVariables()){
                for(JItem compareItem : itemList){
                    if(variable.getVariableType().equals(compareItem.getClassName())){
                        if(compareItem.getPackageName().trim().length() > 0){
                           if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                           }
                        }
                    }
                }
            }
            JItem parentItem = null;
            if(item.getParentName().trim().length() > 0){
                for(JItem tempItem : itemList){
                    if(tempItem.getClassName().equals(item.getParentName())){
                        parentItem = tempItem;
                        imports.add("import " + parentItem.getPackageName() + "." + parentItem.getClassName() + ";" + endl);
                    }
                }
            }
            
            for(String s : imports){
                fileWriter.write(s + endl);
            }
            
            //If its an interface
            if(item.isInterface()){
                //Has a parent
                if(parentItem != null){
                    fileWriter.write("\npublic interface " + item.getClassName() + " extends" + parentItem.getClassName() + " {" + endl);
                }else{
                    fileWriter.write("\npublic interface " + item.getClassName() + " {" + endl);
                }
            }else{
                if(parentItem != null){
                    if(parentItem.isInterface()){
                        fileWriter.write("\npublic class " + item.getClassName() + " implements " + parentItem.getClassName() + " {" + endl);
                    }else{
                        fileWriter.write("\npublic class " + item.getClassName() + " extends " + parentItem.getClassName() + " {" + endl);
                    }    
                }else{
                    fileWriter.write("\npublic class " + item.getClassName() + " {" + endl);
                }
            }
            fileWriter.write(endl);
            
            for(JVariable variable : item.getVariables()){
                String writtenVariable = tab + variable.getAccessType();
                if(variable.getIsStatic()) writtenVariable += " static";
                writtenVariable += " " + variable.getVariableType() + " " + variable.getVariableName() + ";" + endl;
                fileWriter.write(writtenVariable);
            }
            fileWriter.write(endl);
            
            for(JMethod method : item.getMethods()){
                String secondaryType = "";
                if(method.getIsStatic()){
                    secondaryType = "static";
                }else if(method.getIsAbstract()){
                    secondaryType = "abstract";
                }
                
                String methodHeader = tab + method.getAccessType();               
                if(secondaryType.trim().length() >  0) methodHeader += " " + secondaryType;
                if(method.getReturnType().trim().length() > 0) methodHeader += " " + method.getReturnType();
                methodHeader +=  " " + method.getMethodName() + "(";
                
                for(String argName : method.getMethodArgNames()){
                    int index = method.getMethodArgNames().indexOf(argName);
                    if(index + 1 == method.getMethodArgNames().size()){
                        methodHeader += method.getMethodArgTypes().get(index) + " " + argName;
                    }else{
                        methodHeader += method.getMethodArgTypes().get(index) + " " + argName + ",";
                    }
                }
                methodHeader += "){"+ endl;
                if(method.getReturnType().equals("int") || method.getReturnType().equals("byte") ||
                        method.getReturnType().equals("double") || method.getReturnType().equals("long") ||
                        method.getReturnType().equals("short") || method.getReturnType().equals("float")){
                    methodHeader += tab + tab + "return 0;" + endl;
                }else if(method.getReturnType().equals("boolean")){
                    methodHeader += tab + tab + "return false;" + endl;
                }else if(method.getReturnType().equals("char")){
                    methodHeader += tab + tab + "return a;" + endl;
                }else if(method.getReturnType().equals("void")){
                    methodHeader += tab + tab + endl;
                }else if(method.getReturnType().trim().equals("")){
                    methodHeader += tab + tab + endl;
                }else{
                    methodHeader += tab + tab + "return null;" + endl;
                }            
                methodHeader += tab + "}" + endl;
                
                fileWriter.write(methodHeader);
                fileWriter.write(endl);           
            }
            
            fileWriter.write("}");                     
            fileWriter.close();
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    */
    public void exportCode(){
        itemList = dataManager.getItemList();     
        File file = new File("./work");
        if (!file.exists()) {
            file.mkdir();
        }  
        
        //Make all the packages
        for(JItem item : itemList){
            createPackage(item.getPackageName(), "./work/");
        }    
        
        for(JItem item : itemList){
            createClass(item, "./work/");
        }
    }
    
    public void createPackage(String currentPackage, String fileLocation){
        File file = new File(fileLocation + "default_package");
        if(!file.exists()){
            file.mkdir();
        }    
        
        if(currentPackage.length() >= 3 && 
                    currentPackage.substring(0, 3).equals("org")){
                return;
            }
            if(currentPackage.length() >= 4 &&
                    currentPackage.substring(0, 4).equals("java")){
                return;
            }
        
        if(currentPackage.contains(".")){
            String[] parts = currentPackage.split("\\.");
            String partCombiner = "";
            for(String s : parts){
                partCombiner += s;
                File tempFile = new File(fileLocation + partCombiner);
                if (!tempFile.exists()) {
                    tempFile.mkdir();
                }
                partCombiner += "/";
            }
        }else{
            File tempFile = new File(fileLocation + currentPackage);
            if (!tempFile.exists()) {
                    tempFile.mkdir();
            }
        }
    }
    
    public void createClass(JItem item, String fileLocation){
        FileWriter fileWriter = null; 
        String tab = "    ";
        String endl = "\n";
        
            if(item.getPackageName().length() >= 3 && 
                    item.getPackageName().substring(0, 3).equals("org")){
                return;
            }
            if(item.getPackageName().length() >= 4 &&
                    item.getPackageName().substring(0, 4).equals("java")){
                return;
            }
        
        try {       
            String packageLocation = "";
            if(item.getPackageName().trim().length() > 0){
                packageLocation = item.getPackageName().replace(".", "/");
            }else if(item.getPackageName().trim().length() == 0){
                packageLocation = "default_package";
            }
            
            //Makes the file if and only if it doesn't exist
            File file = new File(fileLocation + packageLocation + "/" + item.getClassName() + ".java");
            file.createNewFile();
          
            fileWriter = new FileWriter(fileLocation + packageLocation + "/" + item.getClassName() + ".java");
                      
            //SAVE EVERYTHING HERE
            if(item.getPackageName().trim().length() > 0){
                fileWriter.write("package " + item.getPackageName() + ";" + endl + endl);
            }else if(item.getPackageName().trim().length() == 0){
                fileWriter.write("package default_package;" + endl + endl);
            }
            
            ArrayList<String> imports = new ArrayList();
            
            for(JMethod method : item.getMethods()){
                for(JItem compareItem : itemList){
                    if(method.getReturnType().equals(compareItem.getClassName())){
                        if(compareItem.getPackageName().trim().length() > 0){   
                            if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                            }
                        }
                    }
                    for(String argType : method.getMethodArgTypes()){
                        if(argType.equals(compareItem.getClassName())){
                            if(compareItem.getPackageName().trim().length() > 0){   
                                if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                    imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                                }
                            }
                        }
                    }                   
                }
            }
            
            for(JVariable variable : item.getVariables()){
                for(JItem compareItem : itemList){
                    if(variable.getVariableType().equals(compareItem.getClassName())){
                        if(compareItem.getPackageName().trim().length() > 0){   
                            if(!imports.contains("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl)){
                                imports.add("import " + compareItem.getPackageName() + "." + compareItem.getClassName()+ ";" + endl);
                            }
                        }
                    }
                }
            }
            
            JItem parentItem = null;
            if(item.getParents().get(0).trim().length() > 0){
                for(JItem tempItem : itemList){
                    if(tempItem.getClassName().equals(item.getParents().get(0))){
                        parentItem = tempItem;
                        imports.add("import " + parentItem.getPackageName() + "." + parentItem.getClassName() + ";" + endl);
                    }
                }
            }
            
            for(String s : imports){
                fileWriter.write(s);
            }
            
            //If its an interface
            if(item.isInterface()){
                //Has a parent
                if(parentItem != null){
                    fileWriter.write("\npublic interface " + item.getClassName() + " extends" + parentItem.getClassName() + " {" + endl);
                }else{
                    fileWriter.write("\npublic interface " + item.getClassName() + " {" + endl);
                }
            }else{
                if(parentItem != null){
                    if(parentItem.isInterface()){
                        fileWriter.write("\npublic class " + item.getClassName() + " implements " + parentItem.getClassName() + " {" + endl);
                    }else{
                        fileWriter.write("\npublic class " + item.getClassName() + " extends " + parentItem.getClassName() + " {" + endl);
                    }    
                }else{
                    fileWriter.write("\npublic class " + item.getClassName() + " {" + endl);
                }
            }
            fileWriter.write(endl);
            
            for(JVariable variable : item.getVariables()){
                String writtenVariable = tab + variable.getAccessType();
                if(variable.getIsStatic()) writtenVariable += " static";
                writtenVariable += " " + variable.getVariableType() + " " + variable.getVariableName() + ";" + endl;
                fileWriter.write(writtenVariable);
            }
            fileWriter.write(endl);
            
            for(JMethod method : item.getMethods()){
                JItem parent = dataManager.getItemByName(item.getParents().get(0));
                if(parent != null){
                    if(parent.isAbstract()){
                        for(JMethod method2 : parent.getMethods()){
                            if(method.getMethodName().equals(method2.getMethodName())){
                                fileWriter.write(tab + "@Override" + endl);
                            }
                        }
                    }
                }
                
                String secondaryType = "";
                if(method.getIsStatic()){
                    secondaryType = "static";
                }else if(method.getIsAbstract()){
                    secondaryType = "abstract";
                }
                
                String methodHeader = tab + method.getAccessType();               
                if(secondaryType.trim().length() >  0) methodHeader += " " + secondaryType;
                if(method.getReturnType().trim().length() > 0) methodHeader += " " + method.getReturnType();
                methodHeader +=  " " + method.getMethodName() + "(";
                
                for(String argName : method.getMethodArgNames()){
                    int index = method.getMethodArgNames().indexOf(argName);
                    if(index + 1 == method.getMethodArgNames().size()){
                        methodHeader += method.getMethodArgTypes().get(index) + " " + argName;
                    }else{
                        methodHeader += method.getMethodArgTypes().get(index) + " " + argName + ",";
                    }
                }
                methodHeader += "){"+ endl;
                if(method.getReturnType().equals("int") || method.getReturnType().equals("byte") ||
                        method.getReturnType().equals("double") || method.getReturnType().equals("long") ||
                        method.getReturnType().equals("short") || method.getReturnType().equals("float")){
                    methodHeader += tab + tab + "return 0;" + endl;
                }else if(method.getReturnType().equals("boolean")){
                    methodHeader += tab + tab + "return false;" + endl;
                }else if(method.getReturnType().equals("char")){
                    methodHeader += tab + tab + "return a;" + endl;
                }else if(method.getReturnType().equals("void")){
                    methodHeader += tab + tab + endl;
                }else if(method.getReturnType().trim().equals("")){
                    methodHeader += tab + tab + endl;
                }else{
                    methodHeader += tab + tab + "return null;" + endl;
                }            
                methodHeader += tab + "}" + endl;
                
                fileWriter.write(methodHeader);
                fileWriter.write(endl);           
            }
            
            fileWriter.write("}");                     
            fileWriter.close();
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
