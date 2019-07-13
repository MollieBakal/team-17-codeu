package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.codeu.data.Request;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


/**
 * Responds with a hard-coded message for testing purposes.
 */
@WebServlet("/requests")
public class RequestsServlet extends HttpServlet{
  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException {
   UserService userService = UserServiceFactory.getUserService();
   		response.setContentType("application/json");

   		String user = userService.getCurrentUser().getEmail();


    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    
    User userU = new User(user, null);
    datastore.storeUser(userU);
    List<Request> incomingRequests = datastore.getAllRequests();


    Gson gson = new Gson();
    String json = gson.toJson(incomingRequests);
    response.getOutputStream().println(json);

   }


   @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String user = userService.getCurrentUser().getEmail();
    User userU = new User(user, null);
    datastore.storeUser(userU);
    
    if(request.getParameterMap().containsKey("text")){
      String advisor = request.getParameter("text");
      Request req = new Request(user,advisor);
      datastore.storeRequest(req);
      System.out.println("Request made");
    }
    if(request.getParameterMap().containsKey("requester")&&request.getParameterMap().containsKey("status")){
      String yesOrNo = request.getParameter("status");
      String requester = request.getParameter("requester");
      String idString = request.getParameter("reqID");
      //System.out.println(idString);

      Request aRequest = datastore.getRequestByID(idString);
      if (aRequest == null){      
        System.out.println("No request found.");
      }
      else{
        if(yesOrNo.equals("Accept")){
          //System.out.println("yes");
          aRequest.accept();
          /*
	  User requestee = datastore.getUser(user);
          requestee.addAdvisee(requester);
          datastore.storeUser(requestee);
          

          User requestor = datastore.getUser(requester);
          requestor.addAdvisor(user);
          datastore.storeUser(requestor);
	  */

        }
        else{
          //System.out.println("no");
          aRequest.deny();
        }

        datastore.storeRequest(aRequest);
        
      }

      }
    response.sendRedirect("/advisors.html?user=" + user);
}

}
