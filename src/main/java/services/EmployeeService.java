package services;

import models.Employee;
import models.Animal;
import dao.EmployeeDAOImpl;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeDAO.getByName(name);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    public Animal getAnimalById(int id) {
        return employeeDAO.getAnimalById(id);
    }

    public List<Animal> getAllAnimals() {
        return employeeDAO.getAllAnimals();
    }

    public void saveEmployee(Employee employee) {
        employeeDAO.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    public void deleteEmployee(Employee employee) {
        employeeDAO.delete(employee);
    }

}
