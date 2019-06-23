package com.google.codeu.data;

import java.util.UUID;

public class Request{
    
    enum state
    {
        pending, accepted, denied
    }
    
    
	private UUID id;
  private String requester;
  private String requestee;
  private long timestamp;
  private state status;
  //private int count;

	

  public Request(String requester, String requestee) {
      this.id = UUID.randomUUID();
      this.requester = requester;
      this.requestee = requestee;
      this.timestamp = System.currentTimeMillis();
    this.status = state.pending;
  }

} 
