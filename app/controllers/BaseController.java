package controllers;

import db.Criteria;
import db.Dao;
import db.Entity;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;

import java.util.List;

public class BaseController<T extends Entity, U extends Criteria> extends Controller {

    final FormFactory ff;
    final Dao<T, U> dao;

    BaseController(FormFactory ff, Dao<T, U> dao) {
        this.ff = ff;
        this.dao = dao;
    }

    List<T> onFind() {
        return dao.findAll();
    }

    void onSave(Form<T> form) {
        T entity = form.bindFromRequest().get();
        dao.save(entity);
    }

    void onDelete(T entity) {
        dao.deleteByEntity(entity);
    }

    void onDelete(Long id) {
        dao.deleteById(id);
    }
}
