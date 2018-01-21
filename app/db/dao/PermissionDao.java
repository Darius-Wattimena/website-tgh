package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.criteria.PermissionCriteria;
import db.entity.Permission;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class PermissionDao extends Dao<Permission, PermissionCriteria> {

    @Inject
    public PermissionDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, Permission.class);
    }

}
