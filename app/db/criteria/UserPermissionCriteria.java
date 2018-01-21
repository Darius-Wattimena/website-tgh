package db.criteria;

import db.Criteria;
import db.SqlBuilder;
import db.entity.Permission;
import db.entity.User;

public class UserPermissionCriteria extends Criteria {

    private User user;
    private Permission permission;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT up FROM UserPermission up ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE up FROM UserPermission up ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (user != null) {
            sql.append("AND up.user_id = :uid");
            sql.addParameter("uid", user.getId());
        }

        if (permission != null) {
            sql.append("AND up.permission_id = :pid");
            sql.addParameter("pid", permission.getId());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
