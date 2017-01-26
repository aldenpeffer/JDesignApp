/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdesignapp.data;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import jdesignapp.JDesignApp;

/**
 *
 * @author Alden
 */
public class DataManager {
    
    private ArrayList<JItem> itemList;
    private ArrayList<JConnector> connectorList;
    
    public DataManager(){
        itemList = new ArrayList();
        connectorList = new ArrayList();      
    }

    /**
     * @return the itemList
     */
    public ArrayList<JItem> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(ArrayList<JItem> itemList) {
        this.itemList = itemList;
    }
    
    public void clearItemList(){
        itemList.clear();
    }
    
    public void addItem(JItem item){
        itemList.add(item);
    }
    
    public void removeItem(JItem item){
        itemList.remove(item);
    }
    
    public ArrayList<JConnector> getConnectorList() {
        return connectorList;
    }

    public void setConnectorList(ArrayList<JConnector> connectorList) {
        this.connectorList = connectorList;
    }
    
    public void clearConnectorList(){
        connectorList.clear();
    }
    
    public void addConnector(JConnector connector){
        connectorList.add(connector);
    }
    
    public JItem getItemByName(String name){
        for(JItem item : itemList){
            if(item.getClassName().trim().equals(name)){
                return item;
            }
        }
        return null;
    }
   
}
