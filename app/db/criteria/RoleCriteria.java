package db.criteria;

import db.Criteria;
import db.SqlBuilder;

public class RoleCriteria extends Criteria {

    private Long id;
    private String name;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT r FROM Role r ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE r FROM Role r ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (id != null) {
            sql.append("AND r.id = :id");
            sql.addParameter("id", id);
        }

        if (name != null && !name.isEmpty()) {
            sql.append("AND UPPER(r.name) = UPPER(:name)");
            sql.addParameter("name", name.toUpperCase());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
