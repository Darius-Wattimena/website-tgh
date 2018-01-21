package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.criteria.UserPermissionCriteria;
import db.entity.UserPermission;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class UserPermissionDao extends Dao<UserPermission, UserPermissionCriteria> {

    @Inject
    public UserPermissionDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, UserPermission.class);
    }

}
