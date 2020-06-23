import models.User;
import models.Employee;
import models.Animal;
import org.hibernate.Hibernate;
import services.UserService;
import services.EmployeeService;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;
import java.util.List;
import java.io.StringWriter;
import java.io.IOException;

public class Session {
    private final User user;
    private final UserService userService = new UserService();
    private final EmployeeService employeeService = new EmployeeService();
    private final Scanner scanner = new Scanner(System.in);

    public Session(User user) {
        this.user = user;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void run() {
        System.out.println("Menu");
        System.out.println("1 - Manage account");
        System.out.println("2 - Work with Employees");
        System.out.println("3 - Work with Animals");
        System.out.println("0 - Exit");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                manageAccount();
                break;
            case "2":
                workWithEmployees();
                break;
            case "3":
                workWithAnimals();
                break;
            case "0":
                return;
            default:
                System.out.println("Unknown command");
        }
        run();
    }

    private void manageAccount() {
        System.out.println("1 - Change password");
        System.out.println("2 - Delete account");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                changePassword();
                break;
            case "2":
                deleteAccount();
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private void changePassword() {
        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        user.setPassword(String.valueOf(newPassword.hashCode()));
        userService.updateUser(user);
    }

    private void deleteAccount() {
        System.out.print("Delete account? [yes/no]: ");

        String option = scanner.nextLine();

        if ("yes".equals(option)) {
            userService.deleteUser(user);
        }
    }

    private void workWithEmployees() {
        System.out.println("1 - Create Employee");
        System.out.println("2 - Read Employee");
        System.out.println("3 - Update Employee");
        System.out.println("4 - Delete Employee");
        System.out.println("0 - Back to Menu");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                createEmployee();
                break;
            case "2":
                readEmployee();
                break;
            case "3":
                updateEmployee();
                break;
            case "4":
                deleteEmployee();
                break;
            case "0":
                return;
            default:
                System.out.println("Unknown command");
        }
        workWithEmployees();
    }

    private void createEmployee() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        String age = scanner.nextLine();
        System.out.print("Experience: ");
        String experience = scanner.nextLine();

        try {
            if (!isNumeric(age) || !isNumeric(experience))
                throw new NumberFormatException();

            Employee employee = new Employee(name, Integer.parseInt(age), Integer.parseInt(experience));
            employeeService.saveEmployee(employee);
        }
        catch (NumberFormatException e) {
            System.out.println("Not numeric");
        }
    }

    private void readEmployee() {
        System.out.println("1 - Read by ID");
        System.out.println("2 - Read by Name");
        System.out.println("3 - Read All");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                readEmployeeByID();
                break;
            case "2":
                readEmployeeByName();
                break;
            case "3":
                readAllEmployees();
                break;
            default:
                System.out.println("Unknown command");
        }

        employeeService.closeSession();
    }

    private void readEmployeeByID() {
        System.out.print("Employee ID: ");
        String id_string = scanner.nextLine();

        try {
            if (!isNumeric(id_string))
                throw new NumberFormatException();

            int id = Integer.parseInt(id_string);
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null)
                throw new EmptyException();

            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writeValue(stringWriter, employee);
            System.out.println(stringWriter.toString());
        } catch (NumberFormatException e) {
            System.out.println("ID string is not numeric");
        } catch (EmptyException e) {
            System.out.println("No Employee with such ID");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readEmployeeByName() {
        System.out.print("Employee name: ");
        String name = scanner.nextLine();

        try {
            List<Employee> employees = employeeService.getEmployeeByName(name);
            if (employees.isEmpty())
                throw new EmptyException();

            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writeValue(stringWriter, employees);
            System.out.println(stringWriter.toString());
        } catch (EmptyException e) {
            System.out.println("No Employees with such name");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty())
                throw new EmptyException();

            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writerWithDefaultPrettyPrinter().writeValue(stringWriter, employees);
            System.out.println(stringWriter.toString());
        } catch (EmptyException e) {
            System.out.println("No Employees");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        System.out.print("Employee ID: ");
        String id_string = scanner.nextLine();

        try {
            if (!isNumeric(id_string))
                throw new NumberFormatException();

            int id = Integer.parseInt(id_string);
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null)
                throw new EmptyException();

            employeeService.closeSession();

            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Age: ");
            String age = scanner.nextLine();
            System.out.print("Experience: ");
            String experience = scanner.nextLine();

            try {
                if (!(!name.isEmpty() && isNumeric(age) && isNumeric(experience)))
                    throw new NumberFormatException();

                employee.setName(name);
                employee.setAge(Integer.parseInt(age));
                employee.setExperience(Integer.parseInt(experience));
                employeeService.updateEmployee(employee);
            }
            catch (NumberFormatException e) {
                System.out.println("Not numeric");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID string is not numeric");
        } catch (EmptyException e) {
            System.out.println("No Employee with such ID");
        }
    }

    private void deleteEmployee() {
        System.out.print("Employee ID: ");
        String id_string = scanner.nextLine();

        try {
            if (!isNumeric(id_string))
                throw new NumberFormatException();

            int id = Integer.parseInt(id_string);
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null)
                throw new EmptyException();

            employeeService.closeSession();

            System.out.print("Delete employee? [yes/no]: ");
            String option = scanner.nextLine();
            if ("yes".equals(option)) {
                employeeService.deleteEmployee(employee);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID string is not numeric");
        } catch (EmptyException e) {
            System.out.println("No Employee with such ID");
        }
    }

    private void workWithAnimals() {
        System.out.println("1 - Create Animal");
        System.out.println("2 - Read All Animals");
        System.out.println("3 - Delete Animal");
        System.out.println("0 - Back to Menu");

        System.out.print("Option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                createAnimal();
                break;
            case "2":
                readAllAnimals();
                break;
            case "3":
                deleteAnimal();
                break;
            case "0":
                return;
            default:
                System.out.println("Unknown command");
        }
        workWithAnimals();
    }

    private void createAnimal() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Species: ");
        String species = scanner.nextLine();
        System.out.print("Employee ID: ");
        String id_employee_string = scanner.nextLine();

        try {
            if (!isNumeric(id_employee_string))
                throw new NumberFormatException();

            int id = Integer.parseInt(id_employee_string);
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null)
                throw new EmptyException();

            Animal animal = new Animal(name, species);
            animal.setEmployee(employee);
            employee.addAnimal(animal);
            employeeService.closeSession();
            employeeService.updateEmployee(employee);
        } catch (NumberFormatException e) {
            System.out.println("Not numeric");
        } catch (EmptyException e) {
            System.out.println("No Employee with such ID");
        }
    }

    private void readAllAnimals() {
        try {
            List<Animal> animals = employeeService.getAllAnimals();
            if (animals.isEmpty())
                throw new EmptyException();

            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writerWithDefaultPrettyPrinter().writeValue(stringWriter, animals);
            System.out.println(stringWriter.toString());
        }
        catch (EmptyException e) {
            System.out.println("No Animals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAnimal() {
        System.out.print("Animal ID: ");
        String id_string = scanner.nextLine();

        try {
            if (!isNumeric(id_string))
                throw new NumberFormatException();

            int id = Integer.parseInt(id_string);
            Animal animal = employeeService.getAnimalById(id);
            if (animal == null)
                throw new EmptyException();

            System.out.print("Delete Animal? [yes/no]: ");
            String option = scanner.nextLine();
            if ("yes".equals(option)) {
                Employee employee = animal.getEmployee();
                employee.removeAnimal(animal);
                employeeService.closeSession();
                employeeService.updateEmployee(employee);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID string is not numeric");
        } catch (EmptyException e) {
            System.out.println("No Employee with such ID");
        }
    }
}
