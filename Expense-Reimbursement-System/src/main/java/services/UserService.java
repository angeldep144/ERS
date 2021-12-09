package services;

import dao.UserDao;
import dao.UserDaoImpl;
import kotlin.Pair;
import models.Encryptor;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {
    UserDao userDao;

    // SERVICE SPECIFIC VARIABLES TO CHECK FOR VALID INPUT
    private static final int MAX_LENGTH_OF_USERNAME = 50;
    private static final int MAX_LENGTH_OF_PASSWORD = 50;
    private static final int MAX_LENGTH_OF_NAME = 100;
    private static final int MAX_LENGTH_OF_EMAIL = 150;

    // CONSTRUCTORS
    public UserService() {
        this.userDao = new UserDaoImpl();
    }
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // CREATE
    public Pair<Boolean, String> createUser(User user) {
        Pair<Boolean, String> check = verifyUserFields(user);
        if (!check.getFirst())
            return check;

        // ENCRYPT PASSWORD
        String hash = Encryptor.encrypt(user.getPassword());

        if (Objects.equals(hash, ""))
            return new Pair<>(Boolean.FALSE, "500 Logic issue in encryption method. Try using a different password");

        user.setPassword(hash);
        User newUser = new User(user.getUser_id(), user.getUsername(), user.getPassword(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getRole_id());
        userDao.createUser(newUser);

        return new Pair<>(Boolean.TRUE, "201 New user successfully added to database");
    }

    // READ
    public Pair<Boolean, User> getUser(Integer user_id) {
        User user = userDao.getUser(user_id);   // return User in DB with ID = user_id or NULL if no user with such ID exists

        if (user == null)
            return new Pair<>(Boolean.FALSE, null);

        return new Pair<>(Boolean.TRUE, user);
    }

    public Pair<Boolean, List<User>> getAllUsers() {
        List<User> users = userDao.getAllUsers();   // return all Users in DB or NULL if no user with such ID exists

        if (users == null)
            return new Pair<>(Boolean.FALSE, null);

        return new Pair<>(Boolean.TRUE, users);
    }

    // UPDATE
    public Pair<Boolean, String> updateUser(User user) {
        Pair<Boolean, String> check = verifyUserFields(user);
        if (!check.getFirst())
            return check;

        // ENCRYPT PASSWORD
        String hash = Encryptor.encrypt(user.getPassword());
        if (Objects.equals(hash, ""))
            return new Pair<>(Boolean.FALSE, "500 Logic issue in encryption method. Try using a different password");

        user.setPassword(hash);
        User updatedUser = userDao.updateUser(user);

        if (updatedUser == null)
            return new Pair<>(Boolean.FALSE, "404 No user with ID: " + user.getUser_id() + " exists");

        return new Pair<>(Boolean.TRUE, "200 Success");
    }



    // DELETE
    public Pair<Boolean, String> deleteUser(Integer user_id) {
        Boolean check = userDao.deleteUser(user_id);
        if (!check)
            return new Pair<>(Boolean.FALSE, "404 no user with ID " + user_id + " exists");
        return new Pair<>(Boolean.TRUE, "201 The request has been fulfilled, resulting in the creation of a new user");
    }


    public static Pair<Boolean, String> verifyUserFields(User user) {
        if ((user.getUsername() != null) & (user.getUsername().length() > MAX_LENGTH_OF_USERNAME))
            return new Pair<>(Boolean.FALSE, "400 Length of username should not exceed 50 characters");

        if ((user.getPassword() != null) & (user.getPassword().length() > MAX_LENGTH_OF_PASSWORD))
            return new Pair<>(Boolean.FALSE, "400 Length of password should not exceed 50 characters");

        if ((user.getFirst_name() != null) & (user.getFirst_name().length() > MAX_LENGTH_OF_NAME))
            return new Pair<>(Boolean.FALSE, "400 Length of first name should not exceed 100 characters");

        if ((user.getLast_name() != null) & (user.getLast_name().length() > MAX_LENGTH_OF_NAME))
            return new Pair<>(Boolean.FALSE, "400 Length of last name should not exceed 100 characters");

        if ((user.getEmail() != null) & (user.getEmail().length() > MAX_LENGTH_OF_EMAIL))
            return new Pair<>(Boolean.FALSE, "400 Length of email address should not exceed 150 characters");

        return new Pair<>(Boolean.TRUE, "");
    }
}
