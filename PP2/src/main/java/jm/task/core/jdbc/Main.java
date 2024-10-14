package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Jason", "Statham", (byte) 57);
        userService.saveUser("Tom", "Cruise", (byte) 62);
        userService.saveUser("Keanu", "Reeves", (byte) 60);
        userService.saveUser("Will", "Smith", (byte) 56);

        userService.removeUserById(4);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
