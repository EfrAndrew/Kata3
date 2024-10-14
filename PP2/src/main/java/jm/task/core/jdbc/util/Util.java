package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.Properties;
import java.util.logging.*;

public class Util {

    private static Connection connection = null;
    private static Util instance = null;
    private static final SessionFactory concreteSessionFactory = buildSessionFactory();
    private static final String url = "jdbc:mysql://localhost/kata1";
    private static final String username = "root";
    private static final String password = "2901password";

    private Util() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", username)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw ex;
        }
    }

    public static Session getSession() {
        return concreteSessionFactory.getCurrentSession();
    }
}
