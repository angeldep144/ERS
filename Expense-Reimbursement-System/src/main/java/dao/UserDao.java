package dao;

import models.User;

import java.util.List;

public interface UserDao {
    // CREATE
    void createUser(User user);

    // READ
    User getUser(Integer user_id);
    List<User> getAllUsers();

    // UPDATE
    User updateUser(User user);

    // DELETE
    Boolean deleteUser(Integer user_id);
}
