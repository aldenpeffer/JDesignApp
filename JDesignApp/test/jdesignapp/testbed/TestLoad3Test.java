/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.testbed;

import java.util.ArrayList;
import java.util.Arrays;
import jdesignapp.data.DataManager;
import jdesignapp.data.JConnector;
import jdesignapp.data.JItem;
import jdesignapp.data.JMethod;
import jdesignapp.data.JVariable;
import jdesignapp.file.FileManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alden
 */
public class TestLoad3Test {
    
    public TestLoad3Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLoadAndSave() {
        ArrayList<JItem> itemList = new ArrayList();
        ArrayList<JConnector> connectorList = new ArrayList();
        JItem application, threadExample, pauseHandler, startHandler, counterTask, dateTask,
                eventHandler, task, date, stage, borderPane, flowPane, button, scrollPane,
                textArea, thread, platform;
        //Scene dim. : 780 x 700

        //Application
        application = new JItem(false);
        application.setClassName("Application");
        application.setPackageName("javafx.application");
        application.setIsAbstract(true);
        application.setX(600);
        application.setY(0);
        JMethod method = new JMethod("public", "void", "start");
        method.setIsAbstract(true);
        method.addArgument("Stage", "primaryStage");
        application.addMethod(method);
        
        //ThreadExample
        threadExample = new JItem(false);
        threadExample.setClassName("ThreadExample");
        threadExample.setParentName("Application");
        threadExample.setX(600);
        threadExample.setY(400);
        JVariable startText, pauseText, window, appPane, topPane, 
                startButton, pauseButton, scrollPaneVar, textAreaVar, dateThread,
                dateTaskVar, counterThread, counterTaskVar, work;
        startText = new JVariable("public", "String", "START_TEXT");
        startText.setIsStatic(true);
        pauseText = new JVariable("public", "String", "PAUSE_TEXT");
        pauseText.setIsStatic(true);
        window = new JVariable("private", "Stage", "window");
        appPane = new JVariable("private", "BorderPane", "appPane");
        topPane = new JVariable("private", "FlowPane", "topPane");
        startButton = new JVariable("private", "Button", "startButton");
        pauseButton = new JVariable("private", "Button", "pauseButton");
        scrollPaneVar = new JVariable("private", "ScrollPane", "scrollPane");
        textAreaVar = new JVariable("private", "TextArea", "textArea");
        dateThread = new JVariable("private", "Thread", "dateThread");
        dateTaskVar = new JVariable("private", "Task", "dateTask");
        counterThread = new JVariable("private", "Thread", "counterThread");
        counterTaskVar = new JVariable("private", "Task", "counterTask");
        work = new JVariable("private", "boolean", "work");
        
        JMethod start, startWork, pauseWork, doWork, appendText,
                sleep, initLayout, initHandlers, initWindow,
                initThreads, main;
        start = new JMethod("public", "void", "start");
        start.addArgument("Stage", "primaryStage");
        startWork = new JMethod("public", "void", "startWork");
        pauseWork = new JMethod("public", "void", "pauseWork");
        doWork = new JMethod("public", "boolean", "doWork");
        appendText = new JMethod("public", "void", "appentText");
        appendText.addArgument("String", "textToAppend");
        sleep = new JMethod("public", "void", "sleep");
        sleep.addArgument("int", "timeToSleep");
        initLayout = new JMethod("private", "void", "initLayout");
        initHandlers = new JMethod("private", "void", "initHandlers");
        initWindow = new JMethod("private", "void", "initWindow");
        initWindow.addArgument("Stage", "initPrimaryStage");
        initThreads = new JMethod("private", "void", "initThreads");
        main = new JMethod("public", "void", "main");
        main.setIsStatic(true);
        main.addArgument("String[]", "args");
        
        ArrayList<JMethod> methodList = new ArrayList();
        ArrayList<JVariable> variableList = new ArrayList();
        
        methodList.addAll(Arrays.asList(start, startWork, pauseWork, doWork, appendText,
                sleep, initLayout, initHandlers, initWindow,
                initThreads, main));
        variableList.addAll(Arrays.asList(startText, pauseText, window, appPane, topPane, 
                startButton, pauseButton, scrollPaneVar, textAreaVar, dateThread,
                dateTaskVar, counterThread, counterTaskVar, work));
        threadExample.setMethods(methodList);
        threadExample.setVariables(variableList);
        
        //Pause Handler
        pauseHandler = new JItem(false);
        pauseHandler.setX(500);
        pauseHandler.setY(300);
        pauseHandler.setClassName("PauseHandler");
        pauseHandler.setParentName("EventHandler");
        pauseHandler.addVariable(new JVariable("private", "ThreadExample", "app"));
        JMethod pauseHandlerConstructor, handle;
        pauseHandlerConstructor = new JMethod("public", "PauseHandler");
        pauseHandlerConstructor.addArgument("ThreadExample", "initApp");
        handle = new JMethod("public", "void", "handle");
        handle.addArgument("Event", "event");
        
        pauseHandler.addMethod(pauseHandlerConstructor);
        pauseHandler.addMethod(handle);
        
        //StartHandler
        startHandler = new JItem(false);
        startHandler.setX(100);
        startHandler.setY(200);
        startHandler.setClassName("StartHandler");
        startHandler.setParentName("EventHandler");
        startHandler.addVariable(new JVariable("private", "ThreadExample", "app"));
        JMethod startHandlerConstructor;
        startHandlerConstructor = new JMethod("public", "StartHandler");
        startHandlerConstructor.addArgument("ThreadExample", "initApp");
        handle = new JMethod("public", "void", "handle");
        handle.addArgument("Event", "event");
        
        startHandler.addMethod(pauseHandlerConstructor);
        startHandler.addMethod(handle);
        
        //CounterTask
        counterTask = new JItem(false);
        counterTask.setX(400);
        counterTask.setY(300);
        counterTask.setClassName("CounterTask");
        counterTask.setParentName("Task");
        counterTask.addVariable(new JVariable("private", "ThreadExample", "app"));
        counterTask.addVariable(new JVariable("private", "int", "counter"));
        JMethod counterTaskConstructor, call;
        counterTaskConstructor = new JMethod("public", "CounterTask");
        counterTaskConstructor.addArgument("ThreadExample", "initApp");
        call = new JMethod("protected", "void", "call");
        
        counterTask.addMethod(counterTaskConstructor);
        counterTask.addMethod(call);
        
        //DateTask
        dateTask = new JItem(false);
        dateTask.setX(400);
        dateTask.setY(200);
        dateTask.setClassName("DateTask");
        dateTask.setParentName("Task");
        dateTask.addVariable(new JVariable("private", "ThreadExample", "app"));
        dateTask.addVariable(new JVariable("private", "Date", "counter"));
        JMethod dateTaskConstructor;
        dateTaskConstructor = new JMethod("public", "DateTask");
        dateTaskConstructor.addArgument("ThreadExample", "initApp");
        call = new JMethod("protected", "void", "call");
        
        dateTask.addMethod(dateTaskConstructor);
        dateTask.addMethod(call);
        
        //Event Handler
        eventHandler = new JItem(true);
        eventHandler.setX(600);
        eventHandler.setY(150);
        eventHandler.setClassName("EventHandler");
        eventHandler.setPackageName("javafx.event");
        handle = new JMethod("public", "void", "handle");
        handle.addArgument("Event", "event");
        eventHandler.addMethod(handle);
        
        //Task
        task = new JItem(false);
        task.setX(300);
        task.setY(150);
        task.setClassName("Task");
        task.setPackageName("javafx.concurrent");
        
        //Date
        date = new JItem(false);
        date.setX(600);
        date.setY(150);
        date.setClassName("Date");
        date.setPackageName("java.util");
        
        //Platform
        platform = new JItem(false);
        platform.setX(500);
        platform.setY(150);
        platform.setClassName("Platform");
        platform.setPackageName("javafx.application");
                
        //Stage
        stage = new JItem(false);
        stage.setX(400);
        stage.setY(150);
        stage.setClassName("Stage");
        stage.setPackageName("javafx.stage");
        
        //BorderPane
        borderPane = new JItem(false);
        borderPane.setX(300);
        borderPane.setY(150);
        borderPane.setClassName("BorderPane");
        borderPane.setPackageName("javafx.scene.layout");
        
        //FlowPane
        flowPane = new JItem(false);
        flowPane.setX(200);
        flowPane.setY(150);
        flowPane.setClassName("FlowPane");
        flowPane.setPackageName("javafx.scene.layout");
        
        //Button
        button = new JItem(false);
        button.setX(100);
        button.setY(440);
        button.setClassName("Button");
        button.setPackageName("javafx.scene.control");
        
        //ScrollPane
        scrollPane = new JItem(false);
        scrollPane.setX(300);
        scrollPane.setY(260);
        scrollPane.setClassName("ScrollPane");
        scrollPane.setPackageName("javafx.scene.control");
        
        //Text Area
        textArea = new JItem(false);
        textArea.setX(500);
        textArea.setY(500);
        textArea.setClassName("TextArea");
        textArea.setPackageName("javafx.scene.control");
        
        //Thread
        thread = new JItem(false);
        thread.setX(700);
        thread.setY(300);
        thread.setClassName("Thread");
        
        itemList.addAll(Arrays.asList(application, threadExample, pauseHandler, startHandler, counterTask, dateTask,
            eventHandler, task, date, stage, borderPane, flowPane, button, scrollPane, 
            textArea, thread, platform));
        
        
        //JConnectors
        JConnector childToParent1 = new JConnector();
        childToParent1.setParents("Task", "connector");
        childToParent1.setStartX(5);
        childToParent1.setStartY(5);
        childToParent1.setEndX(10);
        childToParent1.setEndY(10);
        connectorList.add(childToParent1);
        
        JConnector childToParent2 = new JConnector();
        childToParent2.setParents("connector", "CounterTask");
        childToParent2.setStartX(10);
        childToParent2.setStartY(10);
        childToParent2.setEndX(25);
        childToParent2.setEndY(25);
        connectorList.add(childToParent2);
        
        JConnector con = new JConnector();
        con.setParents("Task", "DateTask");
        con.setStartX(10);
        con.setStartY(10);
        con.setEndX(25);
        con.setEndY(25);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("Date", "DateTask");
        con.setStartX(10);
        con.setStartY(10);
        con.setEndX(25);
        con.setEndY(25);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("DateTask", "ThreadExample");
        con.setStartX(10);
        con.setStartY(10);
        con.setEndX(25);
        con.setEndY(25);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("CounterTask", "connector");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("connector", "connector");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("connector", "Platform");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("CounterTask", "Platform");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("DateTask", "Platform");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("Application", "ThreadExample");
        con.setStartX(20);
        con.setStartY(20);
        con.setEndX(45);
        con.setEndY(45);
        connectorList.add(con);
        
        con = new JConnector();
        con.setParents("ThreadExample", "PauseHandler");
        con.setStartX(30);
        con.setStartY(30);
        con.setEndX(75);
        con.setEndY(75);
        connectorList.add(con);
        
        DataManager dataManager = new DataManager();
        dataManager.setConnectorList(connectorList);
        dataManager.setItemList(itemList);
        FileManager fileManager = new FileManager(dataManager);
        fileManager.saveAs();
        
        //CLEAR IT TO VERIFY IT'S FROM THE LOAD
        dataManager.clearConnectorList();
        dataManager.clearItemList();
        
        //LOADING HERE      
        boolean success =  fileManager.load();
        if(success){
            //Location of class
            JItem newValue = dataManager.getItemByName("ThreadExample");
            assertEquals("ThreadExample X: ", threadExample.getX(), newValue.getX(), 0);
            assertEquals("ThreadExample Y: ", threadExample.getY(), newValue.getY(), 0);
            
            //Variable Name and Type
            JVariable oldValue = counterTask.getVariableByName("counter");
            JVariable new_Value = dataManager.getItemByName("CounterTask").getVariableByName("counter");
            assertEquals("CounterTask Var counter Name: ", oldValue.getVariableName(), new_Value.getVariableName());
            assertEquals("CounterTask Var counter Type: ", oldValue.getVariableType(), new_Value.getVariableType());
            
            //Two method arguments, two method types
            String oldArg1 = threadExample.getMethodByName("sleep").getMethodArgNames().get(0);
            String oldArg2 = threadExample.getMethodByName("start").getMethodArgNames().get(0);;
            String newArg1 = newValue.getMethodByName("sleep").getMethodArgNames().get(0);
            String newArg2 = newValue.getMethodByName("start").getMethodArgNames().get(0);
            String oldType1 = threadExample.getMethodByName("sleep").getMethodArgTypes().get(0);
            String oldType2 = threadExample.getMethodByName("start").getMethodArgTypes().get(0);;
            String newType1 = newValue.getMethodByName("sleep").getMethodArgTypes().get(0);
            String newType2 = newValue.getMethodByName("start").getMethodArgTypes().get(0);
            
            assertEquals("ThreadExample Method sleep argName: ", oldArg1, newArg1);
            assertEquals("ThreadExample Method sleep argType: ", oldType1, newType1);
                    
            assertEquals("ThreadExample Method start argName: ", oldArg2, newArg2);
            assertEquals("ThreadExample Method start argType: ", oldType2, newType2);
            
            double oldStartX1 = childToParent1.getStartX();
            double oldStartY1 = childToParent1.getStartY();
            double newStartX1 = dataManager.getConnectorList().get(0).getStartX();
            double newStartY1 = dataManager.getConnectorList().get(0).getStartY();
            
            double oldEndX1 = childToParent1.getEndX();
            double oldEndY1 = childToParent1.getEndY();
            double newEndX1 = dataManager.getConnectorList().get(0).getEndX();
            double newEndY1 = dataManager.getConnectorList().get(0).getEndY();
            
            double oldStartX2 = childToParent2.getStartX();
            double oldStartY2 = childToParent2.getStartY();
            double newStartX2 = dataManager.getConnectorList().get(1).getStartX();
            double newStartY2 = dataManager.getConnectorList().get(1).getStartY();
            
            double oldEndX2 = childToParent2.getEndX();
            double oldEndY2 = childToParent2.getEndY();
            double newEndX2 = dataManager.getConnectorList().get(1).getEndX();
            double newEndY2 = dataManager.getConnectorList().get(1).getEndY();
            
            //16 Coordinates for two connected line segments connecting a child class to a parent
            assertEquals("Start X1", oldStartX1, newStartX1, 0);
            assertEquals("Start Y1", oldStartY1, newStartY1, 0);
            assertEquals("End X1", oldEndX1, newEndX1, 0);
            assertEquals("End Y1", oldEndY1, newEndY1, 0);
            assertEquals("Start X2", oldStartX2, newStartX2, 0);
            assertEquals("Start Y2", oldStartY2, newStartY2, 0);
            assertEquals("End X2", oldEndX2, newEndX2, 0);
            assertEquals("End Y2", oldEndY2, newEndY2, 0); 
        }
    }
    
}
