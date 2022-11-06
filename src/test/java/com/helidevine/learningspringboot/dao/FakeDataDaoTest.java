package com.helidevine.learningspringboot.dao;

import com.helidevine.learningspringboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() throws Exception {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    void shouldSelectAllUsers() throws Exception {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);

        User user = users.get(0);

        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Jo");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("jo.jones@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    void shoouldSelectUserByUserUid() throws Exception {

        UUID annaUserUid = UUID.randomUUID();

        User userAnna = new User(annaUserUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        fakeDataDao.insertUser(annaUserUid, userAnna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaUserUid);

        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualTo(userAnna);
    }

    @Test
    void shouldNotSelectUserByUserByRandomUserUid() throws Exception {

        Optional<User> userRandom = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(userRandom.isPresent()).isFalse();
    }

    @Test
    void shouldUpdateUser() throws Exception {

        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newJoe = new User(joeUserUid,
                "joe", "montana", User.Gender.MALE, 30, "anna@gmail.com");

        fakeDataDao.updateUser(newJoe);
        Optional<User> updatedJoe = fakeDataDao.selectUserByUserUid(joeUserUid);
        assertThat(updatedJoe.isPresent()).isTrue();

        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(updatedJoe.get()).isEqualTo(newJoe);
    }

    @Test
    void deleteUserByUserUid() throws Exception {

        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserByUserUid(joeUserUid);

        assertThat(fakeDataDao.selectUserByUserUid(joeUserUid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    void insertUser() throws Exception {

        UUID newUserUid = UUID.randomUUID();

        User newUser = new User(newUserUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");
        fakeDataDao.insertUser(newUserUid, newUser);

        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(newUserUid).get()).isEqualTo(newUser);
    }
}