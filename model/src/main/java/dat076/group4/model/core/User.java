package dat076.group4.model.core;

import dat076.group4.model.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A user
 */
@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private Long oauth;
    
    @Column(nullable = false, unique = true)
    private String nickname;
    
    /*@Column(nullable = false, unique = true)
    private String email;
*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<MovieList> lists;
    
    protected User() {}
    
    public User(Long oauth, String nickname) {
        this.oauth = oauth;
        this.nickname = nickname;
      //  this.email = email;
        lists = new ArrayList<>();
    }

    public void addList(MovieList list){
        lists.add(list);
    }

    public void deleteMovieList(Long id){
        for (MovieList list : lists){
            if(list.getId().equals(id)){
                lists.remove(list);
                break;
            }
        }
    }
    
    public MovieList getList(Long id){
        for (MovieList list : lists){
            if(list.getId().equals(id)){
                return list;
            }
        }
        return null;
    }
    
    public MovieList newList() {
        MovieList list = new MovieList(this);
        lists.add(list);
        return list;
    }
    
    public Long getOAuth(){
        return oauth;
    }
    
    public List<MovieList> getLists(){
        return lists;
    }
    
    public String getNickname() {
        return nickname;
    }
 

   /* public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
*/
    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", " + "nickname=" + nickname + '}';
    }
}
