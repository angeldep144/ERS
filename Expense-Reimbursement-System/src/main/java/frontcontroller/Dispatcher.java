package frontcontroller;

import controllers.ReimbursementController;
import controllers.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {

    UserController userController = new UserController();
    ReimbursementController reimbursementController = new ReimbursementController();

    public Dispatcher(Javalin app) {

        app.routes(() -> path("/ers", () -> {

            path("/login", () -> post(userController::login));
            path("/check-session", () -> get(userController::checkSession));
            path("/logout", () -> delete(userController::logout));

            path("/user-reimb/{user_id}", () -> {
                get(reimbursementController::getUserReimbursements);
                post(reimbursementController::createReimbursement);
            });

            path("/reimb", () -> {
                get(reimbursementController::getAllReimbursements);

                path("/decided", () -> {
                    get(reimbursementController::getPastReimbursements);
                });

                path("/{reimb_id}", () -> {
                    patch(reimbursementController::updateReimbursement);
                });
            });
        }));
    }
}
