package db.criteria;

import db.Criteria;
import db.SqlBuilder;

public class PermissionCriteria extends Criteria {

    private String name;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT p FROM Permission p ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE p FROM Permission p ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (name != null && !name.isEmpty()) {
            sql.append("AND UPPER(p.name) = UPPER(:name)");
            sql.addParameter("name", name.toUpperCase());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
