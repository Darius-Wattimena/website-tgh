package db;

import db.dao.UserDao;

public class Database {

    private static Database instance;

    private UserDao userDao;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
