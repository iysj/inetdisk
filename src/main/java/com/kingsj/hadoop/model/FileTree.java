package com.kingsj.hadoop.model;

import java.io.Serializable;

public class FileTree extends Hfile implements Serializable{
    
    private static final long serialVersionUID = -2075681958356211294L;

//    [
//    {"0":"1","id":"1","1":"0","parentId":"0","2":"Computers","name":"Computers","3":null,"quantity":null,"4":null,"price":null,"state":"closed","total":0},
//    {"0":"2","id":"2","1":"0","parentId":"0","2":"Electronics","name":"Electronics","3":null,"quantity":null,"4":null,"price":null,"state":"closed","total":0},
//    {"0":"30","id":"30","1":"0","parentId":"0","2":"Sporting","name":"Sporting","3":null,"quantity":null,"4":null,"price":null,"state":"closed","total":0}
//    ]

    //文件ID
    private String id;
    
    private String text;
    
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

   
    
    
    
}
