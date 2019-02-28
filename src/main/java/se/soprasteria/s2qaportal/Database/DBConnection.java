package se.soprasteria.s2qaportal.Database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;

    public DBConnection() {
        try {
            URI dbUri = new URI("postgres://kpltnijayoocxs:409be6b695215a1872f985ea90cca2d39ab246a1a2ca449fd729c1aca9633c1b@ec2-54-228-224-37.eu-west-1.compute.amazonaws.com:5432/d3cmu986rp3c84");
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require&";
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
