package eduardo.com.example.acer.sociappevents.Rest;

import java.util.Date;

/**
 * Created by ACER on 12/07/2015.
 */
public class EventsData {

    private String name;
    private String day;
    private int id;
    private String description;

    public EventsData() {

    }

    public EventsData(String name, String day, int id, String description) {
        this.name = name;
        this.day = day;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name" + name +
                "day" + day +
                "id" + id +
                "description" + description;
    }
}
