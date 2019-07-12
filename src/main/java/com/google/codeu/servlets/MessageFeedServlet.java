 package com.google.codeu.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.codeu.data.Question;
import com.google.gson.Gson;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles fetching all messages for the public feed.
 */
@WebServlet("/feed")
public class MessageFeedServlet extends HttpServlet{
  
 private Datastore datastore;

 @Override
 public void init() {
  datastore = new Datastore();
 }
 
 /**
  * Responds with a JSON representation of Message data for all users.
  */
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {

  response.setContentType("application/json");
  
  List<Question> messages = datastore.getAllQuestions();
       for (Question question : messages){
           List<Message> kids = new ArrayList<>();
           for (UUID id : question.getAnswers()){
               kids.add(datastore.getIDMessage(id.toString()));
           }
           question.setHack(kids);
       }
  Gson gson = new Gson();
  String json = gson.toJson(messages);
  
  response.getWriter().println(json);
 }
}
