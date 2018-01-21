package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.criteria.UserRoleCriteria;
import db.entity.UserRole;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class UserRoleDao extends Dao<UserRole, UserRoleCriteria> {

    @Inject
    public UserRoleDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, UserRole.class);
    }
}
