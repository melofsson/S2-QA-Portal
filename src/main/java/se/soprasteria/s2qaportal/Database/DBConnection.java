package se.soprasteria.s2qaportal.Database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;
    private String username;
    private String password;
    private String dbURL;
    private URI dbURI;

    public DBConnection() {
        try {
            dbURI = new URI("postgres://qsybrkrphstrrh:b1acae8088f487d517c8e18c3a883cc21285092ae69e78076b03530fcd1f873c@ec2-176-34-113-195.eu-west-1.compute.amazonaws.com:5432/d8rn45r0u0osr8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        username = dbURI.getUserInfo().split(":")[0];
        password = dbURI.getUserInfo().split(":")[1];
        dbURL = "jdbc:postgresql://" + dbURI.getHost() + ':' + dbURI.getPort() + dbURI.getPath() + "?sslmode=require&";
    }

    public void closeConnection(Connection connection){
        System.out.println("Closing connection");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        System.out.println("Establishing connection");
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbURL() {
        return dbURL;
    }

    public URI getDbURI() {
        return dbURI;
    }

    public void setDbURI(URI dbURI) {
        this.dbURI = dbURI;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
}
