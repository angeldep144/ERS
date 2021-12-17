package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import kotlin.Pair;
import models.JsonResponse;
import models.LoginDTO;
import models.User;
import models.UserDTO;
import services.UserService;

import java.util.List;

public class UserController {

    static UserService userService = new UserService();

    public void login(Context context) {
        //context.contentType("application/json");
        LoginDTO login = context.bodyAsClass(LoginDTO.class);
        //login.setPassword(Encryptor.encrypt(login.getPassword()));

        User user = userService.login(login.getUsername(), login.getPassword());

        if (user == null) {
            context.status(404);
            context.json(new JsonResponse(false, null));
        } else {
            context.sessionAttribute("user-session", user);
            context.json(new JsonResponse(true, new UserDTO(user)));
        }
    }

    public void logout(Context context) {
        context.sessionAttribute("user-session", null);
        context.json(new JsonResponse(true, null));
    }

    public void checkSession(Context context) {
        context.contentType("application/json");
        User user = context.sessionAttribute("user-session");

        if(user == null){
            context.json(new JsonResponse(false,null));
        }else{
            context.json(new JsonResponse(true, new UserDTO(user)));
        }
    }

    // returns nothing, just updates db
    public void createUser(Context context){
        User user = context.bodyAsClass(User.class);

        Pair<Boolean, String> check = userService.createUser(user);

        context.status(Integer.parseInt(check.getSecond().substring(0, 3)));
    }

    // returns a user in json string
    public void getUser(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer user_id = Integer.parseInt(context.pathParam("user_id"));

        Pair<Boolean, User> user = userService.getUser(user_id);

        if(!user.getFirst()) {
            context.status(404);
            context.json(new JsonResponse(false, null));
        }

        context.json(new JsonResponse(true, user.getSecond()));
    }

    // returns a list of users in json string
    public void getAllUsers(Context context) {
        context.contentType("application/json");

        Pair<Boolean, List<User>> userList = userService.getAllUsers();

        if(!userList.getFirst()) {
            context.status(404);
            context.json(new JsonResponse(false, null));
        }

        context.json(new JsonResponse(true, userList.getSecond()));
    }

    public void updateUser(Context context) {
        Integer user_id = Integer.parseInt(context.pathParam("user_id"));

        User user = userService.getUser(user_id).getSecond();
        Pair<Boolean, String> result = userService.updateUser(user_id);

        context.status(Integer.parseInt(result.getSecond().substring(0, 3)));
    }

    // returns nothing just updates db
    public void deleteUser(Context context) {
        Integer user_id = Integer.parseInt(context.pathParam("user_id"));

        Pair<Boolean, String> result = userService.deleteUser(user_id);

        context.status(Integer.parseInt(result.getSecond().substring(0, 3)));
    }
}
