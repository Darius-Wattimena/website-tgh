package db.criteria;

import db.Criteria;

public class UserCriteria extends Criteria {

    @Override
    public void onSelect(StringBuilder sql) {
        sql.append("SELECT u FROM User u ");
    }

    @Override
    public void onDelete(StringBuilder sql) {
        sql.append("DELETE u FROM User u ");
    }

    @Override
    public void build(StringBuilder sql) {

    }
}
