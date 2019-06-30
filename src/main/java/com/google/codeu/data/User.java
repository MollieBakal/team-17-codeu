package com.google.codeu.data;

import java.awt.Image;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class User {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String aboutMe;
  private Image profilePic;
  
  private List<String> friends = new ArrayList<String>();

  public User(UUID id, String firstName, String lastName, String email, String aboutMe, Image profilePic, List<String> friends) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.aboutMe = aboutMe;
    this.profilePic = profilePic;
      this.friends = friends;
  }
  //for compatibility with datastore
    public User(String email, String aboutMe){
        this(UUID.randomUUID(), "", "", email, aboutMe, null, new ArrayList<String>());
    }
    public User(String email, String fn, String ln, String aboutMe, List<String> friends){
        this(UUID.randomUUID(), fn, ln, email, aboutMe, null, friends);
    }
    
  public UUID getID(){
    return id;
  }

  public String getFirstName(){
    return firstName;
  }

  public String getLastName(){
    return lastName;
  }

  public String getEmail(){
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }
  
  public Image getProfilePic() {
    return profilePic;
  }
    public List<String> getFriends(){
        return friends;
    }
    
    public void addFriend(String email){
        this.friends.add(email);
    }
    
    public void setFriends(List<String> newFriends){
        this.friends = newFriends;
        
    }
    public String getFriendsToString(){
        String frie = "";
	if ((this.friends).size() > 0){
          for(String fr: this.friends){
            frie += fr;
            frie += " ";
          }
	}
        return frie;
  }
}
