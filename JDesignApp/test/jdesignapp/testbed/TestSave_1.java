/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.testbed;

import jdesignapp.testbed.*;
import java.util.ArrayList;
import java.util.Arrays;
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
public class TestSave_1{
    
    public static void main(String[] args){
        ArrayList<JItem> itemList = new ArrayList();
        ArrayList<JConnector> connectorList = new ArrayList();
        JItem application, threadExample, pauseHandler, startHandler, counterTask, dateTask, task, date, thread;
        //Scene dim. : 1280 x 800

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
        threadExample.setX(100);
        threadExample.setY(100);
        threadExample.setClassName("ThreadExample");
        threadExample.setParentName("Application");
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
        pauseHandler.setX(300);
        pauseHandler.setY(200);
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
        startHandler.setX(300);
        startHandler.setY(400);
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
        counterTask.setX(500);
        counterTask.setY(100);
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
        dateTask.setX(100);
        dateTask.setY(400);
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
        
        //Task
        task = new JItem(false);
        task.setX(0);
        task.setY(500);
        task.setClassName("Task");
        task.setPackageName("javafx.concurrent");
        
        //Date
        date = new JItem(false);
        date.setX(500);
        date.setY(500);
        date.setClassName("Date");
        date.setPackageName("java.util");

        //Thread
        thread = new JItem(false);
        thread.setX(250);
        thread.setY(350);
        thread.setClassName("Thread");
        
        itemList.addAll(Arrays.asList(application, threadExample, pauseHandler, startHandler, counterTask, dateTask,
             task, date, thread));
        
        DataManager dataManager = new DataManager();
        dataManager.setItemList(itemList);
        FileManager fileManager = new FileManager(dataManager);
        fileManager.saveAs();
    }
    
}
