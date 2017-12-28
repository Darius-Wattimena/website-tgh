package db.criteria;

import db.Criteria;
import db.SqlBuilder;

public class UserCriteria extends Criteria {

    private Long id;
    private String username;
    private boolean usernameSearching;

    @Override
    public void onSelect(SqlBuilder sql) {
        sql.append("SELECT u FROM User u ");
    }

    @Override
    public void onDelete(SqlBuilder sql) {
        sql.append("DELETE u FROM User u ");
    }

    @Override
    public void build(SqlBuilder sql) {
        if (id != null) {
            sql.append("AND u.id = :id");
            sql.addParameter("id", id);
        }

        if (username != null && !username.isEmpty()) {
            if (usernameSearching) {
                sql.append("AND UPPER(u.username) LIKE UPPER(:name)");
                sql.addParameter("name", "%" + username.toUpperCase() + "%");
            } else {
                sql.append("AND UPPER(u.username) = UPPER(:name)");
                sql.addParameter("name", username.toUpperCase());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setSearchUsername(String name) {
        this.username = name;
        usernameSearching = true;
    }
}
