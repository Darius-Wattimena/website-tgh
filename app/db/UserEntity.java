package db;

import controllers.Application;
import db.entity.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class UserEntity extends DateEntity {

    @ManyToOne
    @JoinColumn
    private User createdBy;

    @ManyToOne
    @JoinColumn
    private User modifiedBy;

    @Override
    public void onSave() {
        super.onSave();
        if (getId() == null) {
            createdBy = Application.getCurrentUser();
            modifiedBy = Application.getCurrentUser();
        } else {
            modifiedBy = Application.getCurrentUser();
        }
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
