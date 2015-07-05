package controllers;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class UserController extends Controller {
    public Result createUser() {
        User user = Json.fromJson(request().body().asJson(), User.class);
        user.save();
        return ok(Json.toJson(user));
    }

    public Result getAllUser() {
        List<User> Usuarios = User.find.all();
        return ok(Json.toJson(Usuarios));
    }

    public Result getOneUser(Integer id) {
        User user = User.find.byId(id);
        if (user == null) {
            return notFound();
        } else
            return ok(Json.toJson(user));
    }

    public Result deleteUser(Integer id) {
        User user = null;
        if (User.find.byId(id) != null) {
            User.find.deleteById(id);
            return ok();
        } else {
            return notFound();
        }
    }

    public Result updateUser(Integer id) {
        User oldUser = User.find.byId(id);
        User newUser = Json.fromJson(request().body().asJson(), User.class);
        if (oldUser == null) {
            return notFound();
        } else {
            oldUser.setName(newUser.getName());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setPassword(newUser.getPassword());
            oldUser.save();
            return ok(Json.toJson(oldUser));
        }
    }

}
