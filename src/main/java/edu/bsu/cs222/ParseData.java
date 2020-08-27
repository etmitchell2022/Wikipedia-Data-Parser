package edu.bsu.cs222;

//This class is here because the revision class will not work without it

public class ParseData {

    private String username;
    private Integer timestamp;

    ParseData(String username, Integer timestamp){
        this.username = username;
        this.timestamp = timestamp;
    }

    String getUsername(){
        return username;
    }

    Integer getTimestamp(){
        return timestamp;
    }


}
