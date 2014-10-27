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

    @OneToMany(cascade = CascadeType.ALL,
               orphanRemoval = true,
               mappedBy = "user")
    private List<MovieList> lists;
    
    protected User() {}
    
    public User(Long oauth, String nickname) {
        this.oauth = oauth;
        this.nickname = nickname;
        lists = new ArrayList<>();
    }

    public Long getOAuth() {
        return oauth;
    }

    public String getNickname() {
        return nickname;
    }

    public List<MovieList> getLists() {
        return lists;
    }

    public MovieList getList(Long id) {
        for (MovieList list : lists) {
            if (list.getId().equals(id)) {
                return list;
            }
        }
        return null;
    }

    public void addList(MovieList list) {
        Long id = list.getId();
        if (id == null)
            lists.add(list);
        else if (getList(id) == null)
            lists.add(list);
    }

    public MovieList newList(String name) {
        MovieList list = new MovieList(this, name);
        lists.add(list);
        return list;
    }

    public void deleteList(Long id) {
        for (MovieList list : lists) {
            if (list.getId().equals(id)) {
                lists.remove(list);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "User{" + "oauth=" + oauth + ", nickname=" + nickname + ", lists=" + lists + '}';
    }
}
