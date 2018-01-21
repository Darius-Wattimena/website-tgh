package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.criteria.RoleCriteria;
import db.entity.Role;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class RoleDao extends Dao<Role, RoleCriteria> {

    @Inject
    public RoleDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, Role.class);
    }
}
