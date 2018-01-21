package db.entity;

import db.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Permission extends UserEntity {

    @Column
    private String name;

    @OneToMany
    @JoinColumn
    private List<RolePermission> roles = new ArrayList<>();

    @OneToMany
    @JoinColumn
    private List<UserPermission> users = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RolePermission> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePermission> roles) {
        this.roles = roles;
    }

    public List<UserPermission> getUsers() {
        return users;
    }

    public void setUsers(List<UserPermission> users) {
        this.users = users;
    }
}
