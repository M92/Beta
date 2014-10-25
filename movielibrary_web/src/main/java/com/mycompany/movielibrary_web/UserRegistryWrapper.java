package com.mycompany.movielibrary_web;


import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.User;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Need this because we have an immutable class User
 * (JAX-RS must have default constructor)
 * 
 * Possible other solution: Change in model to make User mutable or 
 * http://blog.bdoughan.com/2010/12/jaxb-and-immutable-objects.html
 * @author hajo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "User", propOrder = {
    "id",
    "nickname",
    "lists"
})
public class UserRegistryWrapper {

    private User user;

    protected UserRegistryWrapper() { // Must have
    }
   
    public UserRegistryWrapper(User user) { 
        this.user = user; 
    }
    
//    @XmlElement
//    public Long getOAuth() {
//        return user.getOAuth();
//    }
    
//    @XmlElement
//    public void addList(MovieList list){
//        user.addList(list);
//    }
//    
//    @XmlElement
//    public MovieList newList(){
//        return user.newList();
//    }
    
    @XmlElement
    public String getNickname() {
        return user.getNickname();
    }

    @XmlElement
    public List<MovieList> getLists(){
        return user.getLists();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public Long getId() {
        return user.getId();
    }
}
