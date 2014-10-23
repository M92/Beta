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
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<MovieList> lists;
    
    protected User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        lists = new ArrayList<>();
    }

    public void addList(MovieList list){
        lists.add(list);
    }

    public MovieList newList() {
        MovieList list = new MovieList(this);
        lists.add(list);
        return list;
    }
    
    public List<MovieList> getLists(){
        return lists;
    }
    
    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", " + "nickname=" + nickname + ", email=" + email + '}';
    }
}
