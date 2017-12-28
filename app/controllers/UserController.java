package controllers;

import db.criteria.UserCriteria;
import db.dao.UserDao;
import db.entity.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

public class UserController extends BaseController<User, UserCriteria> {

    private Form<User> userForm;

    @Inject
    public UserController(FormFactory ff, UserDao dao) {
        super(ff, dao);
    }

    public Result index() {
        userForm = ff.form(User.class);
        return ok(views.html.User.add.render(userForm));
    }

    public Result add() {
        onSave(userForm);
        return redirect("users");
    }

    public Result getAll() {
        userForm = ff.form(User.class);
        List<User> users = onFind();
        Content html = views.html.User.get.render(users, userForm);
        return ok(html);
    }

    public Result onSearch() {
        User user = userForm.bindFromRequest().get();
        UserCriteria criteria = new UserCriteria();
        criteria.setSearchUsername(user.getUsername());
        List<User> users = dao.findByCritria(criteria);
        Content html = views.html.User.get.render(users, userForm);
        return ok(html);
    }
}
