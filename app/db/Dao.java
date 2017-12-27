package db;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public abstract class Dao<T extends Entity, U extends Criteria> {

    private JPAApi api;
    private DatabaseExecutionContext context;

    private Class<T> tClass;
    private String tName;

    @Inject
    public Dao(JPAApi api, DatabaseExecutionContext context, Class<T> tClass) {
        this.api = api;
        this.context = context;
        this.tClass = tClass;
        this.tName = tClass.getSimpleName();
    }

    public T findFirst() {
        try {
            return supplyAsync(() -> wrap(em -> findFirst(em, "SELECT t FROM " + tName + " t")), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T findFirstByCriteria(U criteria) {
        StringBuilder sql = new StringBuilder();
        criteria.onSelect(sql);
        sql.append("where 1=1 ");
        criteria.build(sql);

        try {
            return supplyAsync(() -> wrap(em -> findFirst(em, sql)), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll() {
        try {
            return supplyAsync(() -> wrap(em -> find(em, "SELECT t FROM " + tName + " t")), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<T> findByCritria(U criteria) {
        StringBuilder sql = new StringBuilder();
        criteria.onSelect(sql);
        sql.append("where 1=1 ");
        criteria.build(sql);
        try {
            return supplyAsync(() -> wrap(em -> find(em, sql)), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public T save(T entity) {
        try {
            if (entity.getId() == null) {
                return create(entity).get();
            } else {
                return update(entity).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CompletableFuture<T> create(T entity) {
        return supplyAsync(() -> wrap(em -> {
            em.persist(entity);
            return entity;
        }), context);
    }

    private CompletableFuture<T> update(T entity) {
        return supplyAsync(() -> wrap(em -> {
            em.merge(entity);
            return entity;
        }), context);
    }

    public int deleteAll() {
        try {
            return supplyAsync(() -> wrap(em -> delete(em, "DELETE t FROM " + tName + " t")), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteByCriteria(U criteria) {
        StringBuilder sql = new StringBuilder();
        criteria.onDelete(sql);
        sql.append("where 1=1 ");
        criteria.build(sql);

        try {
            return supplyAsync(() -> wrap(em -> delete(em, sql)), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private T findFirst(EntityManager em, String sql) {
        return typedQuery(em, sql).getSingleResult();
    }

    private T findFirst(EntityManager em, StringBuilder sql) {
        return typedQuery(em, sql).getSingleResult();
    }

    private List<T> find(EntityManager em, String sql) {
        return typedQuery(em, sql).getResultList();
    }

    private List<T> find(EntityManager em, StringBuilder sql) {
        return typedQuery(em, sql).getResultList();
    }

    private int delete(EntityManager em, String sql) {
        return typedQuery(em, sql).executeUpdate();
    }

    private int delete(EntityManager em, StringBuilder sql) {
        return typedQuery(em, sql).executeUpdate();
    }

    private <S> S wrap(Function<EntityManager, S> function) {
        return api.withTransaction(function);
    }

    private TypedQuery<T> typedQuery(EntityManager em, StringBuilder sql) {
        return typedQuery(em, sql.toString());
    }

    private TypedQuery<T> typedQuery(EntityManager em, String sql) {
        return em.createQuery(sql, tClass);
    }
}
