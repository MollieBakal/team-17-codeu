package com.google.codeu.data;

public class Request{
	private UUID id;
  private String requester;
  private String requestee;
  private long timestamp;
  private int status;
  //private int count;

	enum state       
	{	
	 pending, accepted, denied
	}

  public Request(String requester, String requestee) {
    this(UUID.randomUUID(), requester, requestee, System.currentTimeMillis());
    this.status= state.pending;
  }

} 