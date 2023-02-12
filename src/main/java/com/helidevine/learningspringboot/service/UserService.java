package com.helidevine.learningspringboot.service;

import com.helidevine.learningspringboot.dao.UserDao;
import com.helidevine.learningspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();
        if (!gender.isPresent()) {
            return users;
        }
        try {
            User.Gender theGender = User.Gender.valueOf(gender.get().toUpperCase());
            return users.stream().filter(user -> user.getGender().equals(theGender)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid gender", e);
        }
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid(userUid);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (optionalUser.isPresent()) {
            return userDao.updateUser(user);
        }
        throw new NotFoundException("user " + user.getUserUid() + "doesn't exist");
    }

    public int removeUser(UUID uid) {
        UUID userUid = getUser(uid)
                .map(User::getUserUid)
                .orElseThrow(() -> new NotFoundException("user " + uid + "doesn't exist"));
        return userDao.deleteUserByUserUid(userUid);
    }

    public int insertUser(User user) {
        UUID userUid = user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid();
        return userDao.insertUser(userUid, User.newUser(userUid, user));
    }

    private void validateUser(User user) {
        requireNonNull(user.getFirstName(), "first name required");
        requireNonNull(user.getLastName(), "last name required");
        requireNonNull(user.getAge(), "age required");
        //validate email
        requireNonNull(user.getEmail(), "email required");
        requireNonNull(user.getGender(), "gender required");
    }
}
