package com.helidevine.learningspringboot.service;

import com.helidevine.learningspringboot.dao.FakeDataDao;
import com.helidevine.learningspringboot.dao.UserDao;
import com.helidevine.learningspringboot.model.User;

import java.util.List;
import java.util.UUID;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new FakeDataDao();
    }

    public List<User> getAllUsers() {
        return null;
    }

    public User getUser(UUID userUid) {
        return null;
    }

    public int updateUser(User user) {
        return 1;
    }

    public int removeUser(UUID userUid) {
        return 1;
    }
    public int insertUser(UUID userUid, User user) {
        return 1;

    }
}
