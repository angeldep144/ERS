package controllers;

import io.javalin.http.Context;
import kotlin.Pair;
import models.User;
import services.UserService;

public class UserController {

    static UserService userService = new UserService();

    public static void createUser(Context context){
        User user = context.bodyAsClass(User.class);

        Pair<Boolean, String> check = userService.createUser(user);

        if(!check.getFirst())   // if FALSE, status code is the first 3 characters of returns string
            context.status(Integer.parseInt(check.getSecond().substring(0, 3)));

        // if TRUE, status code returned is 200 SUCCESS
        context.status(Integer.parseInt(check.getSecond().substring(0, 3)));
        context.json(user);
    }
}
