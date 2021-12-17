package dao;

import models.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(){
        /*jdbc:postgresql://[your endpoint here]/[the specific database you want to connect to]*/
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/ers";
        this.username =  System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public UserDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // CREATE NEW USER
    @Override
    public void createUser(User user) {
        //creates active connection to the database
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            // User(ers_user_id, ers_username, ers_password, ers_first_name, ers_last_name, ers_email, ers_role_id
            String sql = "INSERT INTO ers_users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirst_name());
            ps.setString(4, user.getLast_name());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRole_id());

            ps.executeUpdate();

        }catch (SQLException e){
            logger.error(e);
        }
    }

    // READ: GET ONE USER
    @Override
    public User getUser(Integer user_id) {

        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_users WHERE ers_user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7));
            }

        }catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return user;
    }

    // READ: GET ALL USERS (FOR FINANCE MANAGERS)
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_users;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),
                                    rs.getString(4), rs.getString(5),
                                        rs.getString(6), rs.getInt(7)));
            }

        }catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return users;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            // User(ers_user_id, ers_username, ers_password, ers_first_name, ers_last_name, ers_email, ers_role_id
            String sql = "UPDATE ers_users SET ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ? WHERE ers_user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirst_name());
            ps.setString(4, user.getLast_name());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getUser_id());

            ps.executeUpdate();

            // RETURN UPDATED USER
            sql = "SELECT * FROM ers_users WHERE ers_user_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(3, user.getUser_id());

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                updatedUser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7));
            }

        }catch (SQLException e){
            logger.error(e);
        }
        return updatedUser;
    }

    @Override
    public Boolean deleteUser(Integer user_id) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "DELETE FROM ers_users WHERE ers_user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, user_id);

            ps.executeUpdate();

            return Boolean.TRUE;

        }catch (SQLException e){
            logger.error(e);
        }

        return Boolean.FALSE;
    }

    @Override
    public User login(String ers_username, String ers_password) {
        User user = null;

        try(Connection conn = DriverManager.getConnection(url, username, password)){ //try with resources

            String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, ers_username);
            ps.setString(2, ers_password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7));
            }

        }catch (SQLException e){
            e.printStackTrace();
            logger.error(e);
        }

        return user;
    }
}
