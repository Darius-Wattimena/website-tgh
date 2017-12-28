package db.entity;

import db.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post extends UserEntity {

    @Column
    private String postSubject;

    @Column
    private String postMessage;

    @ManyToOne
    @JoinColumn
    private Thread thread;

    @ManyToOne
    @JoinColumn
    private Post replyTo;
}
