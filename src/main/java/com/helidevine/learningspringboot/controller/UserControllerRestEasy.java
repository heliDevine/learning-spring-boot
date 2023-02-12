package com.helidevine.learningspringboot.controller;

import com.helidevine.learningspringboot.model.User;
import com.helidevine.learningspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Validated
@Component
@Path("/api/v1/users")
public class UserControllerRestEasy {

    private UserService userService;

    @Autowired
    public UserControllerRestEasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        System.out.println(gender);
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")
    public User fetchUser(@PathParam("userUid") UUID userUid) {
        return userService.getUser(userUid)
                .orElseThrow(() -> new NotFoundException("User " + userUid + "doesn't exist"));
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public void insertNewUser(@Valid User user) {
        userService.insertUser(user);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void updateUser(User user) {
        userService.updateUser(user);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userUid}")

    public void deleteUser(@PathParam("userUid") UUID userUid) {
        userService.removeUser(userUid);
    }

    private Response getIntegerResponseEntity(int result) {
        if (result == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
