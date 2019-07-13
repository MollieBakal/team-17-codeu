package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.codeu.data.Question;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/answers")
public class AnswerServlet extends HttpServlet{
    //
    //@Override
    //public void doGet(HttpServletRequest request, HttpServletResponse response){
        
    //}
    private Datastore datastore;
    
    
    @Override
    
    public void init() {
        
        datastore = new Datastore();
        
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/index.html");
            return;
        }
        
        String user = userService.getCurrentUser().getEmail();
        Whitelist whitelist = Whitelist.simpleText();
        whitelist.addTags("ins", "strike", "sub", "sup");
        String text = Jsoup.clean(request.getParameter("text"), whitelist);
        
        Message message = new Message(user, text);
        String ID = request.getParameter("parent");
        Question parent = datastore.getIDQuestion(ID);
        parent.addAnswer(message);
        datastore.storeQuestion(parent);
        datastore.storeMessage(message);
        response.sendRedirect("/user-page.html?user=" + user);
        

    }
}
