package com.helidevine.learningspringboot.IT;

import com.helidevine.learningspringboot.clentproxy.UserResourceV1;
import com.helidevine.learningspringboot.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIT {

    @Autowired
    private UserResourceV1 userResourceV1;
//
//    @Test
//    void itShouldFetchALlUsers() {
//
//        List<User> users = userResourceV1.fetchUsers(null);
//        assertThat(users).hasSize(1);
//
//        User joe = new User
//                (null, "Jo", "Jones",
//                        User.Gender.FEMALE, 22, "jo.jones@gmail.com");
//
//        assertThat(users.get(0)).usingRecursiveComparison().ignoringFields("userUid").isEqualTo(joe);
//        assertThat(users.get(0).getUserUid()).isNotNull();
//
//
//    }


    @Test
    public void shouldInsertUser() throws Exception {
        //GIVEN
        UUID userUid = UUID.randomUUID();
        User user = new User
                (userUid, "Jo", "Jones",
                        User.Gender.FEMALE, 22, "jo.jones@gmail.com");
        //WHEN
        userResourceV1.insertNewUser(user);
        //THEN
        User jo = userResourceV1.fetchUser(userUid);
        assertThat(jo).isEqualToComparingFieldByField(user);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //GIVEN
        UUID userUid = UUID.randomUUID();
        User user = new User
                (userUid, "Jo", "Jones",
                        User.Gender.FEMALE, 22, "jo.jones@gmail.com");
        //WHEN
        userResourceV1.insertNewUser(user);
        //THEN
        User jo = userResourceV1.fetchUser(userUid);
        assertThat(jo).isEqualToComparingFieldByField(user);

        //WHEN
        userResourceV1.deleteUser(userUid);

        //THEN
        assertThatThrownBy(() -> userResourceV1.fetchUser(userUid)).isInstanceOf(Exception.class);

    }

    @Test
    public void shouldUpdatetUser() throws Exception {
        //GIVEN
        UUID userUid = UUID.randomUUID();
        User user = new User
                (userUid, "Jo", "Jones",
                        User.Gender.FEMALE, 22, "jo.jones@gmail.com");
        //WHEN
        userResourceV1.insertNewUser(user);
        //THEN

        User updatedUser = new User
                (userUid, "Tom", "Jones",
                        User.Gender.FEMALE, 23, "tom.jones@gmail.com");

        userResourceV1.updateUser(updatedUser);

        user = userResourceV1.fetchUser(userUid);
        assertThat(user).isEqualToComparingFieldByField(updatedUser);

    }
//ERROR IN THIS TEST

    @Test
    public void fetchUsersByGender() throws Exception {

        //GIVEN
        UUID userUid = UUID.randomUUID();

        User user = new User
                (userUid, "Jo", "Jones",
                        User.Gender.MALE, 22, "jo.jones@gmail.com");
        //WHEN
        userResourceV1.insertNewUser(user);
        //THEN
//
//        List<User> females = userResourceV1.fetchUsers(User.Gender.FEMALE.name());
//
//        assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
//        assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
//        assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
//        assertThat(females).extracting("gender").doesNotContain(user.getGender());
//        assertThat(females).extracting("age").doesNotContain(user.getAge());
//        assertThat(females).extracting("email").doesNotContain(user.getEmail());

        List<User> males = userResourceV1.fetchUsers(User.Gender.MALE.name());

        assertThat(males).extracting("userUid").contains(user.getUserUid());
        assertThat(males).extracting("firstName").contains(user.getFirstName());
        assertThat(males).extracting("lastName").contains(user.getLastName());
        assertThat(males).extracting("gender").contains(user.getGender());
        assertThat(males).extracting("age").contains(user.getAge());
        assertThat(males).extracting("email").contains(user.getEmail());

    }
}
