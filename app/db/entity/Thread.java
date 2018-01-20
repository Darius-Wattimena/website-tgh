package db.entity;

import db.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Thread extends UserEntity {

    @Column
    private String subject;

    @ManyToOne
    @JoinColumn
    private Forum forumId;

    @Column
    private Boolean active = true;

    @Column
    private Boolean locked = false;

    @Column
    private Boolean sticky = false;

    @Column
    private Integer viewCount = 0;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Forum getForumId() {
        return forumId;
    }

    public void setForumId(Forum forumId) {
        this.forumId = forumId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
