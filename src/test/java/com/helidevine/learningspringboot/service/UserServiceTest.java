package com.helidevine.learningspringboot.service;

import com.helidevine.learningspringboot.dao.FakeDataDao;
import com.helidevine.learningspringboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jersey.repackaged.com.google.common.collect.ImmutableList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class UserServiceTest {
    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;


    @BeforeEach
    void setUp() {
        fakeDataDao = mock(FakeDataDao.class);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() {

        UUID annaUserUid = UUID.randomUUID();
        User userAnna = new User(annaUserUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>().add(userAnna).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);

        User user = users.get(0);

        assertAnnaFields(user);
    }

    @Test
    void shouldGetAllUsersByGender() {
        UUID annaUserUid = UUID.randomUUID();
        User userAnna = new User(annaUserUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        UUID joeUserUid = UUID.randomUUID();
        User userJoe = new User(joeUserUid,
                "joe", "jones", User.Gender.MALE, 45, "joe@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>()
                .add(userAnna)
                .add(userJoe)
                .build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));

        assertThat(filteredUsers).hasSize(1);
        assertAnnaFields(filteredUsers.get(0));


    }

    @Test
    void shouldThrowExceptionWhenGenderIsInvalid() {
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("notvalid")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid gender");
    }

    @Test
    void shouldGetUser() {

        UUID annaUid = UUID.randomUUID();
        User userAnna = new User(annaUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(userAnna));

        Optional<User> userOptional = userService.getUser(annaUid);

        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();

        assertAnnaFields(user);

    }

    @Test
    void shouldUpdateUser() {
        UUID annaUid = UUID.randomUUID();
        User userAnna = new User(annaUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(userAnna));
        given(fakeDataDao.updateUser(userAnna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updatedResult = userService.updateUser(userAnna);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertAnnaFields(user);

        assertThat(updatedResult).isEqualTo(1);


    }

    @Test
    void shouldRemoveUser() {

        UUID annaUid = UUID.randomUUID();
        User userAnna = new User(annaUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(userAnna));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.removeUser(annaUid);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    void shouldInsertUser() throws Exception {
        UUID userUid = UUID.randomUUID();
        User userAnna = new User(userUid,
                "anna", "montana", User.Gender.FEMALE, 30, "anna@gmail.com");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        given(fakeDataDao.insertUser(any(UUID.class), any(User.class))).willReturn(1);


        int insertResult = userService.insertUser(userAnna);

        verify(fakeDataDao).insertUser(eq(userUid), captor.capture());

        User user = captor.getValue();

        assertAnnaFields(user);

        assertThat(insertResult).isEqualTo(1);
    }


    private void assertAnnaFields(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);
    }
}