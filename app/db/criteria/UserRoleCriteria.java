package db.criteria;

import db.Criteria;
import db.SqlBuilder;
import db.entity.User;

public class UserRoleCriteria extends Criteria {

    private User user;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT ur FROM UserRole ur ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE ur FROM UserRole ur ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (user != null) {
            sql.append("AND ur.user_id = :uid");
            sql.addParameter("uid", user.getId());
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
