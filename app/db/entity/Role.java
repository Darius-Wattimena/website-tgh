package db.entity;

import db.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role extends UserEntity {

    @Column
    private String name;

    @OneToMany
    @JoinColumn
    private List<RolePermission> rolePermissions = new ArrayList<>();

    @OneToMany
    @JoinColumn
    private List<UserRole> userRoles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
