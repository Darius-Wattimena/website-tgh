package controllers;

import db.dao.UserDao;
import db.entity.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

public class UserController extends Controller {

    @Inject
    private FormFactory ff;

    @Inject
    private UserDao dao;

    public Result index() {
        return ok(views.html.User.add.render());
    }

    public Result add() {
        Form<User> userForm = ff.form(User.class);
        User user = userForm.bindFromRequest().get();
        dao.save(user);
        return getAll();
    }

    public Result getAll() {
        List<User> users = dao.findAll();
        Content html = views.html.User.get.render(users);
        return ok(html);
    }
}
