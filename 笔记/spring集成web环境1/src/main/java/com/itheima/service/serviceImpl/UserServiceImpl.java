package com.itheima.service.serviceImpl;

import com.itheima.dao.UserDao;
import com.itheima.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void save() {
        dao.save();
    }
}
