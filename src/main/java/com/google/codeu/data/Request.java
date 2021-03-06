package com.google.codeu.data;

import java.util.UUID;

public class Request{
    
    /*enum state
    {
        pending=0
        accepted=1
        denied=2
    }*/
    
    
	private UUID id;
  private String requester;
  private String requestee;
  private long timestamp;
  private int status;
  //private int count;

  public Request(String requester, String requestee) {
      this.id = UUID.randomUUID();
      this.requester = requester;
      this.requestee = requestee;
      this.timestamp = System.currentTimeMillis();
      this.status = 0;
  }
  public Request(UUID id,String requester, String requestee,long timestamp,long status){
      this.id = id;
      this.requester = requester;
      this.requestee = requestee;
      this.timestamp = timestamp;
      this.status = (int)status;
  }
  public UUID getID() {
    return id;
  }
  public String getRequester(){
    return requester;
  }
  public String getRequestee(){
    return requestee;
  }
  public long getTimestamp() {
    return timestamp;
  }
  public int getStatus(){
    return this.status;
  }

  public void accept(){
    this.status = 1;
  }
  public void deny(){
    this.status = 2;
  }

} 
