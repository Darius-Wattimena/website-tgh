package db;

import java.util.HashMap;
import java.util.Map;

public class SqlBuilder {
    private Map<String, Object> parameters = new HashMap<>();
    private StringBuilder sql;

    public SqlBuilder(StringBuilder sql) {
        this.sql = sql;
    }

    public SqlBuilder append(String sql) {
        this.sql.append(sql);
        return this;
    }

    public SqlBuilder addParameter(String key, Object value) {
        parameters.put(key, value);
        return this;
    }

    public String getSql() {
        return sql.toString();
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
