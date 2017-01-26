/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdesignapp.controller.Controller;
import jdesignapp.data.DataManager;
import jdesignapp.data.JItem;
import jdesignapp.file.FileManager;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.TableView.ResizeFeatures;
import static javafx.scene.control.TableView.UNCONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import jdesignapp.data.JConnector;
import jdesignapp.data.JGrid;
import jdesignapp.data.JMethod;
import jdesignapp.data.JVariable;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author Alden
 */
public class JDesignApp extends Application{

    SplitPane topBottomPane;
    SplitPane leftRightPane;
    Pane workspace;
    JGrid grid;
    Pane leftPane;
    Pane editPane;
    
    FlowPane topBarPane;
    HBox manageFileBox;
    HBox manageWorkspaceBox;
    HBox manageGUIBox;
    VBox exportBox;
    VBox gridOptionBox;

    //EditPane
    Text classText;
    Text packageText;
    Text parentText;
    Text variableText;
    Text methodText;
    TextField classField;
    TextField packageField;
    Button parentChoices;
    TableView <JVariable> variableTable;
    TableView <JMethod> methodTable;
    
    //ManageFilePane
    Button newButton;
    Button loadButton;
    Button saveButton;
    Button saveAsButton;
    Button exportAsPhoto;
    Button exportAsCode;
    Button exitButton;
    
    //ManageWorkspacePane
    Button selectButton;
    Button resizeButton;
    Button addClassButton;
    Button addInterfaceButton;
    Button removeButton;
    Button undoButton;
    Button redoButton;
    
    //ManageGUIPane
    Button zoomInButton;
    Button zoomOutButton;
    CheckBox showGridCheck;
    CheckBox snapToGridCheck;
    
    //EditPane
    Button addVar;
    Button removeVar;
    Button addMeth;
    Button removeMeth;
    Button editVar;
    Button editMeth;
    
    //Images
    Image addClassImage;
    Image addInterfaceImage;
    Image exitImage;
    Image exportAsCodeImage;
    Image exportAsPhotoImage;
    Image loadImage;
    Image newImage;
    Image redoImage;
    Image removeImage;
    Image resizeImage;
    Image saveImage;
    Image saveAsImage;
    Image selectImage;
    Image undoImage;
    Image zoomInImage;
    Image zoomOutImage;
    
    Scene scene;
    FileManager fileManager;
    DataManager dataManager;
    Controller controller;
    
    
    public static final double WIDTH = 1280;
    public static final double HEIGHT = 800;
    public static double leftPaneWidth = 780;
    public static double leftPaneHeight = 735;
    
    int index;
    JItem selectedItem;

    Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        dataManager = new DataManager();
        fileManager = new FileManager(this);
        workspace = new Pane();
        topBarPane = new FlowPane();
        manageFileBox = new HBox();
        manageWorkspaceBox = new HBox();
        manageGUIBox = new HBox();
        exportBox = new VBox();
        grid = new JGrid();
        leftPane = new Pane();
        
        initButtons();
        initEditPane();
        initUIState(false);
        initHandlers();
        
        controller = new Controller(this, dataManager, fileManager);
        
        exportBox.setPadding(new Insets(5, 0, 5, 0));       
        manageFileBox.setPadding(new Insets(10));
        manageWorkspaceBox.setPadding(new Insets(10));
        manageGUIBox.setPadding(new Insets(10));
        
        exportBox.getChildren().addAll(exportAsPhoto, exportAsCode);
        manageFileBox.getChildren().addAll(newButton, loadButton, saveButton, saveAsButton, exitButton);
        manageWorkspaceBox.getChildren().addAll(selectButton, resizeButton, addClassButton, addInterfaceButton, removeButton,
                undoButton, redoButton);
        manageGUIBox.getChildren().addAll(zoomInButton, zoomOutButton);
        topBarPane.getChildren().addAll(manageFileBox, exportBox, manageWorkspaceBox, manageGUIBox, gridOptionBox);
        
        editPane.setMinWidth(500);
        editPane.setMaxWidth(500);
        
        topBarPane.setMinHeight(65);
        topBarPane.setMaxHeight(65);
        topBarPane.setStyle("-fx-background-color: #566376;");
        
        grid.setVisible(false);
        //LeftPane stuff
        leftPane.getChildren().addAll(grid, workspace);
        leftPane.setStyle("-fx-background-color: #f0f2f4;");
        
        leftRightPane = new SplitPane(leftPane, editPane);
        leftRightPane.setDividerPositions(0.8f);
        
        topBottomPane = new SplitPane(topBarPane, leftRightPane);
        topBottomPane.setOrientation(Orientation.VERTICAL);
        topBottomPane.setDividerPositions(0.1f);
        
        leftPane.setVisible(false);
        editPane.setVisible(false);
        
        scene = new Scene(topBottomPane, WIDTH, HEIGHT);
        stage = primaryStage;
        stage.setTitle("JDesignApp");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
    
    public void initButtons(){
        addClassImage = new Image("file:./images/AddClass.png"); 
        addInterfaceImage = new Image("file:./images/AddInterface.png"); 
        exitImage = new Image("file:./images/Exit.png"); 
        exportAsCodeImage = new Image("file:./images/ExportAsCode.png"); 
        exportAsPhotoImage = new Image("file:./images/ExportAsPhoto.png"); 
        loadImage = new Image("file:./images/Load.png"); 
        newImage = new Image("file:./images/New.png"); 
        redoImage = new Image("file:./images/Redo.png"); 
        removeImage = new Image("file:./images/Remove.png"); 
        resizeImage = new Image("file:./images/Resize.png"); 
        saveImage = new Image("file:./images/Save.png"); 
        saveAsImage = new Image("file:./images/SaveAs.png"); 
        selectImage = new Image("file:./images/Select.png"); 
        undoImage = new Image("file:./images/Undo.png"); 
        zoomInImage = new Image("file:./images/ZoomIn.png"); 
        zoomOutImage = new Image("file:./images/ZoomOut.png"); 
        
        newButton = new Button();
        ImageView tempView = new ImageView(newImage);
        handleImage(tempView);             
        newButton.setGraphic(tempView);
        newButton.setTooltip(new Tooltip("New"));
        
        loadButton = new Button();
        tempView = new ImageView(loadImage);
        handleImage(tempView); 
        loadButton.setGraphic(tempView);
        loadButton.setTooltip(new Tooltip("Load"));
        
        saveButton = new Button();
        tempView = new ImageView(saveImage);
        handleImage(tempView); 
        saveButton.setGraphic(tempView);
        saveButton.setTooltip(new Tooltip("Save"));
        
        saveAsButton = new Button();
        tempView = new ImageView(saveAsImage);
        handleImage(tempView); 
        saveAsButton.setGraphic(tempView);
        saveAsButton.setTooltip(new Tooltip("Save As"));
        
        exportAsPhoto = new Button();
        tempView = new ImageView(exportAsPhotoImage);
        handleImage(tempView); 
        tempView.setFitHeight(16);
        tempView.setFitWidth(32);
        exportAsPhoto.setGraphic(tempView);
        exportAsPhoto.setTooltip(new Tooltip("Export as Photo"));
        
        exportAsCode = new Button();
        tempView = new ImageView(exportAsCodeImage);
        handleImage(tempView); 
        tempView.setFitHeight(16);
        tempView.setFitWidth(32);
        exportAsCode.setGraphic(tempView);
        exportAsCode.setTooltip(new Tooltip("Export as Code"));
        
        exitButton = new Button();
        tempView = new ImageView(exitImage);
        handleImage(tempView); 
        exitButton.setGraphic(tempView);
        exitButton.setTooltip(new Tooltip("Exit"));
        
        selectButton = new Button();
        tempView = new ImageView(selectImage);
        handleImage(tempView); 
        selectButton.setGraphic(tempView);
        selectButton.setTooltip(new Tooltip("Select"));
        
        resizeButton = new Button();
        tempView = new ImageView(resizeImage);
        handleImage(tempView); 
        resizeButton.setGraphic(tempView);
        resizeButton.setDisable(true);
        resizeButton.setTooltip(new Tooltip("Resize"));
        
        addClassButton = new Button();
        tempView = new ImageView(addClassImage);
        handleImage(tempView); 
        addClassButton.setGraphic(tempView);
        addClassButton.setTooltip(new Tooltip("Add Class"));
        
        addInterfaceButton = new Button();
        tempView = new ImageView(addInterfaceImage);
        handleImage(tempView); 
        addInterfaceButton.setGraphic(tempView);
        addInterfaceButton.setTooltip(new Tooltip("Add Interface"));
        
        removeButton = new Button();
        tempView = new ImageView(removeImage);
        handleImage(tempView); 
        removeButton.setGraphic(tempView);
        removeButton.setDisable(true);
        removeButton.setTooltip(new Tooltip("Remove"));
        
        undoButton = new Button();
        tempView = new ImageView(undoImage);
        handleImage(tempView); 
        undoButton.setGraphic(tempView);
        undoButton.setTooltip(new Tooltip("Undo"));
        
        redoButton = new Button();
        tempView = new ImageView(redoImage);
        handleImage(tempView); 
        redoButton.setGraphic(tempView);
        redoButton.setTooltip(new Tooltip("Redo"));
        
        zoomInButton = new Button();
        tempView = new ImageView(zoomInImage);
        handleImage(tempView); 
        zoomInButton.setGraphic(tempView);
        zoomInButton.setTooltip(new Tooltip("Zoom In"));
        
        zoomOutButton = new Button();
        tempView = new ImageView(zoomOutImage);
        handleImage(tempView); 
        zoomOutButton.setGraphic(tempView);
        zoomOutButton.setTooltip(new Tooltip("Zoom Out"));
        
        gridOptionBox = new VBox();
        
        showGridCheck = new CheckBox("Grid");
        showGridCheck.setPadding(new Insets(0, 10, 5, 10));
        showGridCheck.setStyle("-fx-text-fill: white;");
        
        snapToGridCheck = new CheckBox("Snap");
        snapToGridCheck.setPadding(new Insets(0, 10, 5, 10));
        snapToGridCheck.setStyle("-fx-text-fill: white;");
        gridOptionBox.getChildren().addAll(showGridCheck, snapToGridCheck);

    }
    
    
    public void initEditPane(){
        editPane = new Pane();    
        VBox fieldBox = new VBox();
        classText = new Text("Class Name: ");
        classText.setFont(Font.font(24));
        packageText = new Text("Package: ");
        packageText.setFont(Font.font(18));
        parentText = new Text("Parent: ");
        parentText.setFont(Font.font(18));
        variableText = new Text("Variables: ");
        variableText.setFont(Font.font(18));
        methodText = new Text("Methods: ");
        methodText.setFont(Font.font(18));
        
        variableTable = new TableView();
        variableTable.setEditable(false);
        variableTable.setMaxHeight(150);
        variableTable.setMaxWidth(450);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(112.5);
        nameCol.setCellValueFactory(new PropertyValueFactory("variableName"));
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory("variableType"));
        typeCol.setPrefWidth(112.5);
        TableColumn accessCol = new TableColumn("Access");
        accessCol.setCellValueFactory(new PropertyValueFactory("accessType"));
        accessCol.setPrefWidth(112.5);
        TableColumn staticCol = new TableColumn("Static");
        staticCol.setCellValueFactory(new PropertyValueFactory("isStatic"));
        staticCol.setPrefWidth(112.5);
        variableTable.getColumns().setAll(nameCol, typeCol, accessCol, staticCol);
        variableTable.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);
        
        methodTable = new TableView();
        methodTable.setEditable(false);
        methodTable.setMaxHeight(150);
        methodTable.setMaxWidth(450);
        nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("methodName"));
        nameCol.setPrefWidth(75);
        typeCol = new TableColumn("Return");
        typeCol.setCellValueFactory(new PropertyValueFactory("returnType"));
        typeCol.setPrefWidth(75);
        accessCol = new TableColumn("Access");
        accessCol.setCellValueFactory(new PropertyValueFactory("accessType"));
        accessCol.setPrefWidth(75);
        TableColumn argsCol = new TableColumn("Arguments");
        argsCol.setCellValueFactory(new PropertyValueFactory("argsString"));
        argsCol.setPrefWidth(75);
        staticCol = new TableColumn("Static");
        staticCol.setCellValueFactory(new PropertyValueFactory("isStatic"));
        staticCol.setPrefWidth(75);
        TableColumn abstractCol = new TableColumn("Abstract");
        abstractCol.setCellValueFactory(new PropertyValueFactory("isAbstract"));
        abstractCol.setPrefWidth(75);
        methodTable.getColumns().setAll(nameCol, typeCol, accessCol, argsCol, staticCol, abstractCol);
        methodTable.setColumnResizePolicy(UNCONSTRAINED_RESIZE_POLICY);

        
        classField = new TextField();
        classField.setDisable(true);
        packageField = new TextField();
        packageField.setDisable(true);
        parentChoices = new Button("Parent Selection");
        
        fieldBox.setPadding(new Insets(15, 15, 15, 15));
        
        for(int i = 0; i < 3; i++){
            HBox tempBox = new HBox();
            tempBox.setPadding(new Insets(5, 15, 20, 0));
            if(i == 0) tempBox.getChildren().addAll(classText, classField);
            if(i == 1) tempBox.getChildren().addAll(packageText, packageField);
            if(i == 2) tempBox.getChildren().addAll(parentText, parentChoices);
            
            fieldBox.getChildren().add(tempBox);
        }            
        
        addVar = new Button("+");
        addVar.setPrefWidth(40);
        removeVar = new Button("-");
        removeVar.setPrefWidth(40);
        editVar = new Button("Edit");
        editMeth = new Button("Edit");
        HBox varBox = new HBox();
        varBox.setPadding(new Insets(10));
        varBox.getChildren().addAll(variableText, addVar, removeVar, editVar);

        addMeth = new Button("+");
        addMeth.setPrefWidth(40);
        removeMeth = new Button("-");
        removeMeth.setPrefWidth(40);
        HBox methBox = new HBox();
        methBox.setPadding(new Insets(10));
        methBox.getChildren().addAll(methodText, addMeth, removeMeth, editMeth);
        
        fieldBox.getChildren().addAll(varBox, variableTable, methBox, methodTable);
        editPane.getChildren().add(fieldBox);     
        editPane.setStyle("-fx-background-color: #b7c0ca;");
    }
    
    public void handleImage(ImageView img){
        img.setFitWidth(32);
        img.setFitHeight(32);
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
    }
    
    public void initHandlers(){
        newButton.setOnAction(e -> controller.handleNewRequest());
        addClassButton.setOnAction(e -> controller.handleAddClassRequest());
        addInterfaceButton.setOnAction(e -> controller.handleAddInterfaceRequest());
        removeButton.setOnAction(e -> controller.handleRemove());       
        selectButton.setOnAction(e -> controller.handleSelectRequest());
        leftPane.setOnMousePressed(e -> controller.handleWorkspaceMousePressed(e));
        leftPane.setOnMouseDragged(e -> controller.handleWorkspaceMouseDragged(e));
        leftPane.setOnMouseMoved(e -> controller.handleWorkspaceMouseMoved(e));
        showGridCheck.setOnAction(e -> controller.handleShowGrid());
        resizeButton.setOnAction(e -> controller.handleResizeRequest());
        classField.textProperty().addListener((observable, oldValue, newValue) -> controller.handleClassFieldChanged(newValue));
        packageField.textProperty().addListener((observable, oldValue, newValue) -> controller.handlePackageFieldChanged(newValue));
        saveAsButton.setOnAction(e -> controller.handleSaveAs());
        saveButton.setOnAction(e -> controller.handleSave());
        addVar.setOnAction(e -> controller.handleAddVariable());
        addMeth.setOnAction(e -> controller.handleAddMethod());
        removeVar.setOnAction(e -> controller.handleRemoveVariable());
        removeMeth.setOnAction(e -> controller.handleRemoveMethod());
        editVar.setOnAction(e -> controller.handleEditVariable());
        editMeth.setOnAction(e -> controller.handleEditMethod());
        exportAsCode.setOnAction(e -> controller.handleExportCode());
        loadButton.setOnAction(e -> controller.handleLoad());
        parentChoices.setOnAction(e -> controller.handleParentsModified());       
    }
    
    public void itemSelectedUpdate(boolean isSelected, JItem item){
        resizeButton.setDisable(!isSelected);
        removeButton.setDisable(!isSelected);
        addVar.setDisable(!isSelected);
        removeVar.setDisable(!isSelected);
        editVar.setDisable(!isSelected);
        editMeth.setDisable(!isSelected);
        addMeth.setDisable(!isSelected);
        removeMeth.setDisable(!isSelected);
        parentChoices.setDisable(!isSelected);
        if(item != null){
           variableTable.setItems(FXCollections.observableList(item.getVariables())); 
           methodTable.setItems(FXCollections.observableList(item.getMethods()));
           
        }else{
            variableTable.setItems(null);
            methodTable.setItems(null);

        }
    }
    
    public void updateWorkspace(){
        ArrayList<JItem> itemList = dataManager.getItemList();        
        ArrayList<JConnector> connectorList = dataManager.getConnectorList();
        
        workspace.getChildren().clear();        
        for(int i = 0; i < itemList.size(); i++){
            JItem item = itemList.get(i);
            workspace.getChildren().add(item);
            workspace.getChildren().addAll(item.getLines());
            workspace.getChildren().addAll(item.getText());
            workspace.getChildren().addAll(item.getResizeMarkers());
        }
        
        for(int i = 0; i < connectorList.size(); i++){
            JConnector connector = connectorList.get(i);
            workspace.getChildren().add(connector);
        }
    }
    
    public void clearWorkspace(){
        workspace.getChildren().clear();
        index = 0;
        selectedItem = null;
        classField.setText("");
        classField.setDisable(true);
        parentChoices.setDisable(true);
        packageField.setText("");
        packageField.setDisable(true);
        removeButton.setDisable(true);
        resizeButton.setDisable(true);
        addVar.setDisable(true);
        removeVar.setDisable(true);
        addMeth.setDisable(true);
        removeMeth.setDisable(true);
        editVar.setDisable(true);
        editMeth.setDisable(true);
    }
    
    public void loadWorkspace(){    
        initUIState(true);
        leftPane.setVisible(true);
        editPane.setVisible(true);
    }   
    
    public TextField getClassField(){
        return classField;
    }
    
    public TextField getPackageField(){
        return packageField;
    }
    
    public Button getRemoveButton(){
        return removeButton;
    }

    public TableView getVariableTable() {
       return variableTable;
    }

    public TableView getMethodTable() {
        return methodTable;
    }
    
    public JGrid getGrid() {
        return grid;
    }
    
    public CheckBox getShowGridCheck() {
        return showGridCheck;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public DataManager getDataManager(){
        return dataManager;
    }
    
    public Button getResizeButton(){
        return resizeButton;
    }
    
    public Button getParentChoices(){
        return parentChoices;
    }

    private void initUIState(Boolean init) {
        //ManageFilePane
        saveButton.setDisable(!init);
        saveAsButton.setDisable(!init);
        exportAsPhoto.setDisable(!init);
        exportAsCode.setDisable(!init);
        //ManageWorkspacePane
        resizeButton.setDisable(true);
        removeButton.setDisable(true);
        selectButton.setDisable(!init);
        addClassButton.setDisable(!init);
        addInterfaceButton.setDisable(!init);
        undoButton.setDisable(!init);
        redoButton.setDisable(!init);
        //ManageGUIPane
        zoomInButton.setDisable(!init);
        zoomOutButton.setDisable(!init);
        showGridCheck.setDisable(!init);
        snapToGridCheck.setDisable(!init);
    
        //EditPane
        addVar.setDisable(true);
        removeVar.setDisable(true);
        addMeth.setDisable(true);
        removeMeth.setDisable(true);
        editVar.setDisable(true);
        editMeth.setDisable(true);
        parentChoices.setDisable(true);
    }
}
