package controllers;

import db.criteria.UserCriteria;
import db.dao.UserDao;
import db.entity.User;
import org.mindrot.jbcrypt.BCrypt;
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
        User user = userForm.bindFromRequest().get();
        UserCriteria criteria = new UserCriteria();
        criteria.setUsername(user.getUsername());

        if (dao.findFirstByCriteria(criteria) != null) {
            return index(); // TODO add error already exists
        }

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        dao.save(user);

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

    public Result login() {
        userForm = ff.form(User.class);
        return ok(views.html.User.login.render(userForm));
    }

    public Result onLogin() {
        User user = userForm.bindFromRequest().get();
        UserCriteria criteria = new UserCriteria();
        criteria.setUsername(user.getUsername());
        User databaseUser = dao.findFirstByCriteria(criteria);

        if (databaseUser == null) {
            return ok(); // TODO add error message
        }

        String password = databaseUser.getPassword();
        String salt = databaseUser.getSalt();

        String hashedUserInput = BCrypt.hashpw(user.getPassword(), salt);

        if (hashedUserInput.equals(password)) {
            Application.setCurrentUser(databaseUser);
            return ok(); // TODO redirect to admin panel
        } else {
            return ok(); // TODO add error message
        }
    }
}
