package com.google.codeu.data;

//import Message.java;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Question extends Message{
    private List<UUID> childIDs = new ArrayList<UUID>();
    //this is only used when displaying children
    //basically, I need to access all the data in the servlet(s). Before the javascript.
    //But in that case I can't pass the answers as the children pre-json conversion
    //if datastore supported custom types this would be so much easier
    //but there we go, wishing for things. If I had a pony...
    private List<Message> tempHack = new ArrayList<Message>();
    //private String access = "public";
    
    public Question(String user, String text) {
        super(user, text);
    }
    
    public Question(UUID id, String user, String text, long timestamp/*, String access*/) {
        super(id, user, text, timestamp);
        //this.access = access;
    }
    
    public Question(UUID id, String user, String text, long timestamp, List<String> answers/*, String access*/) {
        super(id, user, text, timestamp);
        for(String answer : answers){
            this.childIDs.add(UUID.fromString(answer));
        }
        //this.access = access;
    }
    
    public List<UUID> getAnswers(){
        return childIDs;
    }
    
    public void addAnswer(Message answer){
        this.childIDs.add(answer.getId());
    }
    
    public void setChildren(List<UUID> newKids){
        this.childIDs = newKids;
        
    }
    public void setHack(List<Message> answers){
        this.tempHack = answers;
    }
    public String getKidsToString(){
        String strkid = "";
        for(UUID kidID: childIDs){
            strkid += kidID.toString();
	    strkid += ":";
        }
        return strkid;
    }
    /*
    public String getAccess(){
        return access;
    }
    
    public void setAccess(String stuff){
        this.access = stuff;
    }
    public String getUser(){
	return user;
    }
     */
}

