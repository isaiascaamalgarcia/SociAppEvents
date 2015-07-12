package controllers;

import models.Event;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Shary on 09/07/2015.
 */
public class EventController extends Controller {
    public Result createEvent(Integer id) {
        return ok();
    }

    public Result getUserEvent(Integer id) {
        User user = User.find.byId(id);
        List<Event> events = user.getEvents();
        return ok(Json.toJson(events));
    }

    public Result inviteUser(Integer id1, Integer id2) {
        return ok();
    }
}
