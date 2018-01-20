package db.entity;

import db.DateEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends DateEntity {

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String salt;

    @Column
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
