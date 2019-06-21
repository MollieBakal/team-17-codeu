package com.google.codeu.data;

import java.awt.Image;
import java.util.UUID;

public class User {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String aboutMe;
  private Image profilePic;
  

  public User(UUID id, String firstName, String lastName, String email, String aboutMe, Image profilePic) {
    this.id = id;
    this.firstName = firstName;
    this.firstName = lastName;
    this.email = email;
    this.aboutMe = aboutMe;
    this.profilePic = profilePic;
  }
  //for compatibility with datastore
    public User(String email, String aboutMe){
        this(UUID.randomUUID(), "", "", email, aboutMe, null);
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
}
