package db;

public abstract class Criteria {

    public abstract void onSelect(SqlBuilder sql);
    public abstract void onDelete(SqlBuilder sql);
    public abstract void build(SqlBuilder sql);
}
