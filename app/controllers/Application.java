package controllers;

import db.entity.User;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    /*public static Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.,
                        routes.javascript.Users.get()
                )
        ).as("text/javascript");
    }*/

    private static User currentUser;
    private static List<String> currentUserPermissions = new ArrayList<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Application.currentUser = currentUser;
    }

    public static List<String> getCurrentUserPermissions() {
        return currentUserPermissions;
    }

    public static void setCurrentUserPermissions(List<String> currentUserPermissions) {
        Application.currentUserPermissions = currentUserPermissions;
    }
}
