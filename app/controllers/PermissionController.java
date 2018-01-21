package controllers;

import db.criteria.PermissionCriteria;
import db.criteria.RolePermissionCriteria;
import db.criteria.UserPermissionCriteria;
import db.criteria.UserRoleCriteria;
import db.dao.*;
import db.entity.*;
import play.data.FormFactory;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PermissionController extends BaseController<Permission, PermissionCriteria> {

    @Inject
    private UserDao userDao;

    @Inject
    private UserPermissionDao userPermissionDao;

    @Inject
    private UserRoleDao userRoleDao;

    @Inject
    private RolePermissionDao rolePermissionDao;

    @Inject
    public PermissionController(FormFactory ff, PermissionDao dao) {
        super(ff, dao);
    }

    public Result onFindUserPermissions(Long userId) {
        User user = userDao.findById(userId);
        List<String> permissions = getPermissions(user);
        Content html = views.html.Permission.user_permission.render(user, permissions);
        return ok(html);
    }

    public List<String> getPermissions(User user) {
        List<String> permissions = new ArrayList<>();

        UserPermissionCriteria userPermissionCriteria = new UserPermissionCriteria();
        userPermissionCriteria.setUser(user);
        List<UserPermission> userPermissions = userPermissionDao.findByCritria(userPermissionCriteria);

        UserRoleCriteria userRoleCriteria = new UserRoleCriteria();
        userRoleCriteria.setUser(user);
        List<UserRole> userRoles = userRoleDao.findByCritria(userRoleCriteria);

        for (UserPermission userPermission : userPermissions) {
            String permission = userPermission.getPermission().getName();

            if (!permissions.contains(permission)) {
                permissions.add(userPermission.getPermission().getName());
            }
        }

        for (UserRole userRole : userRoles) {
            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
            rolePermissionCriteria.setRole(userRole.getRole());
            List<RolePermission> rolePermissions = rolePermissionDao.findByCritria(rolePermissionCriteria);

            for (RolePermission rolePermission : rolePermissions) {
                String permission = rolePermission.getPermission().getName();

                if (!permissions.contains(permission)) {
                    permissions.add(rolePermission.getPermission().getName());
                }
            }
        }

        return permissions;
    }
}
