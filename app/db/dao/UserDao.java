package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.criteria.UserCriteria;
import db.entity.User;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

public class UserDao extends Dao<User, UserCriteria> {

    @Inject
    public UserDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, User.class);
    }
}
