package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {
    public static String url = "jdbc:h2:./h2/db";
    public static String username = "sa";
    public static String password = "sa";

    public static void createUsersTable(){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "CREATE TABLE ers_users(\n" +
                    "\tuser_id serial PRIMARY KEY,\n" +
                    "\tusername varchar(100) NOT NULL,\n" +
                    "\tpassword varchar(100) NOT NULL,\n";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void dropUserTable(){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "DROP TABLE ers_users;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void createReimbursementTable(){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "CREATE TABLE ers_reimbursements(\n" +
                    "\tuser_id serial PRIMARY KEY,\n" +
                    "\tusername varchar(100) NOT NULL,\n" +
                    "\tpassword varchar(100) NOT NULL,\n";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void dropReimbursementTable(){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "DROP TABLE ers_reimbursements;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}