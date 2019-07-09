package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Request;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
    String advisor = request.getParameter("text");

    Request req = new Request(user,advisor);
    datastore.storeRequest(req);
    System.out.println("Request made");

    response.sendRedirect("/advisors.html?user=" + user);
}

}
