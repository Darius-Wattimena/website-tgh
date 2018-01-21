package db.criteria;

import db.Criteria;
import db.SqlBuilder;
import db.entity.Permission;
import db.entity.Role;

public class RolePermissionCriteria extends Criteria {

    private Role role;
    private Permission permission;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT rp FROM RolePermission rp ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE rp FROM RolePermission rp ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (role != null) {
            sql.append("AND rp.role_id = :rid");
            sql.addParameter("rid", role.getId());
        }

        if (permission != null) {
            sql.append("AND rp.permission_id = :pid");
            sql.addParameter("pid", permission.getId());
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
