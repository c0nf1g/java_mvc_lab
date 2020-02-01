package iot.lviv.ua.ViewAndController;

import iot.lviv.ua.model.UserEntity;
import iot.lviv.ua.service.UserService;
import iot.lviv.ua.service.UserService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class test {
    private Map<String, String> menu;
    private Map<String, Printable> methodsMenu;
    private static Scanner input = new Scanner(System.in);
    
    private void deleteFromUser() throws SQLException {
        System.out.println("Input ID for User: ");
        int id = Integer.parseInt(input.nextLine());
        UserService userService = new UserService();
        int count = userService.delete(id);
        System.out.printf("There are deleted %d rows\n", count);
    }

    private void createForUser() throws SQLException {
        System.out.println("Input ID for User: ");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Input name for User: ");
        String name = input.nextLine();
        System.out.println("Input surname for User: ");
        String surname = input.nextLine();
        UserEntity entity = new UserEntity(id, name, surname);

        UserService userService = new UserService();
        int count = userService.create(entity);
        System.out.printf("There are created %d rows\n", count);
    }

    private void updateUser() throws SQLException {
        System.out.println("Input ID for User: ");
        int id = Integer.parseInt(input.nextLine());
        System.out.println("Input name for User: ");
        String name = input.nextLine();
        System.out.println("Input surname for User: ");
        String surname = input.nextLine();
        UserEntity entity = new UserEntity(id, name, surname);

        UserService userService = new UserService();
        int count = userService.update(entity);
        System.out.printf("There are updated %d rows\n", count);
    }

    private void selectUser() throws SQLException {
        System.out.println("\nTable: User");
        UserService userService = new UserService();
        List<UserEntity> users = userService.findAll();
        for (UserEntity entity : users) {
            System.out.println(entity);
        }
    }

    private void findUserByID() throws SQLException {
        System.out.println("Input ID(dept_no) for User: ");
        int id = Integer.parseInt(input.nextLine());
        UserService userService = new UserService();
        UserEntity entity = userService.findById(id);
        System.out.println(entity);
    }
}
