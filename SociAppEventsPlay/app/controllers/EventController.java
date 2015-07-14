package controllers;

import models.AccessToken;
import models.Event;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shary on 09/07/2015.
 */
public class EventController extends Controller {
   public Result createEvent(Integer id) {
        return ok();
    }
 /*
    public Result getUserEvent(Integer id) {
        User user = User.find.byId(id);
        List<Event> events = user.getEvents();
        return ok(Json.toJson(events));
    }

    public Result inviteUser(Integer id1, Integer id2) {
        return ok();
    }*/

    public Result addEvent(int userId) {
        if (!request().hasHeader("ACCESS_TOKEN")) {
            return badRequest("ACCESS_TOKEN header is required.");
        }

        String token = request().getHeader("ACCESS_TOKEN");

        AccessToken accessToken = AccessToken.find.where()
                .eq("token", token)
                .findUnique();

        if (accessToken == null) {
            return unauthorized("Invalid access token.");
        }

        User user = accessToken.getUser();

        if (user.getId() != userId) {
            return forbidden("You don't have permission to add an event for this user.");
        }

        Event event = Json.fromJson(request().body().asJson(), Event.class);
        event.setHost(user);

        List<User> guests = new ArrayList<>();
        guests.add(user);

        event.setGuests(guests);

        event.save();

        return created(Json.toJson(event));
    }

    /**
     * @param userId  Id of the event's host.
     * @param eventId Id of the event.
     * @return Http status 200 OK.
     */
    public Result inviteGuest(int userId, int eventId) {
        if (!request().hasHeader("ACCESS_TOKEN")) {
            return badRequest("ACCESS_TOKEN header is required.");
        }

        String token = request().getHeader("ACCESS_TOKEN");

        AccessToken accessToken = AccessToken.find.where()
                .eq("token", token)
                .findUnique();

        if (accessToken == null) {
            return unauthorized("Invalid access token.");
        }

        User user = accessToken.getUser();

        if (user.getId() != userId) {
            return forbidden("You don't have permission to invite users to this event.");
        }

        Event event = Event.find.byId(eventId);

        if (event == null) {
            return notFound("Event doesn't exist.");
        }

        if (event.getHost().getId() != userId) {
            return forbidden("You don't have permission to invite users to this event.");
        }

        String guestEmail = Json.fromJson(request().body().asJson(), String.class);

        User guest = User.find.where()
                .eq("email", guestEmail)
                .findUnique();

        if (guest == null) {
            notFound("User doesn't exist.");
        }

        List<User> guests = event.getGuests();

        for (User u : guests) {
            if (u.getId() == guest.getId()) {
                return badRequest("User " + guest.getId() + " already is a guest of event " + event.getId());
            }
        }

        guests.add(guest);
        event.save();

        return ok();
    }

    /**
     * @param userId The id of the user.
     * @return A list of the events to which the user is invited.
     */
    public Result getUserEvents(int userId) {
        if (!request().hasHeader("ACCESS_TOKEN")) {
            return badRequest("ACCESS_TOKEN header is required.");
        }

        String token = request().getHeader("ACCESS_TOKEN");

        AccessToken accessToken = AccessToken.find.where()
                .eq("token", token)
                .findUnique();

        if (accessToken == null) {
            return unauthorized("Invalid access token.");
        }

        User user = accessToken.getUser();

        if (user.getId() != userId) {
            return forbidden("You don't have permission to view events for this user.");
        }

        List<Event> events = user.getEvents();

        return ok(Json.toJson(events));
    }

}
