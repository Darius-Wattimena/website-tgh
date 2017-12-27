package db;

public abstract class Criteria {

    public abstract void onSelect(StringBuilder sql);
    public abstract void onDelete(StringBuilder sql);
    public abstract void build(StringBuilder sql);
}
