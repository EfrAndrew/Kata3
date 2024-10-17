package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            String createSQL = "CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "lastname VARCHAR(255),"
                    + "age INT)";

            transaction = session.beginTransaction();
            session.createNativeQuery(createSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            doRollbackTransaction(transaction);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            String dropSQL = "DROP TABLE IF EXISTS users";
            transaction = session.beginTransaction();
            session.createSQLQuery(dropSQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            doRollbackTransaction(transaction);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            doRollbackTransaction(transaction);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            doRollbackTransaction(transaction);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = Collections.emptyList();
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            doRollbackTransaction(transaction);
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            doRollbackTransaction(transaction);
        }
    }

    private void doRollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}