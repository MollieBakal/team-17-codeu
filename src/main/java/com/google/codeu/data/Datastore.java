/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Iterator;

import java.util.Arrays;

import java.util.Set;
import java.util.HashSet;


/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }


  public void storeRequest(Request request){
    Entity requestEntity = new Entity("Request", request.getID().toString());
    requestEntity.setProperty("requester", request.getRequester());
    requestEntity.setProperty("requestee", request.getRequestee());
    requestEntity.setProperty("timestamp", request.getTimestamp());
    requestEntity.setProperty("status",request.getStatus());

    datastore.put(requestEntity);
  }

  public List<Request> getIncomingRequests(String requestee) {
    List<Request> requests = new ArrayList<Request>();

    Query query =
        new Query("Request")
            //The setFilter line was here originally but not in the Step 3 provided code
            .setFilter(new Query.FilterPredicate("requestee", FilterOperator.EQUAL, requestee))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return requestHelper(requestee, requests, query, results);
  }

  public List<Request> getAllRequests() {
    List<Request> requests = new ArrayList<Request>();

    Query query =
        new Query("Request")
            //The setFilter line was here originally but not in the Step 3 provided code
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return requestHelper("getAllRequests", requests, query, results);
  }

  public List<Request> requestHelper(String userOrAll, List<Request> requests, Query query, PreparedQuery results) {
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);

        String requestee;
        //The users need only be specified when all messages of possibly more than one user is being shown
        if(userOrAll.equals("getAllRequests")) {
          requestee = (String) entity.getProperty("requestee");
        }
        else {
          requestee = userOrAll;
        }

        String requester = (String) entity.getProperty("requester");
        long timestamp = (long) entity.getProperty("timestamp");
        long status = (long) entity.getProperty("status");

        Request request = new Request(id,requester,requestee,timestamp,status);
        requests.add(request);
      } catch (Exception e) {
        System.err.println("Error reading request.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return requests;
  }






  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());

    datastore.put(messageEntity);
  }

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String user) {
    List<Message> messages = new ArrayList<Message>();

    Query query =
        new Query("Message")
            //The setFilter line was here originally but not in the Step 3 provided code
            .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return messageHelper(user, messages, query, results);
  }
    
    
    public Message getIDMessage(String parentID) {
        List<Question> messages = new ArrayList<Question>();
        Query query = new Query("Message").setFilter(new Query.FilterPredicate("UUID", FilterOperator.EQUAL, parentID)).addSort("timestamp", SortDirection.DESCENDING);
        //PreparedQuery results = datastore.prepare(query);
        //System.out.println(results.toString());
        Key parent = KeyFactory.createKey("Message", parentID);
        try{
            Entity entity = datastore.get(parent);
            String user = (String) entity.getProperty("user");
            String idString = entity.getKey().getName();
            UUID id = UUID.fromString(idString);
            //UserID is unique, so this should only ever resolve to one message.
            String text = (String) entity.getProperty("text");
            long timestamp = (long) entity.getProperty("timestamp");
            Message message = new Message(id, user, text, timestamp);
            return message;
        }catch(Exception e){
            System.out.println("ugh");
            System.out.println("couldn't find it?");
            e.printStackTrace();
        }
        Message thing = new Message("nobody", "debug");
        return thing;
        /*
        List<Message> messages = new ArrayList<Message>();
        Query query = new Query("Message").setFilter(new Query.FilterPredicate("UUID", FilterOperator.EQUAL, parentID));
        PreparedQuery results = datastore.prepare(query);
        String user = (String) results.asSingleEntity().getProperty("user");
        messageHelper(user, messages, query, results);
        return messages.get(0);
         */
    }

  public List<Message> getAllMessages(){
    List<Message> messages = new ArrayList<Message>();

    Query query = new Query("Message")
      .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return messageHelper("getAllMessages", messages, query, results);
 }
  
  public List<Message> messageHelper(String userOrAll, List<Message> messages, Query query, PreparedQuery results) {
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);

        String user;
        //The users need only be specified when all messages of possibly more than one user is being shown
        if(userOrAll.equals("getAllMessages")) {
          user = (String) entity.getProperty("user");
        }
        else {
          user = userOrAll;
        }

        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        Message message = new Message(id, user, text, timestamp);
        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
  }

 /** Stores the User in Datastore. */

 public void storeUser(User user) {

  Entity userEntity = new Entity("User", user.getEmail());
  userEntity.setProperty("email", user.getEmail());
  userEntity.setProperty("aboutMe", user.getAboutMe());
     userEntity.setProperty("firstName", user.getFirstName());
     userEntity.setProperty("lastName", user.getLastName());
     userEntity.setProperty("friends", user.getFriendsToString());
  datastore.put(userEntity);

 }

 

 /**

  * Returns the User owned by the email address, or

  * null if no matching User was found.

  */

 public User getUser(String email) {

  Query query = new Query("User")
    .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
  PreparedQuery results = datastore.prepare(query);
  Entity userEntity = results.asSingleEntity();
  if(userEntity == null) {return null; }
  String aboutMe = (String) userEntity.getProperty("aboutMe");
     List<String> friends = Arrays.asList(((String) userEntity.getProperty("friends")).split(" "));
                                          String fn = (String) userEntity.getProperty("firstName");
                                          String ln = (String) userEntity.getProperty("lastName");
  User user = new User(email, fn, ln, aboutMe, friends);
  return user;

 }

  
 /** Returns the total number of messages for all users. */
public int getTotalMessageCount(){
  Query query = new Query("Message");
  Query query2 = new Query("Question");
  PreparedQuery results = datastore.prepare(query);
    PreparedQuery results2 = datastore.prepare(query2);
  return results.countEntities(FetchOptions.Builder.withLimit(1000)) + results2.countEntities(FetchOptions.Builder.withLimit(1000));
}


public long getAverageMessageLength(){
  Query query = new Query("Message");
    Query query2 = new Query("Question");
  PreparedQuery results = datastore.prepare(query);
    PreparedQuery results2 = datastore.prepare(query2);

  long totalChars=0;
  for (Entity entity : results.asIterable()) {
    String text = (String) entity.getProperty("text");
    totalChars+= text.length();
  }
    for (Entity entity : results2.asIterable()) {
        String text = (String) entity.getProperty("text");
        totalChars+= text.length();
    }
  long tot = getTotalMessageCount();
  return totalChars/tot;

}

public int getLongestMessage(){
  Query query = new Query("Message");
    Query query2 = new Query("Question");
  PreparedQuery results = datastore.prepare(query);
    PreparedQuery results2 = datastore.prepare(query2);

  int maxLength = 0;
  for (Entity entity : results.asIterable()) {
    String s = (String) entity.getProperty("text");
    if (s.length() > maxLength) {
              maxLength = s.length();
          }
  }
    for (Entity entity : results2.asIterable()) {
        String s = (String) entity.getProperty("text");
        if (s.length() > maxLength) {
            maxLength = s.length();
        }
    }
  return maxLength;
  
}

    //And so begin the Question methods
    /** Stores the Message in Datastore. */
    public void storeQuestion(Question message) {
        Entity messageEntity = new Entity("Question", message.getId().toString());
        messageEntity.setProperty("user", message.getUser());
        messageEntity.setProperty("text", message.getText());
        messageEntity.setProperty("timestamp", message.getTimestamp());
        messageEntity.setProperty("children", message.getKidsToString());
        datastore.put(messageEntity);
    }
    
    /**
     * Gets messages posted by a specific user.
     *
     * @return a list of messages posted by the user, or empty list if user has never posted a
     *     message. List is sorted by time descending.
     */
    public List<Question> getQuestions(String user) {
        List<Question> messages = new ArrayList<Question>();
        
        Query query =
        new Query("Question")
        //The setFilter line was here originally but not in the Step 3 provided code
        .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
        .addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
        
        return questionHelper(user, messages, query, results);
    }
    
    
    public Question getIDQuestion(String parentID) {
        List<Question> messages = new ArrayList<Question>();
        Query query = new Query("Question").setFilter(new Query.FilterPredicate("UUID", FilterOperator.EQUAL, parentID)).addSort("timestamp", SortDirection.DESCENDING);
        //PreparedQuery results = datastore.prepare(query);
        //System.out.println(results.toString());
        Key parent = KeyFactory.createKey("Question", parentID);
        try{
            Entity entity = datastore.get(parent);
            String user = (String) entity.getProperty("user");
            String idString = entity.getKey().getName();
            UUID id = UUID.fromString(idString);
            //UserID is unique, so this should only ever resolve to one message.
            String text = (String) entity.getProperty("text");
            long timestamp = (long) entity.getProperty("timestamp");
            String kids = (String) entity.getProperty("children");
            
            Question message = new Question(id, user, text, timestamp);
            
            if (kids.length() > 0){
                List<UUID> children = new ArrayList<>();
                List<String> itemList = Arrays.asList(kids.split(":"));
                for (String childID : itemList){
                    children.add(UUID.fromString(childID));
                }
                message.setChildren(children);
            }
            return message;
        }catch(Exception e){
            System.out.println("ugh");
            System.out.println("couldn't find it?");
            e.printStackTrace();
        }
        
        Question thing = new Question("nobody", "debug");
        return thing;
    }
    
    public List<Question> getAllQuestions(){
        List<Question> messages = new ArrayList<Question>();
        
        Query query = new Query("Question")
        .addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
        
        return questionHelper("getAllMessages", messages, query, results);
    }
    
    public List<Question> questionHelper(String userOrAll, List<Question> messages, Query query, PreparedQuery results) {
        for (Entity entity : results.asIterable()) {
            try {
                String idString = entity.getKey().getName();
                UUID id = UUID.fromString(idString);
                
                String user;
                //The users need only be specified when all messages of possibly more than one user is being shown
                if(userOrAll.equals("getAllMessages")) {
                    user = (String) entity.getProperty("user");
                }
                else {
                    user = userOrAll;
                }
                
                String text = (String) entity.getProperty("text");
                long timestamp = (long) entity.getProperty("timestamp");
                String kids = (String) entity.getProperty("children");
                
                Question message = new Question(id, user, text, timestamp);
                if (kids == null){
                    kids = "";
                }
                System.out.println(kids);
                System.out.println(kids.length());
                if (kids.length() > 0){
                    List<UUID> children = new ArrayList<>();
                    List<String> itemList = Arrays.asList(kids.split(":"));
                    for (int i = 0; i < itemList.size(); i++){
                        System.out.println(itemList.get(i));
                        children.add(UUID.fromString(itemList.get(i)));
                    }
                    message.setChildren(children);
                }
                
                messages.add(message);
            } catch (Exception e) {
                System.err.println("Error reading message.");
                System.err.println(entity.toString());
                e.printStackTrace();
            }
        }
        
        return messages;
    }

public Set<String> getUsers(){
  Set<String> users = new HashSet<>();
  Query query = new Query("Question");
  PreparedQuery results = datastore.prepare(query);
  for(Entity entity : results.asIterable()) {
    users.add((String) entity.getProperty("user"));
  }
  return users;
}

}
