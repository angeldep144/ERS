package controllers;

import io.javalin.http.Context;
import kotlin.Pair;
import models.JsonResponse;
import models.Reimbursement;
import models.ReimbursementDTO;
import services.ReimbursementService;

import java.util.List;

public class ReimbursementController {

    ReimbursementService reimbService = new ReimbursementService();

    // returns nothing, just updates db
    public void createReimbursement(Context context) {
        context.contentType("application/json");
        Reimbursement reimb = context.bodyAsClass(Reimbursement.class);

        Pair<Boolean, String> check = reimbService.createReimbursement(reimb);

        context.status(Integer.parseInt(check.getSecond().substring(0, 3)));
    }

    public void getAllReimbursements(Context context) {
        context.contentType("application/json");

        Pair<Boolean, List<ReimbursementDTO>> reimbList = reimbService.getAllReimbursements();

        if(!reimbList.getFirst()) {
            context.status(404);
        }

        context.json(reimbList.getSecond());
    }

    public void getUserReimbursements(Context context) {
        context.contentType("application/json");
        Integer user_id = Integer.parseInt(context.pathParam("user_id"));

        Pair<Boolean, List<ReimbursementDTO>> reimbList = reimbService.getUserReimbursements(user_id);

        if(!reimbList.getFirst()) {
            context.status(404);
        }

        context.json(reimbList.getSecond());
    }

    public void updateReimbursement(Context context) {
        context.contentType("application/json");
        Integer reimb_id = Integer.parseInt(context.pathParam("reimb_id"));

        Pair<Boolean, String> check = reimbService.updateStatus(reimb_id, context.bodyAsClass(Reimbursement.class).getStatus_id(), context.bodyAsClass(Reimbursement.class).getResolver_id());

        context.status(Integer.parseInt(check.getSecond().substring(0, 3)));
    }
}