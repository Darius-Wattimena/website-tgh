package db;

import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public abstract class Dao<T extends Entity, U extends Criteria> {

    protected JPAApi api;
    protected DatabaseExecutionContext context;

    protected Class<T> tClass;
    protected String tName;

    @Inject
    public Dao(JPAApi api, DatabaseExecutionContext context, Class<T> tClass) {
        this.api = api;
        this.context = context;
        this.tClass = tClass;
        this.tName = tClass.getSimpleName();
    }

    public T findById(Long id) {
        try {
            return supplyAsync(() -> wrap(em ->
                    typedQuery(em, "SELECT t FROM " + tName + " t WHERE t.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T findFirst() {
        try {
            return supplyAsync(() -> wrap(em -> findFirst(em, "SELECT t FROM " + tName + " t")), context).get();
        } catch (NoResultException e) {
            return null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T findFirstByCriteria(U criteria) throws NoResultException {
        SqlBuilder sqlBuilder = new SqlBuilder(new StringBuilder());
        criteria.onSelect(sqlBuilder);
        sqlBuilder.append("WHERE 1=1");
        criteria.build(sqlBuilder);

        try {
            return supplyAsync(() -> wrap(em -> findFirst(em, sqlBuilder)), context).get();
        } catch (NoResultException e) {
            return null;
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
        SqlBuilder sqlBuilder = new SqlBuilder(new StringBuilder());
        criteria.onSelect(sqlBuilder);
        sqlBuilder.append("WHERE 1=1");
        criteria.build(sqlBuilder);
        Logger.info(sqlBuilder.getSql());
        try {
            return supplyAsync(() -> wrap(em -> find(em, sqlBuilder)), context).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public T save(T entity) {
        try {
            entity.onSave();
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

    public void deleteByEntity(T entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        T entity = findById(id);
        entity.setDeleted(true);
        save(entity);
    }

    public void deleteAll() {
        List<T> entities = findAll();

        for (T entity : entities) {
            entity.setDeleted(true);
            save(entity);
        }
    }

    public void deleteByCriteria(U criteria) {
        List<T> entities = findByCritria(criteria);

        for (T entity : entities) {
            entity.setDeleted(true);
            save(entity);
        }
    }

    protected T findFirst(EntityManager em, String sql) {
        return typedQuery(em, sql).getSingleResult();
    }

    protected T findFirst(EntityManager em, SqlBuilder sql) {
        return typedQuery(em, sql).getSingleResult();
    }

    protected List<T> find(EntityManager em, String sql) {
        return typedQuery(em, sql).getResultList();
    }

    protected List<T> find(EntityManager em, SqlBuilder sql) {
        return typedQuery(em, sql).getResultList();
    }

    protected int delete(EntityManager em, String sql) {
        return typedQuery(em, sql).executeUpdate();
    }

    protected int delete(EntityManager em, SqlBuilder sql) {
        return typedQuery(em, sql).executeUpdate();
    }

    protected <S> S wrap(Function<EntityManager, S> function) {
        return api.withTransaction(function);
    }

    protected TypedQuery<T> typedQuery(EntityManager em, SqlBuilder sql) {
        TypedQuery<T> tq = em.createQuery(sql.getSql(), tClass);

        for (Map.Entry<String, Object> parameter : sql.getParameters().entrySet()) {
            tq.setParameter(parameter.getKey(), parameter.getValue());
        }

        return tq;
    }

    protected TypedQuery<T> typedQuery(EntityManager em, String sql) {
        return em.createQuery(sql, tClass);
    }
}
