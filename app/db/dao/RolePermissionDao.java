package db.dao;

import db.Dao;
import db.DatabaseExecutionContext;
import db.SqlBuilder;
import db.criteria.RolePermissionCriteria;
import db.entity.Permission;
import db.entity.Role;
import db.entity.RolePermission;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class RolePermissionDao extends Dao<RolePermission, RolePermissionCriteria> {

    @Inject
    public RolePermissionDao(JPAApi api, DatabaseExecutionContext context) {
        super(api, context, RolePermission.class);
    }

    public List<Permission> findAllRolePermissions(List<Role> roles) {
        SqlBuilder query = new SqlBuilder(new StringBuilder());

        List<RolePermission> result;
        Map<Long, Permission> permissions = new HashMap<>();
        try {
            query.append("SELECT rp FROM RolePermission rp WHERE 1=1");
            query.append("AND (");
            for (int i = 0; i < roles.size(); i++) {
                if (i + 1 != roles.size()) {
                    query.append("rp.role_id == :role_id" + i);
                    query.append("OR");
                    query.addParameter(":role_id" + i, roles.get(i).getId());
                } else {
                    query.append("rp.role_id == :role_id" + i);
                    query.addParameter(":role_id" + i, roles.get(i).getId());
                }
            }
            query.append(")");

            result = supplyAsync(() -> wrap(em -> find(em, query.toString())), context).get();

            for (RolePermission item : result) {
                permissions.put(item.getPermission().getId(), item.getPermission());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(permissions.values());
    }

}
