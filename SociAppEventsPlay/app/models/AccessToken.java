package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Shary on 30/06/2015.
 */
@Entity
public class AccessToken extends Model {
    @Id
    private Integer id;
    private String token;
    @ManyToOne
    private User user;

    public static Finder<Integer, AccessToken> find =
            new Finder(AccessToken.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
