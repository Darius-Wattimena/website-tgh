package db.entity;

import db.Entity;

import javax.persistence.Column;

@javax.persistence.Entity
public class User extends Entity {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
