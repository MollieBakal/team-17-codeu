package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Responds with a hard-coded message for testing purposes.
 */
@WebServlet("/advisors")
public class AdvisorsServlet extends HttpServlet{
  private Datastore datastore;
 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {
  
  	

   response.setContentType("application/json");

   String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<String> advisors = datastore.getUser(user).getFriends();


   Gson gson = new Gson();
    String json = gson.toJson(advisors);
    response.getOutputStream().println(json);

 }
}