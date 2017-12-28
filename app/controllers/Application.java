package controllers;

import db.entity.User;
import play.mvc.Controller;

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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Application.currentUser = currentUser;
    }
}
