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
  
  //private ArrayList<String> advisees = new ArrayList<String>();
  //private ArrayList<String> advisors = new ArrayList<String>();

  public User(UUID id, String firstName, String lastName, String email, String aboutMe, Image profilePic) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.aboutMe = aboutMe;
    this.profilePic = profilePic;
    //this.advisees = advisees;
    //this.advisors = advisors;
  }
  //for compatibility with datastore
    public User(String email, String aboutMe){
        this(UUID.randomUUID(), "", "", email, aboutMe, null /*, new ArrayList<String>(),new ArrayList<String>()*/);
    }
    public User(String email, String fn, String ln, String aboutMe /*, ArrayList<String> advisees,ArrayList<String> advisors */){
        this(UUID.randomUUID(), fn, ln, email, aboutMe, null /*, advisees,advisors*/ );
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
    /*public ArrayList<String> getAdvisees(){
        return advisees;
    }
    
    public void addAdvisee(String email){
        this.advisees.add(email);
    }
    
    public void setAdvisees(ArrayList<String> newFriends){
        this.advisees = newFriends;
    }
    public ArrayList<String> getAdvisors(){
        return advisors;
    }
    
    public void addAdvisor(String email){
        this.advisors.add(email);
    }
    
    public void setAdvisors(ArrayList<String> newFriends){
        this.advisors = newFriends;
    }
     */
    public void setAboutMe(String newAM){
        this.aboutMe = newAM;
    }
    public void setFirstName(String fn){
        this.firstName = fn;
    }
    public void setLastName(String ln){
        this.lastName = ln;
    }
    /*public String getAdviseesToString(){
        String frie = "";
	if ((this.advisees).size() > 0){
          for(String fr: this.advisees){
            frie += fr;
            frie += " ";
          }
	}
        return frie;
  }
  public String getAdvisorsToString(){
        String frie = "";
	if ((this.advisors).size() > 0){
          for(String fr: this.advisors){
            frie += fr;
            frie += " ";
          }
	}
        return frie;
  }
     */
}
