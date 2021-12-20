package dao;

import models.Reimbursement;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao {

    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(ReimbursementDaoImpl.class);

    public ReimbursementDaoImpl(){
        /*jdbc:postgresql://[your endpoint here]/[the specific database you want to connect to]*/
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/ers";
        this.username =  System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ReimbursementDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public void createReimbursement(Reimbursement reimb) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) { //try with resources

            // Reimbursement(id, amount, date_submitted, date_resolved, description, receipt_img, author_id, resolver_id, status_id, type_id)
            String sql = "INSERT INTO ers_reimbursements VALUES (DEFAULT, ?, DEFAULT, DEFAULT, ?, DEFAULT, ?, DEFAULT, DEFAULT, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, reimb.getAmount());
            ps.setString(2, reimb.getDescription());
            // ps.setBytes(3, reimb.getReceipt());
            ps.setInt(3, reimb.getAuthor_id());
            ps.setInt(4, reimb.getType_id());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    @Override
    public Reimbursement getReimbursement(Integer reimb_id) {
        Reimbursement reimb = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_reimbursements WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, reimb_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimb = new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getTimestamp(3),
                                rs.getTimestamp(4), rs.getString(5), rs.getBytes(6), rs.getInt(7),
                                    rs.getInt(8), rs.getInt(9), rs.getInt(10));
            }

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return reimb;
    }

    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> reimbs = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_reimbursements WHERE reimb_status_id = 0 ORDER BY reimb_submitted;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbs.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4), rs.getString(5), rs.getBytes(6), rs.getInt(7),
                        rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return reimbs;
    }

    @Override
    public List<Reimbursement> getPastReimbursements() {
        List<Reimbursement> reimbs = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_reimbursements WHERE reimb_status_id !=0 ORDER BY reimb_submitted;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbs.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4), rs.getString(5), rs.getBytes(6), rs.getInt(7),
                        rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return reimbs;
    }

    @Override
    public List<Reimbursement> getUserReimbursements(Integer user_id) {
        List<Reimbursement> reimbs = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_reimbursements WHERE reimb_author = ? ORDER BY reimb_status_id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                reimbs.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getTimestamp(3),
                        rs.getTimestamp(4), rs.getString(5), rs.getBytes(6), rs.getInt(7),
                        rs.getInt(8), rs.getInt(9), rs.getInt(10)));
            }

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return reimbs;
    }

    @Override
    public void updateStatus(Integer reimb_id, Integer status_id, Integer resolver_id) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "UPDATE ers_reimbursements SET reimb_status_id = ?, reimb_resolver = ?, reimb_resolved = now() WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, status_id);
            ps.setInt(2, resolver_id);
            ps.setInt(3, reimb_id);

            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }
    }

    @Override
    public void deleteReimbursement(Integer reimb_id) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "DELETE from ers_reimbursements WHERE reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, reimb_id);

            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }
    }
}
