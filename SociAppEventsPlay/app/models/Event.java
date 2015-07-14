package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Event extends Model {
    @Id
    private Integer id;
    private String name;


    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private Date day;

    /*@Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date dueDate = new Date();*/

    private String description;

    @JsonIgnore
    @ManyToOne
    private User host;

    @JsonIgnore
    @ManyToMany
    private List<User> guests;


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static Finder<Integer, Event> find =
            new Finder(Event.class);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public List<User> getGuests() {
        return guests;
    }

    public void setGuests(List<User> guests) {
        this.guests = guests;
    }
}
