package services;

import dao.UserDao;
import kotlin.Pair;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserDao userDao = Mockito.mock(UserDao.class);
    UserService userService;

    public UserServiceTest(){
        this.userService = new UserService(userDao);
    }

    @BeforeEach
    void setUp() {
        System.out.println("STARTING NEW TEST");
    }

    @AfterEach
    void tearDown() {
        System.out.println("END OF TEST");
    }

    @Test
    void getUserValid() {
        User testUser = new User(1, "adept", "password", "Angel", "Depena", "adept@gmail.com", 2);
        Pair<Boolean, User> expectedResult = new Pair<>(Boolean.TRUE, testUser);

        Mockito.when(userDao.getUser(testUser.getUser_id())).thenReturn(testUser);

        Pair<Boolean, User> actualResult = userService.getUser(testUser.getUser_id());

        assertEquals(expectedResult, actualResult);
    }

    @Test // user not in database
    void getUserInvalid() {
        User testUser = new User(1, "user1", "password", "first", "last", "email@gmail.com", 1);
        Pair<Boolean, User> expectedResult = new Pair<>(Boolean.TRUE, testUser);

        Mockito.when(userDao.getUser(testUser.getUser_id())).thenReturn(null);

        Pair<Boolean, User> actualResult = userService.getUser(testUser.getUser_id());

        assertNotEquals(expectedResult, actualResult);
    }

    @Test
    void getAllUsers() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User(1, "user1", "password", "first", "last", "email@gmail.com", 1));
        testUsers.add(new User(2, "user2", "password", "first", "last", "email@gmail.com", 1));
        testUsers.add(new User(3, "user3", "password", "first", "last", "email@gmail.com", 1));
        Pair<Boolean, List<User>> expectedResult = new Pair<>(Boolean.TRUE, testUsers);

        Mockito.when(userDao.getAllUsers()).thenReturn(testUsers);

        Pair<Boolean, List<User>> actualResult = userService.getAllUsers();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateUser() {
        User testUser = new User(1, "user1", "password", "first", "last", "email@gmail.com", 1);

        Mockito.when(userDao.getUser(testUser.getUser_id())).thenReturn(testUser);

        userService.updateUser(testUser.getUser_id());

        Mockito.verify(userDao, Mockito.times(1)).updateUser(testUser);
    }

    @Test
    void verifyUserFieldsValid() {
        User testUser = new User(1, "user1", "password", "first", "last", "email@gmail.com", 1);
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertTrue(result);
    }

    @Test
    void verifyUserFieldsEmptyUserFields() {
        User testUser = new User();
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertFalse(result);
    }

    @Test
    void verifyUserFieldsInvalidUsername() {
        User testUser = new User(1, "Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I1", "password", "first", "last", "email@gmail.com", 1);
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertFalse(result);
    }

    @Test
    void verifyUserFieldsInvalidEmail() {
        User testUser = new User(1, "user1", "password", "first", "last", "Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I1Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I1Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I1", 1);
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertFalse(result);
    }

    @Test
    void verifyUserFieldsEdgePassword() {
        User testUser = new User(1, "user1", "Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I", "first", "last", "email@gmail.com", 1);
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertTrue(result);
    }

    @Test
    void verifyUserFieldsInvalidPassword() {
        User testUser = new User(1, "user1", "Y5Kz71pSvUaJY5p3ok7u03wiCGFxV6ApA7YIx8VlA8cH9RLZ9I1", "first", "last", "email@gmail.com", 1);
        Boolean result = userService.verifyUserFields(testUser).getFirst();

        assertFalse(result);
    }
}