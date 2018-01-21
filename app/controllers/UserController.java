package controllers;

import db.criteria.UserCriteria;
import db.dao.UserDao;
import db.entity.User;
import form.UserRegisterForm;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

public class UserController extends BaseController<User, UserCriteria> {

    private Form<User> userForm;
    private Form<UserRegisterForm> userRegisterForm;

    @Inject
    private PermissionController permissionController;

    @Inject
    public UserController(FormFactory ff, UserDao dao) {
        super(ff, dao);
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

    public Result register() {
        userRegisterForm = ff.form(UserRegisterForm.class);
        return ok(views.html.User.register.render(userRegisterForm));
    }

    public Result onRegister() {
        UserRegisterForm user = userRegisterForm.bindFromRequest().get();
        UserCriteria criteria = new UserCriteria();
        criteria.setUsername(user.getUsername());

        if (dao.findFirstByCriteria(criteria) != null) {
            return register(); // TODO add error already exists
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return register();
        }

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

        User databaseUser = new User();
        databaseUser.setUsername(user.getUsername());
        databaseUser.setPassword(hashedPassword);
        databaseUser.setSalt(salt);
        onSave(databaseUser);

        return redirect("users");
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
            return ok("Error user not found"); // TODO add error message
        }

        String password = databaseUser.getPassword();
        String salt = databaseUser.getSalt();

        String hashedUserInput = BCrypt.hashpw(user.getPassword(), salt);

        if (hashedUserInput.equals(password)) {
            List<String> permissions = permissionController.getPermissions(databaseUser);
            Application.setCurrentUser(databaseUser);
            Application.setCurrentUserPermissions(permissions);
            return getAll(); // TODO redirect to admin panel
        } else {
            return ok("Error wrong password"); // TODO add error message
        }
    }
}
