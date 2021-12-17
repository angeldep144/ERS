import frontcontroller.FrontController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("/frontend", Location.CLASSPATH);
        }).start(9000);

        new FrontController(app);


        //User user = new User(1, "adept", "password123456789012345678901345678901234567890", "angel", "dep", "adepthnic@gmail.com", 2);
       /* System.out.println("Password before Hash: password123456789012345678901345678901234567890");
        String encrypted = UserService.encrypt("password123456789012345678901345678901234567890");
        System.out.println("Password after Hash: " +encrypted);
        String decrypted = UserService.decrypt(encrypted);
        System.out.println("Password after decryption: " +decrypted);*/

    }
}
