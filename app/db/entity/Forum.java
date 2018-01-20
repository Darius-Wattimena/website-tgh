package db.entity;

import db.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Forum extends UserEntity {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
