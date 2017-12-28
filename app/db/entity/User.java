package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends db.DateEntity {

    @Column
    private String username;

    @Column
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
