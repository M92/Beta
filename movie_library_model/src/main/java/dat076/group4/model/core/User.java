package dat076.group4.model.core;

import dat076.group4.model.persistence.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    public User() {}

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
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
