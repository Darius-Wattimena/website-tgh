package db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class DateEntity extends Entity {

    @Column
    private Date createdOn;

    @Column
    private Date modifiedOn;

    @Override
    public void onSave() {
        Date currentDate = new Date();
        if (getId() == null) {
            createdOn = currentDate;
        }
        modifiedOn = currentDate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
