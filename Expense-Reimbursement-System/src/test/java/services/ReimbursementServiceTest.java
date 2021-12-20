package services;

import dao.ReimbursementDao;
import dao.UserDao;
import kotlin.Pair;
import models.Reimbursement;
import models.ReimbursementDTO;
import models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementServiceTest {

    ReimbursementDao reimbursementDao = Mockito.mock(ReimbursementDao.class);
    UserDao userDao = Mockito.mock(UserDao.class);

    ReimbursementService reimbursementService;
    UserService userService;

    public ReimbursementServiceTest(){
        this.reimbursementService = new ReimbursementService(reimbursementDao);
        this.userService = new UserService(userDao);
    }

    @Test
    void createReimbursement() {
    }

    @Test
    void getReimbursement() {
        Reimbursement testReimb = new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1);

        Pair<Boolean, ReimbursementDTO> expectedResult = new Pair<>(Boolean.TRUE, new ReimbursementDTO(testReimb));

        Mockito.when(reimbursementDao.getReimbursement(testReimb.getReimb_id())).thenReturn(testReimb);
        Mockito.when(userDao.getUser(testReimb.getAuthor_id())).thenReturn(new User(1, "angel", "password", "str", "str", "email@gmail.com", 2));
        Mockito.when(userDao.getUser(testReimb.getResolver_id())).thenReturn(new User(1, "angel", "password", "str", "str", "email@gmail.com", 2));


        Pair<Boolean, ReimbursementDTO> actualResult = reimbursementService.getReimbursement(testReimb.getReimb_id());

        assertEquals(expectedResult.getFirst(), actualResult.getFirst());
    }

    @Test
    void getAllReimbursements() {
        List<Reimbursement> reimbs = new ArrayList<>();
        reimbs.add(new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));
        reimbs.add(new Reimbursement(2, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));
        reimbs.add(new Reimbursement(3, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));

        Mockito.when(reimbursementDao.getAllReimbursements()).thenReturn(reimbs);

        List<ReimbursementDTO> testReimbs = new ArrayList<>();
        for(Reimbursement r : reimbs){
            testReimbs.add(new ReimbursementDTO(r));
        }

        Pair<Boolean, List<ReimbursementDTO>> expectedResult = new Pair<>(Boolean.TRUE, testReimbs);

        Pair<Boolean, List<ReimbursementDTO>> actualResult = reimbursementService.getAllReimbursements();

        assertEquals(expectedResult.getFirst(), actualResult.getFirst());
    }

    @Test
    void getUserReimbursements() {

        List<Reimbursement> reimbs = new ArrayList<>();
        reimbs.add(new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));
        reimbs.add(new Reimbursement(2, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));
        reimbs.add(new Reimbursement(3, 200.0, new Timestamp(0), null, "description", null, 1, 2, 0, 1));

        Mockito.when(reimbursementDao.getUserReimbursements(1)).thenReturn(reimbs);
        Mockito.when(userDao.getUser(1)).thenReturn(new User(1, "angel", "password", "str", "str", "email@gmail.com", 2));

        List<ReimbursementDTO> testReimbs = new ArrayList<>();
        for(Reimbursement r : reimbs){
            testReimbs.add(new ReimbursementDTO(r));
        }

        Pair<Boolean, List<ReimbursementDTO>> expectedResult = new Pair<>(Boolean.TRUE, testReimbs);

        Pair<Boolean, List<ReimbursementDTO>> actualResult = reimbursementService.getUserReimbursements(1);

        assertEquals(expectedResult.getFirst(), actualResult.getFirst());
    }

    @Test
    void updateStatus() {
        Reimbursement reimb = new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 1, 1, 1, 2);

        Mockito.when(reimbursementDao.getReimbursement(reimb.getReimb_id())).thenReturn(reimb);
        Mockito.when(userDao.getUser(reimb.getResolver_id())).thenReturn(new User(1, "angel", "password", "str", "str", "email@gmail.com", 2));

        reimbursementService.updateStatus(reimb.getReimb_id(), reimb.getAuthor_id(), reimb.getResolver_id());

        Mockito.verify(reimbursementDao, Mockito.times(1)).updateStatus(reimb.getReimb_id(), reimb.getStatus_id(), reimb.getResolver_id());
    }

    @Test
    void verifyReimbursementFields() {
        Reimbursement reimb = new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 1, 1, 1, 2);

        Mockito.when(userDao.getUser(reimb.getAuthor_id())).thenReturn(new User(1, "angel", "password", "str", "str", "email@gmail.com", 2));

        assertTrue(reimbursementService.verifyReimbursementFields(reimb).getFirst());
    }

    @Test
    void verifyReimbursementFieldsNullParam() {
        Reimbursement reimb = null;

        assertFalse(reimbursementService.verifyReimbursementFields(reimb).getFirst());
    }

    @Test
    void verifyReimbursementFieldsLongDescription() {
        Reimbursement test_reimb = new Reimbursement(1, 200.0, new Timestamp(0), null, "vuHLGeIAmDU7bajT1C9vCRVVcD2XhGevoVhJiPdaUsM2xedICTf7C26NJh61OdIm5OvSsHTy4KLJ0SaQkcybWF8km8MgKwyIklX08256czzfJAhke0JdWwd8i21JkohBYOfoxNgxspwLNHqGRXhoRV", null, 1, 1, 1, 2);

        assertFalse(reimbursementService.verifyReimbursementFields(test_reimb).getFirst());
    }

    @Test
    void verifyReimbursementFieldsNoUserExists() {
        Reimbursement reimb = new Reimbursement(1, 200.0, new Timestamp(0), null, "description", null, 99999, 1, 1, 2);

        Mockito.when(userDao.getUser(reimb.getAuthor_id())).thenReturn(null);

        assertFalse(reimbursementService.verifyReimbursementFields(reimb).getFirst());
    }
}

/*
* AWS_RDS_ENDPOINT=jwa-db.cizypscyslcb.us-east-2.rds.amazonaws.com; RDS_USERNAME=postgres; RDS_PASSWORD=p4ssw0rd;
* */