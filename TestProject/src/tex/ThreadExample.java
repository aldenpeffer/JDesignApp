package tex;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.concurrent.Task;
import javafx.application.Application;

public class ThreadExample extends Application {

    public static String START_TEXT;
    public static String PAUSE_TEXT;
    private Stage window;
    private BorderPane appPane;
    private FlowPane topPane;
    private Button startButton;
    private Button pauseButton;
    private ScrollPane scrollPane;
    private TextArea textArea;
    private Thread dateThread;
    private Task dateTask;
    private Thread counterThread;
    private Task counterTask;
    private boolean work;

    @Override
    public void start(Stage primaryStage){
        
    }

    public void startWork(){
        
    }

    public void pauseWork(){
        
    }

    public boolean doWork(){
        return false;
    }

    public void appentText(String textToAppend){
        
    }

    public void sleep(int timeToSleep){
        
    }

    private void initLayout(){
        
    }

    private void initHandlers(){
        
    }

    private void initWindow(Stage initPrimaryStage){
        
    }

    private void initThreads(){
        
    }

    public static void main(String[] args){
        
    }

}