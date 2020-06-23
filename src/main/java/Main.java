import models.User;
import services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        main.run();
    }

    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    private void run() {
        System.out.println("1 - Sign in");
        System.out.println("2 - Sign up");
        System.out.println("0 - Exit");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                signIn();
                break;
            case "2":
                signUp();
                break;
            case "0":
                return;
            default:
                System.out.println("Unknown command");
        }
        run();
    }

    private void signIn() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        password = String.valueOf(password.hashCode());

        try {
            if (userService.getUserByAuth(login, password) == null)
                throw new EmptyException();

            Session session = new Session(userService.getUserByLogin(login));
            session.run();
        }
        catch (EmptyException e) {
            System.out.println("Wrong authentication data");
        }
    }

    private void signUp() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            if (userService.getUserByLogin(login) != null)
                throw new EmptyException();

            User user = new User(login, String.valueOf(password.hashCode()));
            userService.saveUser(user);
            Session session = new Session(user);
            session.run();
        }
        catch (EmptyException e) {
            System.out.println("Specified Login is already taken");
        }
    }
}