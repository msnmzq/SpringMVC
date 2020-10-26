package service;

import dao.UserDao;

public class UserService {
    private UserDao dao;
    public UserService(){}
    public void setDao() {}

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public UserDao getDao() {
        return dao;
    }
    public void save(){
        dao.save();
    }

}
